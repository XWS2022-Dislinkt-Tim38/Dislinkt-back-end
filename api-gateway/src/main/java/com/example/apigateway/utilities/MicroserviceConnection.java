package com.example.apigateway.utilities;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

public class MicroserviceConnection {

    private static UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public static UserServiceGrpc.UserServiceBlockingStub openChannelToUserService(){
        if(userServiceBlockingStub == null) {
            ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 8000).usePlaintext();
            Channel channel = channelBuilder.build();
            userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
        }
        return userServiceBlockingStub;
    }
}
