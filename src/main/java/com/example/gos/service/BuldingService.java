package com.example.gos.service;



import com.example.gos.domain.User;
import com.example.gos.domain.dto.BuildingDto;
import com.example.gos.repos.BuildingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuldingService {
    @Autowired
    private BuildingRepo buildingRepo;

    public Page<BuildingDto> buildingList(Pageable pageable, String filter, User user) {
        if (filter != null && !filter.isEmpty()) {
            return buildingRepo.findByName(filter, pageable, user);
        } else {
            return buildingRepo.findAll(pageable, user);
        }
    }

    public static Page<BuildingDto> buildingListForUser(Pageable pageable, User currentUser, User author) {
        return BuildingRepo.findByUser(pageable, author, currentUser);
    }
}