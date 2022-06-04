package com.example.joboffersevice.model;

import com.example.joboffersevice.dto.JobOfferDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class JobOffer {

    @Id
    public String id;
    public String title;
    public String content;
    public String company;
    public String industry;
    public String field;
    public String seniority;
    public List<String> requirements;
    public String workType;
    public Date publishDate;
    public Date deadlineDate;
    public String city;

    public JobOffer(){}
    public JobOffer(JobOfferDTO jobOfferDTO){
        String id = java.util.UUID.randomUUID().toString();
        this.id = "jobOffer_" + id;
        this.title = jobOfferDTO.title;
        this.content = jobOfferDTO.content;
        this.company = jobOfferDTO.company;
        this.industry = jobOfferDTO.industry;
        this.field = jobOfferDTO.field;
        this.seniority = jobOfferDTO.seniority;
        this.requirements = jobOfferDTO.requirements;
        this.workType = jobOfferDTO.workType;
        this.publishDate = new Date();
        this.deadlineDate = jobOfferDTO.deadlineDate;
        this.city = jobOfferDTO.city;
    }


}
