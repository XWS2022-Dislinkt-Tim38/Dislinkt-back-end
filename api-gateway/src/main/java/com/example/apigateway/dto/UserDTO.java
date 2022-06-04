package com.example.apigateway.dto;

import com.example.apigateway.model.enums.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

public class UserDTO {

    public String id;

    @Pattern(regexp = "^[A-Z][A-za-z ]{1,15}")
    public String firstName;


    @Pattern(regexp = "^[A-Z][A-za-z ]{1,15}")
    public String lastName;
    public boolean isPublic;


    @Pattern(regexp = "[a-zA-Z0-9.]{4,9}$")
    public String username;


    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}$")
    public String password;
    public Gender gender;

    public Date dateOfBirth;


    @Pattern(regexp = "[0-9]{6,12}")
    public String phoneNumber;


    @Email
    public String email;


    @Pattern(regexp = "[a-zA-Z0-9 ]{4,20}$")
    public String address;

    public List<String> followers;
    public List<String> following;
    public List<String> followRequests;
    public String key;


    public UserDTO(){}

    public UserDTO(String firstName, String lastName, String username, String password, String phoneNumber, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}
