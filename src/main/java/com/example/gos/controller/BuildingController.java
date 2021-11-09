package com.example.gos.controller;

import com.example.gos.domain.Buildings;
import com.example.gos.domain.User;
import com.example.gos.domain.dto.BuildingDto;
import com.example.gos.repos.BuildingRepo;
import com.example.gos.service.BuldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class BuildingController {
    @Autowired
    private BuildingRepo buildingRepo;

    @Autowired
    private BuldingService buildingService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user
    ) {
        Page<BuildingDto> page = buildingService.buildingList(pageable, filter, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Buildings buildings,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        buildings.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ContollerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("buildings", buildings);
        } else {
            saveFile(buildings, file);

            model.addAttribute("buildings", null);

            buildingRepo.save(buildings);
        }

        Iterable<Buildings> building = buildingRepo.findAll();

        model.addAttribute("buildings", buildings);

        return "main";
    }

    private void saveFile(@Valid Buildings buildings, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            buildings.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-buildings/{author}")
    public String userBuildings(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            Model model,
            @RequestParam(required = false) Buildings buildings,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BuildingDto> page = BuldingService.buildingListForUser(pageable, currentUser, author);

        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("page", page);
        model.addAttribute("buildings", buildings);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("url", "/user-buildings/" + author.getId());

        return "userbuildings";
    }

    @PostMapping("/user-buildings/{user}")
    public String updatebuildings(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Buildings buildings,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("description") String description,
            @RequestParam("stages") Integer stages,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (buildings.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(name)) {
                buildings.setName(name);
            }

            if (!StringUtils.isEmpty(address)) {
                buildings.setName(name);
            }

            saveFile(buildings, file);

            BuildingRepo.save(buildings);
        }

        return "redirect:/user-buildings/" + user;
    }

    @GetMapping("/buildings/{buildings}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Buildings buildings,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = buildings.getLikes();

        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }
}