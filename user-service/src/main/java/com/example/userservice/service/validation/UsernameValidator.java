package com.example.userservice.service.validation;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class UsernameValidator implements Predicate<String> {
    @Override
    public boolean test(String username) {
        String regexPattern = "[A-Za-z0-9.]*";
        if(username.length() < 4 || username.length() > 10)
            return false;
        else
            return Pattern.compile(regexPattern)
                    .matcher(username)
                    .matches();
    }
}
