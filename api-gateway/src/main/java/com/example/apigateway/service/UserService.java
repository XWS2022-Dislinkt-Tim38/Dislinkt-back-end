package com.example.apigateway.service;

import com.example.apigateway.dto.UserDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.user.UserServiceGrpc;


import static com.example.apigateway.utilities.MicroserviceConnection.openChannelToUserService;


@Service
public class UserService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    public UserDTO getUserById(String Id){
        blockingStub = openChannelToUserService();
        InputID input = InputID.newBuilder().setId(Id).build();
        Output result=this.blockingStub.getUserById(input);
        UserDTO userDTO =  new UserDTO();
        userDTO.firstName = result.getFirstName();
        userDTO.lastName = result.getLastName();
        return userDTO;
    }

}
