package com.example.userservice.dto;

import com.example.userservice.model.User;

public class UserDTO {

    public String id;
    public String firstName;
    public String lastName;
    public String username;
    public String password;

    public UserDTO(){}

    public UserDTO(String id, String firstName, String lastName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public UserDTO(User user){
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.username = user.username;
        this.password = user.password;
    }
}
