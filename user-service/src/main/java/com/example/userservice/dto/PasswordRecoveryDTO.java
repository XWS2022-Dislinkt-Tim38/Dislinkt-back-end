package com.example.userservice.dto;

import javax.validation.constraints.Pattern;

public class PasswordRecoveryDTO {
    public String tokenId;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}$")
    public String newPassword;

    PasswordRecoveryDTO(){}

    PasswordRecoveryDTO(String tokenId, String newPassword){
        this.newPassword = newPassword;
        this.tokenId = tokenId;
    }

}
