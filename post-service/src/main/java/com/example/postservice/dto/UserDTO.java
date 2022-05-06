package com.example.postservice.dto;

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
}
