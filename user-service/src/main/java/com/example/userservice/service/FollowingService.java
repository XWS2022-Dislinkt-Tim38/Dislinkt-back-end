package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingService {

    @Autowired
    private UserRepository userRepository;
    public String followUser(String subjectId, String targetId){
        String response = "";
        User target = userRepository.findById(targetId).orElse(null);
        User subject = userRepository.findById(subjectId).orElse(null);

        if(target == null) response = "User you want to follow cannot be found!";
        else if (subject == null)  response = "User can not be found!";
        else {

            if(!target.isPublic){
                target.followRequests.add(subjectId);
                userRepository.save(target);
                response = "Request to follow " + target.username + " is pending...";
            } else {

                addFollower(subject, target);
                response = "You successfully followed " + target.username;
            }
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
            //TODO: Informisati korisnika da mu je zahtev odobren - Poruke/notifikacije
        }
        else{

            target.followRequests.remove(subjectId);
            userRepository.save(target);
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
}
