package com.example.authservice.service.common;

import com.example.authservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "userFeign", url = "http://localhost:8000")
public interface UserFeignClient {

    @GetMapping(value = "/user/username")
    public User getUserByUsername(@RequestParam String username);

    @GetMapping(value = "/user/getByTokenId/token")
    public User getUserByTokenId(@RequestParam String token);
}
