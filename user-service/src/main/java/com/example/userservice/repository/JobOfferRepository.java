package com.example.userservice.repository;

import com.example.userservice.model.JobOffer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobOfferRepository extends MongoRepository<JobOffer, String> {
    JobOffer findByPosition(String position);
}
