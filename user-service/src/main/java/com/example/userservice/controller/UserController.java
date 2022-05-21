package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.service.FollowingService;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FollowingService followingService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required = false) boolean isPublic) {

        List<UserDTO> users = new ArrayList<UserDTO>();

        if(isPublic) {
            users = userService.getAllPublicProfiles();
        }
        else {
            users = userService.getAllUsers();
        }
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

    @GetMapping(value = "/username")
    public ResponseEntity<Object> getUserByUsername(@RequestParam String username){

        User user = userService.getUserByUsername(username);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDTO newUserDTO) throws NoSuchAlgorithmException {
        UserDTO user = userService.addUser(newUserDTO);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>("Username already exists!", HttpStatus.OK);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO editUserDTO){
        boolean status = userService.updateUser(editUserDTO);
        if(status)
            return new ResponseEntity<>("User successfully updated!", HttpStatus.OK);
        else
            return new ResponseEntity<>("There was an error while updating!", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        boolean status = userService.deleteUser(id);
        if(status)
            return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
        else
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/follow")
    public ResponseEntity<String> followUser(@RequestParam (value = "subjectId") String subjectId,
                                             @RequestParam (value = "targetId") String targetId){
        String response = followingService.followUser(subjectId, targetId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/follow/manage")
    public ResponseEntity<String> manageFollowRequest(@RequestParam (value = "subjectId") String subjectId,
                                                      @RequestParam (value = "targetId") String targetId,
                                                      @RequestParam (value = "followResponse") boolean followResponse){
        String response = followingService.manageRequest(subjectId, targetId, followResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "role/testadmin")
    public ResponseEntity<String> testAdminRole(){
        return new ResponseEntity<>("This is ADMIN", HttpStatus.OK);
    }

    @GetMapping(value = "role/testuser")
    public ResponseEntity<String> testUserRole(){
        return new ResponseEntity<>("This is USER", HttpStatus.OK);
    }
}
