package com.example.userservice.controller;

import com.example.userservice.dto.LinkRequestDTO;
import com.example.userservice.dto.PasswordRecoveryDTO;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.service.FollowingService;
import com.example.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger((UserController.class));
    @Autowired
    private UserService userService;
    @Autowired
    private FollowingService followingService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required = false) boolean isPublic) {
        logger.info("GET REQUEST /user");
        List<UserDTO> users;

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
        logger.info("GET REQUEST /user/{id}");
        UserDTO userDTO = userService.getUser(id);
        if(userDTO != null)
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/username")
    public ResponseEntity<Object> getUserByUsername(@RequestParam String username){
        logger.info("GET REQUEST /user/username");
        User user = userService.getUserByUsername(username);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/key/{key}")
    public ResponseEntity<Object> getUserByKey(@PathVariable String key){
        logger.info("GET REQUEST /user/key/{key}");
        User user = userService.getUserByKey(key);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDTO newUserDTO)  {
        logger.info("POST REQUEST /user");
        UserDTO user = userService.addUser(newUserDTO);
        return new ResponseEntity<>(Objects.requireNonNullElse(user, "Username already exists!"), HttpStatus.OK);

    }

    @CrossOrigin
    @PostMapping(value = "/resetPasswordRequest")
    public ResponseEntity<Object> resetPasswordRequest(@RequestBody String email) {
        logger.info("POST REQUEST /user/resetPasswordRequest");
        User user = userService.getUserByEmail(email);
        if(user != null){
            userService.passwordRecovery(user);
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PostMapping(value = "/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody PasswordRecoveryDTO changePasswordDTO){
        logger.info("POST REQUEST /user/change/password");
        boolean status = userService.changePassword(changePasswordDTO);
        if(status)
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO editUserDTO){
        logger.info("PUT REQUEST /user");
        boolean status = userService.updateUser(editUserDTO);
        if(status)
            return new ResponseEntity<>("User successfully updated!", HttpStatus.OK);
        else
            return new ResponseEntity<>("There was an error while updating!", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        logger.info("DELETE REQUEST /user/{id}");
        boolean status = userService.deleteUser(id);
        if(status)
            return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
        else
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/follow/{subjectId}/{targetId}")
    public ResponseEntity<String> followUser(@PathVariable (value = "subjectId") String subjectId,
                                             @PathVariable(value = "targetId") String targetId){
        logger.info("PUT REQUEST /user/follow/{subjectId}/{targetId}");
        String response = followingService.followUser(subjectId, targetId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/follow/manage")
    public ResponseEntity<String> manageFollowRequest(@RequestParam (value = "subjectId") String subjectId,
                                                      @RequestParam (value = "targetId") String targetId,
                                                      @RequestParam (value = "followResponse") boolean followResponse){
        logger.info("PUT REQUEST /user/follow/manage");
        String response = followingService.manageRequest(subjectId, targetId, followResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/passwordless")
    public ResponseEntity<Boolean> sendEmailForPasswordlessLogin( @RequestBody String email){
        logger.info("POST REQUEST /user/passwordless");
        Boolean response = userService.sendEmailPasswordlessLogin(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getByTokenId/token")
    public ResponseEntity<User> getUserByTokenId( @RequestParam String token){
        logger.info("GET REQUEST /user/getByTokenId/token");
        User user = userService.getUserByTokenId(token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4201")
    @PutMapping("/link")
    public ResponseEntity<Object> getUserByTokenId(@RequestBody LinkRequestDTO linkRequestDTO){
        logger.info("PUT REQUEST /user/link");
        String key = userService.linkAccount(linkRequestDTO);
        return new ResponseEntity<>(key, HttpStatus.OK);
    }

    @PutMapping(value = "/unfollow/{subjectId}/{targetId}")
    public ResponseEntity<Boolean> unfollowUser(@PathVariable (value = "subjectId") String subjectId,
                                             @PathVariable (value = "targetId") String targetId){
        logger.info("PUT REQUEST /user/unfollow/{subjectId}/{targetId}");
        Boolean response = followingService.unfollowUser(subjectId, targetId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
