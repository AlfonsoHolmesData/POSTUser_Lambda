package com.revature.post_user;



import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@DynamoDbBean
public class User {

    String   id;
    String   rank;
    int      losses;
    int      total_games_played;
    String   username;
    int      games_as_navigator;
    int      games_as_runner;
    float    win_percentage;
    int      wins;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @DynamoDbSortKey
    @DynamoDbAttribute("rank")
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
    @DynamoDbAttribute("losses")
    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
    @DynamoDbAttribute("total_games_played")
    public int getTotal_games_played() {
        return total_games_played;
    }

    public void setTotal_games_played(int total_games_played) {
        this.total_games_played = total_games_played;
    }
    @DynamoDbAttribute("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @DynamoDbAttribute("games_as_navigator")
    public int getGames_as_navigator() {
        return games_as_navigator;
    }

    public void setGames_as_navigator(int games_as_navigator) {
        this.games_as_navigator = games_as_navigator;
    }
    @DynamoDbAttribute("games_as_runner")
    public int getGames_as_runner() {
        return games_as_runner;
    }

    public void setGames_as_runner(int games_as_runner) {
        this.games_as_runner = games_as_runner;
    }
    @DynamoDbAttribute("win_percentage")
    public float getWin_percentage() {
        return win_percentage;
    }

    public void setWin_percentage(int win_percentage) {
        this.win_percentage = win_percentage;
    }
    @DynamoDbAttribute("wins")
    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
