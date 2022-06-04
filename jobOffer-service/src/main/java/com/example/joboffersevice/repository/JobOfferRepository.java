package com.example.joboffersevice.repository;

import com.example.joboffersevice.model.JobOffer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobOfferRepository extends MongoRepository<JobOffer, String> {
    JobOffer findByTitle(String title);
    JobOffer findByIndustry(String industry);
    JobOffer findBySeniority(String seniority);
    JobOffer findByWorkType(String workType);
}
