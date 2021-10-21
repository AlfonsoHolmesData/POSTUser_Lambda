package com.revature.post_user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.post_user.documents.User;
import com.revature.post_user.exceptions.InalidRequestExectption;
import com.revature.post_user.repos.UserRepository;


import java.util.HashMap;
import java.util.Map;

public class PostUserHandler implements RequestHandler<APIGatewayProxyRequestEvent , APIGatewayProxyResponseEvent> {
    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();
    private final UserRepository userRepo;


    public PostUserHandler()
    {
        userRepo = new UserRepository();

    }
    public PostUserHandler(UserRepository userRepo  )
    {
        this.userRepo = userRepo;

    }


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization");
        headers.put("Access-Control-Allow-Origin", "*");
        response.setHeaders(headers);
        User newUser = null;
        try{
            newUser = mapper.fromJson( request.getBody(), User.class );
            logger.log("CONVERTED USER AFTER GSON MAPPER: " + newUser + " : RECORDED BY LAMBDA LOGGER \n");
            userRepo.createUser(newUser);
            response.setBody(mapper.toJson(newUser));
            response.setStatusCode(200);
        }catch (InalidRequestExectption ire) {
            response.setStatusCode(400);
            logger.log("ERROR MESSAGE FROM LINE 40: " + ire.getMessage() + " : RECORDED BY LAMBDA LOGGER \n");
            logger.log("USER AFTER EXECPTION: " + mapper.toJson(newUser) + " : RECORDED BY LAMBDA LOGGER \n");

        }catch(Exception e) {
            response.setStatusCode(500);
            logger.log("USER AFTER 500 EXECPTION: " + mapper.toJson(newUser) + " : RECORDED BY LAMBDA LOGGER \n");
        }
        return response;
    }
}
