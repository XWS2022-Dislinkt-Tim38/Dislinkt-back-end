package com.example.userservice.model;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.enums.Gender;
import com.example.userservice.model.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class User {

    @Id
    public String id;

    public Role role;
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

    //Validation & Verification fields
    public boolean isVerified;
    public boolean isBlocked;
    private String verificationCode;
    public UserProfile profile;

    public List<String> following;
    public List<String> followers;
    public List<String> followRequests; //IDs of users that sent the request
    public String key;
    public User() {}

    public User(String firstName, String lastName,boolean isPublic, String username, String password,
                Gender gender, Date dateOfBirth, String phoneNumber, String email,
                String address, UserProfile profile ) {

        String id = java.util.UUID.randomUUID().toString();
        this.id = "user_" + id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isPublic = isPublic;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.followRequests = new ArrayList<>();
        this.profile = profile;
        this.isBlocked = false;
        this.isVerified = false;
        this.role = Role.USER;

    }

    public User(UserDTO userDTO){
        String id = java.util.UUID.randomUUID().toString();
        this.id = "user_" + id;
        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.isPublic = userDTO.isPublic;
        this.username = userDTO.username;
        this.password = userDTO.password;
        this.gender = userDTO.gender;
        this.dateOfBirth = userDTO.dateOfBirth;
        this.phoneNumber = userDTO.phoneNumber;
        this.email = userDTO.email;
        this.address = userDTO.address;
        this.profile = userDTO.profile;
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.followRequests = new ArrayList<>();
        this.role = Role.USER;
        this.isVerified = false;
        this.isBlocked = false;
        this.key = "";
    }
}

