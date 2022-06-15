package com.example.userservice.controller;


import com.example.userservice.model.VerificationToken;
import com.example.userservice.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/token")
public class TokenController {

    Logger logger = LoggerFactory.getLogger(TokenController.class);
    @Autowired
    TokenService tokenService;

    @GetMapping
    public ResponseEntity<String> confirmToken(@RequestParam(value = "tokenID") String tokenId) {
        logger.info("GET REQUEST /token");
        String response = tokenService.verifyTokenRegistration(tokenId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/recovery")
    public ResponseEntity<Boolean> checkIfTokenExpired(@RequestParam(value = "tokenId") String tokenId) {
        logger.info("GET REQUEST /token/recovery");
       Boolean response = tokenService.isExpired(tokenId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findTokenById(@PathVariable String id){
        logger.info("GET REQUEST /token/{id}");
        VerificationToken token = tokenService.findTokenById(id);
        if(token != null)
            return new ResponseEntity<>(token, HttpStatus.OK);
        else
            return new ResponseEntity<>("Token not found!", HttpStatus.NOT_FOUND);
    }
}
