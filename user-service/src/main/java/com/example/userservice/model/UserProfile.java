package com.example.userservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserProfile {

    public String biography;
    public List<String> skills;
    public List<String> interests;
    public List<Education> education;
    public List<WorkExperience> experience;

    public UserProfile(){
        this.skills = new ArrayList<>();
        this.interests = new ArrayList<>();
        this.education = new ArrayList<>();
        this.experience = new ArrayList<>();
    }
    public UserProfile(String biography, List<String> skills, List<String> interests,
                       List<Education> education,
                       List<WorkExperience> experience){
        this.biography = biography;
        this.skills = skills;
        this.interests = interests;
        this.education =  education;
        this.experience = experience;

    }
}
