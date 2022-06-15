package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FollowingService {

    Logger logger = LoggerFactory.getLogger(FollowingService.class);
    @Autowired
    private UserRepository userRepository;
    public String followUser(String subjectId, String targetId){
        String response = "";
        User target = userRepository.findById(targetId).orElse(null);
        User subject = userRepository.findById(subjectId).orElse(null);

        if(target == null) response = "User you want to follow cannot be found!";
        else if (subject == null)  response = "User can not be found!";
        else {
            //TODO: Uncomment
            /*
            if(!target.isPublic){
                target.followRequests.add(subjectId);
                userRepository.save(target);
                response = "Request to follow " + target.username + " is pending...";
            } else {

                addFollower(subject, target);
                response = "You successfully followed " + target.username;
            }
            */

            addFollower(subject, target);
            response = "You successfully followed " + target.username;
            logger.info("User with id: " + subjectId + " is now following user with id: " + targetId);
        }
        return response;
    }

    //Subject: The user that sent the request
    //Target: The user subject wants to follow
    public String manageRequest(String subjectId, String targetId, boolean response){
        String responseMessage = "";
        User target = userRepository.findById(targetId).orElse(null);
        User subject = userRepository.findById(subjectId).orElse(null);
        assert target != null;
        assert subject != null;

        if(response){

            target.followRequests.remove(subjectId);
            addFollower(subject, target);
            responseMessage = subject.username + " is now following you";
            logger.info("User with id: " + subjectId + " is now following user with id: " + targetId);
            //TODO: Informisati korisnika da mu je zahtev odobren - Poruke/notifikacije
        }
        else{

            target.followRequests.remove(subjectId);
            userRepository.save(target);
            logger.info("Request of user with id: " + subjectId + " to follow user with id: " + targetId + "declined");
            responseMessage = "You declined request of " + subject.username + " :(";
        }

        return responseMessage;
    }

    private void addFollower(User subject, User target){

        target.followers.add(subject.id);
        subject.following.add(target.id);
        userRepository.save(target);
        userRepository.save(subject);
    }

    public boolean unfollowUser(String subjectId, String targetId) {

        boolean response = false;
        User subject = userRepository.findById(subjectId).orElse(null);
        User target = userRepository.findById(targetId).orElse(null);

        if(subject != null && target != null){
            subject.following.removeIf(s -> s.equals(targetId));
            target.followers.removeIf(t -> t.equals(subjectId));

            userRepository.save(target);
            userRepository.save(subject);
            response = true;
        }
        logger.info("User with id: " + subjectId + " unfollowed user with id: " + targetId);
        return response;
    }
}
