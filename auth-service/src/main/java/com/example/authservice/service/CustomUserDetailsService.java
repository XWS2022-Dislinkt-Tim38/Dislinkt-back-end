package com.example.authservice.service;

import com.example.authservice.model.User;
import com.example.authservice.service.common.UserFeignClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.net.ConnectException;

@Service
public class CustomUserDetailsService implements  UserDetailsService{

    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    private UserFeignClient  userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userFeignClient.getUserByUsername(username);
            if (user == null) {
                logger.error("Could not find user with username: " + username);
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            } else {
                logger.info("Successfully loaded user with username: " + username);
            }
        } catch (FeignException.NotFound ex) {
            logger.error("Could not find user with username: " + username, ex);
            throw ex;
        }
        return user;
    }
}
