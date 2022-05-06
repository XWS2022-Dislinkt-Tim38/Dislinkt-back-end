package com.example.postservice.service.common;

import com.example.postservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userFeign", url = "http://localhost:8000")
public interface UserFeignClient {

    @GetMapping(value = "/user/{id}")
    public UserDTO getUser(@PathVariable  String id);
}
