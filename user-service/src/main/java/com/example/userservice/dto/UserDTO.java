package com.example.userservice.dto;

import com.example.userservice.model.User;

import java.util.Date;

public class UserDTO {

    public String id;
    public String firstName;
    public String lastName;

    public String address;

    public Date dateOfBirth;

    public String biography;

    public boolean isPublic;
    public String username;
    public String password;

    public UserDTO(){}

    public UserDTO(String id, String firstName, String lastName, String address, Date dateOfBirth, String biography,
                   boolean isPublic, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
        this.isPublic = isPublic;
        this.username = username;
        this.password = password;
    }

    public UserDTO(User user){
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.username = user.username;
        this.password = user.password;
        this.address = user.address;
        this.dateOfBirth = user.dateOfBirth;
        this.biography = user.biography;
        this.isPublic = user.isPublic;
    }
}
