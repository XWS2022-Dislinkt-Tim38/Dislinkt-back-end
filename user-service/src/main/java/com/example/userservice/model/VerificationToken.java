package com.example.userservice.model;

import com.example.userservice.model.User;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Random;

@Document
public class VerificationToken {
    @Id
    public Long id;
    public String token;
    public LocalDateTime createdAt;
    public LocalDateTime expiresAt;
    public LocalDateTime confirmedAt;

    public User user;

    public VerificationToken(LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             User user) {
        this.id = new Random().nextLong();
        this.token = java.util.UUID.randomUUID().toString();
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmedAt = null;
        this.user = user;
    }
}
