package com.example.userservice.repository;

import com.example.userservice.model.User;
import com.example.userservice.model.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<VerificationToken, String> {
}
