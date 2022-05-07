package com.example.authservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;


@Document
public class User implements UserDetails {

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

    public boolean isPublic() {
        return isPublic;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }


    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

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

}

