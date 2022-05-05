package com.example.userservice.model;

import com.example.userservice.dto.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class User {

    @Id
    public String id;
    public String firstName;
    public String lastName;
    public String username;
    public String password;

    public User() {}
    public User(String id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
    }
}

