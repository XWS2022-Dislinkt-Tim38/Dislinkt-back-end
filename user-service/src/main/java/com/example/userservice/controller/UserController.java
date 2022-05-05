package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        List<UserDTO> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/public")
    public ResponseEntity<List<UserDTO>> getAllPublicProfiles() {

        List<UserDTO> users = userService.getAllPublicProfiles();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id){

        UserDTO userDTO = userService.getUser(id);
        if(userDTO != null)
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody UserDTO newUserDTO){
        UserDTO user = userService.addUser(newUserDTO);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>("Username already exists!", HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO editUserDTO){
        boolean status = userService.updateUser(editUserDTO);
        if(status)
            return new ResponseEntity<>("User successfully updated!", HttpStatus.OK);
        else
            return new ResponseEntity<>("There was an error while updating!", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id){
        boolean status = userService.deleteUser(id);
        if(status)
            return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
        else
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);

    }
}
