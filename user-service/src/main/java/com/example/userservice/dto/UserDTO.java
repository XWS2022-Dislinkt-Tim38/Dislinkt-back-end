package com.example.userservice.dto;

import com.example.userservice.model.User;
import com.example.userservice.model.UserProfile;
import com.example.userservice.model.enums.Gender;

import java.util.Date;

public class UserDTO {

    public String id;
    public String firstName;
    public String lastName;
    public boolean isPublic;
    public String username;
    public String password;
    public Gender gender;
    public Date dateOfBirth;
    public String phoneNumber;
    public String email;
    public String address;
    public UserProfile profile;


    public UserDTO(){}


    public UserDTO(User user){
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.isPublic = user.isPublic;
        this.username = user.username;
        this.password = user.password;
        this.gender = user.gender;
        this.dateOfBirth = user.dateOfBirth;
        this.phoneNumber = user.phoneNumber;
        this.email = user.email;
        this.address = user.address;
        this.profile = user.profile;
    }
}
