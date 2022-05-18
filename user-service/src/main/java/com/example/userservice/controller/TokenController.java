package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/token")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @GetMapping
    public ResponseEntity<String> getAllUsers(@RequestParam(value = "tokenID") String tokenId) {
        String response = tokenService.verifyToken(tokenId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
