package com.example.userservice.config;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories (basePackageClasses = UserRepository.class)
@Configuration
public class MongoDBConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                /*userRepository.save(new User("user_1312321323", "Imenko", "Prezimic",
                        "testusername", "testpass"));
                        */

            }
        };
    }


}
