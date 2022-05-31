package com.example.userservice.model;

import com.example.userservice.dto.JobOfferDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class JobOffer {

    @Id
    public String id;
    public String position;
    public List<String> description;
    public List<String> dailyActivities;
    public List<String> requirements;

    public JobOffer(){}

    public JobOffer(JobOfferDTO jobOfferDTO){
        String id = java.util.UUID.randomUUID().toString();
        this.id = "jobOffer_" + id;
        this.position = jobOfferDTO.position;
        this.description = jobOfferDTO.description;
        this.dailyActivities = jobOfferDTO.dailyActivities;
        this.requirements = jobOfferDTO.requirements;
    }
}
