package com.example.joboffersevice.service.common;

import com.example.joboffersevice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userFeign", url = "http://localhost:8000")
public interface UserFeignClient {

    @GetMapping(value = "/user/key/{id}")
    UserDTO getUser(@PathVariable  String id);
}
