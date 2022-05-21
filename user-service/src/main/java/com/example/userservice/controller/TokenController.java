package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.VerificationToken;
import com.example.userservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/token")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @GetMapping
    public ResponseEntity<String> confirmToken(@RequestParam(value = "tokenID") String tokenId) {
        String response = tokenService.verifyToken(tokenId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/recovery/{tokenId}")
    public ResponseEntity<Boolean> checkIfTokenExpired(@RequestParam(value = "tokenID") String tokenId) {
       Boolean response = tokenService.isExpired(tokenId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findTokenById(@PathVariable String id){

        VerificationToken token = tokenService.findTokenById(id);
        if(token != null)
            return new ResponseEntity<>(token, HttpStatus.OK);
        else
            return new ResponseEntity<>("Token not found!", HttpStatus.NOT_FOUND);
    }
}
