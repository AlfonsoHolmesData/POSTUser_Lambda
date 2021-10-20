package com.revature.post_user;

import com.revature.post_user.exceptions.InalidRequestExectption;
import lombok.NonNull;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {
    private final DynamoDbTable<User> userTable;

    public UserRepository(){
        // create client
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        // create enhanced client
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        // specify target table
        userTable = dbClient.table("Users", TableSchema.fromBean(User.class));
    }

    /**
     * Gets all users from the Users table
     * @Authors Alfonso Holmes
     */
    public List<User> getAllUsers(){
        System.out.println("FROM USER REPOSITORY : " + userTable.scan().items().stream().collect(Collectors.toList()));
        // check if list cannot be found
        if(userTable.scan().items().stream().collect(Collectors.toList()) == null){ throw new RuntimeException("Null List"); }
        return userTable.scan().items().stream().collect(Collectors.toList());
    }

    public void createUser(User newUser){
        System.out.println("FROM USER REPOSITORY : " + userTable.scan().items().stream().collect(Collectors.toList()));
        if(newUser == null) { throw new InalidRequestExectption("Somthing went wrong :("); }
        if(newUser.username == null) { throw new InalidRequestExectption("User Must Have Username!"); }
        if(newUser.username.isEmpty() || newUser.username.length() < 2 ) { throw new InalidRequestExectption("Username must be larger than  2 characters"); }
        // check if user already exists with the included username

        //if(userTable.query()){ throw new RuntimeException("Null List"); }

        userTable.putItem(newUser);
    }
}