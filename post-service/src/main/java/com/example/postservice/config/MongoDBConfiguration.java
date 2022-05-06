package com.example.postservice.config;

import com.example.postservice.dto.PostDTO;
import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Date;

@EnableMongoRepositories(basePackageClasses = PostRepository.class)
@Configuration
public class MongoDBConfiguration {


}