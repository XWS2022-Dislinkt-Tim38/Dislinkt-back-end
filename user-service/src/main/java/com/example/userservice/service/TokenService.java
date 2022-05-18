package com.example.userservice.service;

import com.example.userservice.model.VerificationToken;
import com.example.userservice.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public void saveToken(VerificationToken token){
        tokenRepository.save(token);
    }
    
}
