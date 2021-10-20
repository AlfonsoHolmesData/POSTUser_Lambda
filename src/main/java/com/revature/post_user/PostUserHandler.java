package com.revature.post_user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PostUserHandler implements RequestHandler<APIGatewayProxyRequestEvent , APIGatewayProxyResponseEvent> {
    private final UserRepository userRepo;
    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();

    public PostUserHandler()
    {
        userRepo = new UserRepository();
    }
    public PostUserHandler(UserRepository userRepo)
    {
        this.userRepo = new UserRepository();
    }


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try{
            User newUser = mapper.fromJson( response.getBody(), User.class );
            userRepo.createUser(newUser);
            response.setStatusCode(200);
        }catch(Exception e) {
            response.setStatusCode(400);
        }
        return null;
    }
}
