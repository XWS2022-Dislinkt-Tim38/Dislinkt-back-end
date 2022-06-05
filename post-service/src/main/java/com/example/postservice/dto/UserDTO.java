package com.example.postservice.dto;

import java.util.Date;
import java.util.List;

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

    public List<String> following;

    public UserDTO(){}

}
