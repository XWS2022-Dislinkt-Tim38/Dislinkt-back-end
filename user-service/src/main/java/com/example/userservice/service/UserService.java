package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.model.VerificationToken;
import com.example.userservice.repository.TokenRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.validation.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    private final EmailValidator emailValidator = new EmailValidator();

    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    public UserDTO getUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null)
            return new UserDTO(user);
        else
            return null;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null)
            return user;
        else
            return null;
    }


    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for (User user : users) {
            usersDTO.add(new UserDTO(user));
        }

        return usersDTO;
    }

    public List<UserDTO> getAllPublicProfiles(){
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for(User user : users){
            if(user.isPublic)
                usersDTO.add(new UserDTO(user));
        }

        return usersDTO;
    }

    public UserDTO addUser(UserDTO newUserDTO) {

        //Validation
        validate(newUserDTO);

        //Password hashing (BCrypt)
        newUserDTO.password = passwordEncoder().encode(newUserDTO.password);

        User newUser = new User(newUserDTO);
        userRepository.save(newUser);

        //EmailConfirmation
        VerificationToken verificationToken = new VerificationToken(
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                newUser);

        tokenService.saveToken(verificationToken);

        //TODO: Send E-mail

        return new UserDTO(newUser);

    }

    private void validate(UserDTO newUserDTO){
        String error = "";
        if (usernameExists(newUserDTO.username) || emailExists((newUserDTO.email)))
            error += "Username or Email already exists!\n";

        if(!emailValidator.test(newUserDTO.email))
            error += "E-mail not valid!\n";

        //TODO: Password Validation

        if(!error.equals(""))
            throw new IllegalStateException("\n" + error);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }


    public boolean updateUser(UserDTO updateUserDTO) {
        boolean status = userRepository.existsById(updateUserDTO.id);

        if (status) {
            User userToUpdate = userRepository.findById(updateUserDTO.id).orElse(null);
            assert userToUpdate != null;
            userToUpdate.firstName = updateUserDTO.firstName;
            userToUpdate.lastName = updateUserDTO.lastName;
            userToUpdate.username = updateUserDTO.username;
            userToUpdate.password = passwordEncoder().encode(updateUserDTO.password);
            userToUpdate.address = updateUserDTO.address;
            userToUpdate.email = updateUserDTO.email;
            userToUpdate.dateOfBirth = updateUserDTO.dateOfBirth;
            userToUpdate.phoneNumber = updateUserDTO.phoneNumber;
            userToUpdate.gender = updateUserDTO.gender;
            userToUpdate.isPublic = updateUserDTO.isPublic;

            userToUpdate.profile = updateUserDTO.profile;

            userRepository.save(userToUpdate);
        }

        return status;

    }

    public boolean deleteUser(String id) {
        boolean status = userRepository.existsById(id);
        if (status)
            userRepository.deleteById(id);

        return status;

    }

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
