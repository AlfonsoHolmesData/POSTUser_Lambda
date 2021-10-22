package com.revature.post_user;

import com.revature.post_user.User;
import com.revature.exceptions.InalidRequestExectption;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import java.util.UUID;


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
//        User newUserCopy = new User();
//
//        newUserCopy.setId("6t7i-ek04-34ij-34tr");
//        newUserCopy.setUsername(newUser.username);
//        newUserCopy.setRank(newUser.rank);
//        newUserCopy.setLosses(newUser.losses);
//        newUserCopy.setWins(newUser.wins);
//        newUserCopy.setTotal_games_played(newUser.total_games_played);
//        newUserCopy.setGames_as_navigator(newUser.games_as_navigator);
//        newUserCopy.setGames_as_runner(newUser.games_as_runner);
//        newUserCopy.setWin_percentage(newUser.win_percentage);

        System.out.println("FROM USER REPOSITORY : " +  newUser + "\n");
        if(newUser == null) { throw new InalidRequestExectption("Somthing went wrong :("); }
        if(newUser.getUsername() == null) { throw new InalidRequestExectption("User Must Have Username!"); }
        if(newUser.getUsername().isEmpty() || newUser.getUsername().length() < 2 ) { throw new InalidRequestExectption("Username must be larger than  2 characters"); }
        // check if user already exists with the included username

        //if(userTable.query()){ throw new RuntimeException("Null List"); }
        UUID uuid = UUID.randomUUID();
        newUser.setId(uuid.toString());
        userTable.putItem(newUser);
    }
}