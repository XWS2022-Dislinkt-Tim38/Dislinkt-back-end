package com.example.joboffersevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobOfferServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOfferServiceApplication.class, args);
    }

}
