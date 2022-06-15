package com.example.authservice.controller;

import com.example.authservice.dto.AuthenticationRequest;
import com.example.authservice.dto.UserTokenState;
import com.example.authservice.model.User;
import com.example.authservice.security.util.TokenUtils;
import com.example.authservice.service.common.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthenticationController {

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserFeignClient userFeignClient;

    @PostMapping("/login")
    public ResponseEntity<Object> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) {
        logger.info("Login Attempt for: " + authenticationRequest.getUsername());

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername(), user.role.toString(), user.id);
            int expiresIn = tokenUtils.getExpiredIn();
            logger.info("User with id: " + user.id + " successfully authenticated");
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));

        } catch (BadCredentialsException e){
            logger.warn("Login attempt failed for: " + authenticationRequest.getUsername());
            throw e;
        }

    }

    @PostMapping("/passwordlesslogin")
    public ResponseEntity<UserTokenState> passwordlessLogin(
            @RequestBody String token) {

        User userByToken = userFeignClient.getUserByTokenId(token);
        String jwt = tokenUtils.generateToken(userByToken.getUsername(), userByToken.role.toString(), userByToken.id);
        int expiresIn = tokenUtils.getExpiredIn();
        logger.info("User with id: " + userByToken.id + " successfully authenticated");
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

}
