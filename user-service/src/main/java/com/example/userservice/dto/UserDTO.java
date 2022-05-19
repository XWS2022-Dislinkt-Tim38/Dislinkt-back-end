package com.example.userservice.dto;

import com.example.userservice.model.User;
import com.example.userservice.model.UserProfile;
import com.example.userservice.model.enums.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

public class UserDTO {

    public String id;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+")
    public String firstName;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]*")
    public String lastName;
    public boolean isPublic;
    @Pattern(regexp = "^[A-Za-z0-9.]+")
    public String username;
    public String password;
    public Gender gender;
    public Date dateOfBirth;
    @NotBlank
    public String phoneNumber;
    @Email
    public String email;
    @NotBlank
    public String address;
    public UserProfile profile;
    public List<String> followers;
    public List<String> following;
    public List<String> followRequests;


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
        this.following = user.following;
        this.followers = user.followers;
        this.followRequests = user.followRequests;
    }
}
