package com.example.apigateway.model;

import com.example.apigateway.model.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

    @Id
    public String id;

    public Role role;
    public String username;
    public String password;

    //Validation & Verification fields
    public boolean isVerified;
    public boolean isBlocked;
    public User() {}
}

