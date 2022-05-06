package com.example.postservice.config;

import com.example.postservice.repository.PostRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = PostRepository.class)
@Configuration
public class MongoDBConfiguration {


}