package com.example.userservice.service;

import com.example.userservice.model.VerificationToken;
import com.example.userservice.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserService userService;

    public void saveToken(VerificationToken token){
        tokenRepository.save(token);
    }

    public String verifyToken(String token){
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken.confirmedAt != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = verificationToken.expiresAt;

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        verificationToken.confirmedAt = LocalDateTime.now();
        userService.enableUser(
                verificationToken.userId);

        verificationToken.confirmedAt = LocalDateTime.now();
        tokenRepository.save(verificationToken);
        return "Your E-mail has been successfully verified!";
    }
}
