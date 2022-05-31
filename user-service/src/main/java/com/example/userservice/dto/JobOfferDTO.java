package com.example.userservice.dto;

import com.example.userservice.model.JobOffer;

import java.util.List;

public class JobOfferDTO {

    public String id;
    public String position;
    public List<String> description;
    public List<String> dailyActivities;
    public List<String> requirements;

    public JobOfferDTO(){}

    public JobOfferDTO(JobOffer jobOffer){
        this.id =  jobOffer.id;
        this.position = jobOffer.position;
        this.description = jobOffer.description;
        this.dailyActivities = jobOffer.dailyActivities;
        this.requirements = jobOffer.requirements;
    }
}
