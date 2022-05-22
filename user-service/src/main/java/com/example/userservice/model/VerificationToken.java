package com.example.userservice.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Random;

@Document
public class VerificationToken {
    @Id
    public String id;
    public String token;
    public LocalDateTime createdAt;
    public LocalDateTime expiresAt;
    public LocalDateTime confirmedAt;
    public String idUser;
    public User user; //skloniti


    public VerificationToken(LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             String idUser) {
        String id = java.util.UUID.randomUUID().toString();
        this.id = "token_" + id;
        this.token = java.util.UUID.randomUUID().toString();
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmedAt = null;
        this.idUser = idUser;

    }
}
