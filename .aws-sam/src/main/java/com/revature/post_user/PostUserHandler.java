package com.revature.post_user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.exceptions.InalidRequestExectption;
import java.util.HashMap;
import java.util.Map;

public class PostUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private Gson mapper = (new GsonBuilder()).setPrettyPrinting().create();
    private final UserRepository userRepo;

    public PostUserHandler() {
        this.userRepo = new UserRepository();
    }

    public PostUserHandler(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap();
        headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization");
        headers.put("Access-Control-Allow-Origin", "*");
        response.setHeaders(headers);
        User newUser = null;

        try {
            newUser = (User)this.mapper.fromJson(requestEvent.getBody(), User.class);
            logger.log("CONVERTED USER AFTER GSON MAPPER: " + newUser + " : RECORDED BY LAMBDA LOGGER \n");
            this.userRepo.createUser(newUser);
            response.setBody(this.mapper.toJson(newUser));
            response.setStatusCode(200);
        } catch (InalidRequestExectption var8) {
            response.setStatusCode(400);
            logger.log("ERROR MESSAGE FROM LINE 40: " + var8.getMessage() + " : RECORDED BY LAMBDA LOGGER \n");
            logger.log("USER AFTER EXECPTION: " + this.mapper.toJson(newUser) + " : RECORDED BY LAMBDA LOGGER \n");
        } catch (Exception var9) {
            response.setStatusCode(500);
            logger.log("USER AFTER 500 EXECPTION: " + this.mapper.toJson(newUser) + " : RECORDED BY LAMBDA LOGGER \n");
        }

        return response;
    }
}
