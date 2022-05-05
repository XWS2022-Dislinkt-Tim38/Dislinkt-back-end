package com.example.userservice.model;

import com.example.userservice.dto.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.Date;


@Document
public class User {

    @Id
    public String id;
    public String firstName;
    public String lastName;
    public String address;
    public Date dateOfBirth;
    public String biography;
    public boolean isPublic;
    public String username;
    public String password;

    public User() {}

    public User(String firstName, String lastName, String address, Date dateOfBirth, String biography, boolean isPublic, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
        this.isPublic = isPublic;
        this.username = username;
        this.password = password;
    }

    public User(UserDTO userDTO){
        String id = java.util.UUID.randomUUID().toString();
        this.id = "user_" + id;
        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.username = userDTO.username;
        this.password = userDTO.password;
        this.address = userDTO.address;
        this.biography = userDTO.biography;
        this.dateOfBirth = userDTO.dateOfBirth;
        this.isPublic = userDTO.isPublic;

    }
}

