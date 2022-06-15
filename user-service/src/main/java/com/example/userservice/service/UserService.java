package com.example.userservice.service;

import com.example.userservice.dto.LinkRequestDTO;
import com.example.userservice.dto.PasswordRecoveryDTO;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.model.VerificationToken;
import com.example.userservice.repository.TokenRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.validation.UsernameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService /*extends UserServiceGrpc.UserServiceImplBase*/ {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private final EmailService emailService = new EmailService();
    private final UsernameValidator usernameValidator = new UsernameValidator();

    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    public UserDTO getUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null){
            logger.info("Fetching user with id: " + id);
            return new UserDTO(user);
        }
        else{
            return null;
        }

    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for (User user : users) {
            usersDTO.add(new UserDTO(user));
        }

        logger.info("Fetching all users from database");
        return usersDTO;
    }

    public List<UserDTO> getAllPublicProfiles(){
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for(User user : users){
            if(user.isPublic)
                usersDTO.add(new UserDTO(user));
        }

        logger.info("Fetching all public profiles");
        return usersDTO;
    }

    public UserDTO addUser(UserDTO newUserDTO) {

        //Validation
        validate(newUserDTO);

        //Password hashing (BCrypt)
        newUserDTO.password = passwordEncoder().encode(newUserDTO.password);

        User newUser = new User(newUserDTO);
        userRepository.save(newUser);
        logger.info("Registered user with id: " + newUser.id + " in database");

        sendVerification(newUser);
        return new UserDTO(newUser);

    }
    public void enableUser(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Token not found!"));
        user.isVerified = true;
        userRepository.save(user);
        logger.info("Enabled user with id: " + user.id);
    }
    private void validate(UserDTO newUserDTO){
        String error = "";

        if(!usernameValidator.test(newUserDTO.username))
            error += "Username not valid!\n";

        if (usernameExists(newUserDTO.username) || emailExists((newUserDTO.email)))
            error += "Username or Email already exists!\n";

        if(!error.equals(""))
            throw new IllegalStateException("\n" + error);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }



    private void sendVerification(User newUser){
        //EmailConfirmation
        VerificationToken verificationToken = new VerificationToken(
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                newUser.id);

        tokenRepository.save(verificationToken);
        logger.info("Created verification token: " + verificationToken.id +
                "for user with id: " + newUser.id);

        String confirmationLink = "http://localhost:8000/token?tokenID=" + verificationToken.id;
        String emailContent = buildEmail(newUser.firstName, confirmationLink);

        emailService.send(newUser.email, emailContent);
        logger.info("Sent verification link for user with id: " + newUser.id);
    }

    public void passwordRecovery(User user){
        VerificationToken verificationToken = new VerificationToken(
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user.id);
        tokenRepository.save(verificationToken);
        logger.info("Created verification token: " + verificationToken.id +
                "for user with id: " + user.id);
        String resetPasswordLink = "http://localhost:4200/password-recovery/" + verificationToken.id;
        String emailContent = "Dear " + user.firstName +
                ", please click on the link below to reset your password: \n" +
                resetPasswordLink + " \n(Expires in 15 minutes)\n Regards, Dislinkt";

        emailService.send(user.email, emailContent);
        logger.info("Sent verification link for user with id: " + user.id);
    }

    public boolean changePassword(PasswordRecoveryDTO changePasswordDTO){

        VerificationToken foundToken = tokenRepository.findTokenById(changePasswordDTO.tokenId);

        if(foundToken != null) {

            validateToken(foundToken);

            foundToken.confirmedAt = LocalDateTime.now();
            String idUser = foundToken.idUser;
            User user = userRepository.findById(idUser).orElseThrow(NullPointerException::new);

            user.password = passwordEncoder().encode(changePasswordDTO.newPassword);
            userRepository.save(user);
            tokenRepository.delete(foundToken);

            logger.warn("Password changed for user with id: " + user.id);
            logger.info("Token with id:  " + foundToken.id + " deleted");
            return true;

        }else{
            return false;
        }
    }

    public boolean updateUser(UserDTO updateUserDTO) {
        boolean status = userRepository.existsById(updateUserDTO.id);

        if (status) {
            User userToUpdate = userRepository.findById(updateUserDTO.id).orElse(null);
            assert userToUpdate != null;
            userToUpdate.firstName = updateUserDTO.firstName;
            userToUpdate.lastName = updateUserDTO.lastName;
            userToUpdate.username = updateUserDTO.username;
            //userToUpdate.password = passwordEncoder().encode(updateUserDTO.password);
            userToUpdate.address = updateUserDTO.address;
            userToUpdate.email = updateUserDTO.email;
            userToUpdate.dateOfBirth = updateUserDTO.dateOfBirth;
            userToUpdate.phoneNumber = updateUserDTO.phoneNumber;
            userToUpdate.gender = updateUserDTO.gender;
            userToUpdate.isPublic = updateUserDTO.isPublic;

            userToUpdate.profile = updateUserDTO.profile;

            userRepository.save(userToUpdate);
            logger.warn("Changes made for user: " + userToUpdate.id);
        }

        return status;

    }

    public boolean deleteUser(String id) {
        boolean status = userRepository.existsById(id);
        if (status)
            userRepository.deleteById(id);

        logger.info("User with id: " + id + " deleted");
        return status;

    }

    public boolean sendEmailPasswordlessLogin(String email){
        User user = userRepository.findByEmail(email);
        if(user != null){

            VerificationToken verificationToken = new VerificationToken(
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(1),
                    user.id);

            tokenRepository.save(verificationToken);

            String confirmationLink = "http://localhost:4200/passwordless?tokenId=" + verificationToken.id;
            String emailContent = "Dear " + user.firstName + " ,\n " +
                    "In order to confirm your identity, please click on the following link: \n" +
                    confirmationLink + "\n\n Regards, Dislinkt";

            emailService.send(email, emailContent);
            return true;
        }
        else return false;
    }

    public User getUserByTokenId(String tokenId){
        VerificationToken verificationToken = tokenRepository.findTokenById(tokenId);

        validateToken(verificationToken);
        User user = userRepository.findById(verificationToken.idUser).orElse(null);
        tokenRepository.delete(verificationToken);
        logger.info("Token with id:  " + verificationToken.id + " deleted");

        return user;
    }

    private void  validateToken(VerificationToken token){

        if (token == null) {
            throw new IllegalStateException("Token Already Used");
        }

        if (token.confirmedAt != null) {
            logger.info("Token with id: " + token.id + " used");
            throw new IllegalStateException("link expired/used");
        }

        LocalDateTime expiredAt = token.expiresAt;

        if (expiredAt.isBefore(LocalDateTime.now())) {
            logger.info("Token with id: " + token.id + " expired");
            throw new IllegalStateException("link expired");
        }
    }

    public String linkAccount(LinkRequestDTO linkRequestDTO){
        String key = "";
        User user = userRepository.findByUsername(linkRequestDTO.username);
        if(user != null){
            if(passwordEncoder().matches(linkRequestDTO.password, user.password)){
                key = java.util.UUID.randomUUID().toString();
                user.key = key;
                userRepository.save(user);
                logger.info("User with id: " + user.id + " linked account with Agent app");
            }
        }
        return key;
    }

    public User getUserByKey(String key) {
        return userRepository.findByKey(key);
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    //grpc
//    public void getUserById(InputID request, StreamObserver<Output> responseObserver) {
//        User user = userRepository.findById(id).orElse(null);
//        proto.user.Output output;
//        if(user == null){
//            output = Output.newBuilder().build();
//        }else {
//            output = Output.newBuilder()
//                    .setEmail(user.getEmail())
//                    .setUsername(user.getUsername())
//                    .setFirstName(user.getFirstName())
//                    .setLastName(user.getLastName())
//                    .build();
//        }
//        responseObserver.onNext(output);
//        responseObserver.onCompleted();
//    }

}
