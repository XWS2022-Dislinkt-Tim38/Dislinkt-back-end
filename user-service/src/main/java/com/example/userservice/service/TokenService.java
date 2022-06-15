package com.example.userservice.service;

import com.example.userservice.model.VerificationToken;
import com.example.userservice.repository.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {

    Logger logger = LoggerFactory.getLogger(TokenService.class);
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserService userService;

    public VerificationToken findTokenById(String id){
        return tokenRepository.findTokenById(id);
    }
    public boolean isExpired(String token) {
        VerificationToken verificationToken = tokenRepository.findTokenById(token);
        return verificationToken.expiresAt.isBefore(LocalDateTime.now());
    }

    public String verifyTokenRegistration(String tokenId){
        VerificationToken verificationToken = tokenRepository.findTokenById(tokenId);

        if (verificationToken.confirmedAt != null) {
            logger.error("Token with id: " + verificationToken.id + " already used");
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = verificationToken.expiresAt;

        if (expiredAt.isBefore(LocalDateTime.now())) {
            logger.error("Token with id: " + tokenId + " used");
            throw new IllegalStateException("token expired");
        }

        verificationToken.confirmedAt = LocalDateTime.now();
        userService.enableUser(
                verificationToken.idUser);


        verificationToken.confirmedAt = LocalDateTime.now();
        tokenRepository.save(verificationToken);
        logger.info("E-mail for user with id: " + verificationToken.idUser + " verified");
        return "Your E-mail has been successfully verified!";
    }

}
