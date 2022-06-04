package com.example.joboffersevice.config;

import com.example.joboffersevice.repository.JobOfferRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = JobOfferRepository.class)
@Configuration
public class MongoDBConfiguration {


}