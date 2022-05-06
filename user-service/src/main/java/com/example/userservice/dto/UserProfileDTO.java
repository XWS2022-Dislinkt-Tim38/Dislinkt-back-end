package com.example.userservice.dto;

import com.example.userservice.model.Education;
import com.example.userservice.model.WorkExperience;

import java.util.List;

public class UserProfileDTO {

    public String biography;
    public List<String> skills;
    public List<String> interests;
    public List<Education> education;
    public List<WorkExperience> experience;

    public UserProfileDTO(){}
    public UserProfileDTO(String biography, List<String> skills,
                          List<String> interests,List<Education> education,
                          List<WorkExperience> experience ){
        this.biography = biography;
        this.skills = skills;
        this.interests = interests;
        this.education = education;
        this.experience = experience;

    }
}
