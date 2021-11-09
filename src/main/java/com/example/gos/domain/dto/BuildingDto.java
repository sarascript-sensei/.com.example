package com.example.gos.domain.dto;


import com.example.gos.domain.Buildings;
import com.example.gos.domain.User;
import com.example.gos.domain.util.BuildingHelper;

public class BuildingDto {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Integer stages;
    private User author;
    private String filename;
    private Long likes;
    private Boolean meLiked;

    public BuildingDto(Buildings buildings, Long likes, Boolean meLiked) {
        this.id = buildings.getId();
        this.name = buildings.getName();
        this.address = buildings.getAddress();
        this.description = buildings.getDescription();
        this.stages = buildings.getStages();
        this.author = buildings.getAuthor();
        this.filename = buildings.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return BuildingHelper.getAuthorName(author);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
    public Integer getStages() {
        return stages;
    }

    public User getAuthor() {
        return author;
    }

    public String getFilename() {
        return filename;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getMeLiked() {
        return meLiked;
    }

    @Override
    public String toString() {
        return "BuildingDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }
}
