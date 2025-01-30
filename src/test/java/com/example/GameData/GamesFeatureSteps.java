package com.example.GameData;

import com.example.GameData.model.GameStats;
import com.example.GameData.model.Player;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class GamesFeatureSteps {

    private Player player;
    private GameStats gameStats;
    private String gameId;

    @Given("A player named {string} exists in the system")
    public void a_player_named_exists_in_the_system(String playerName) {
        player = new Player(playerName, "Doe", 10);
    }

    @Given("A match with gameId {string} has been created")
    public void a_match_with_gameId_has_been_created(String gameId) {
        this.gameId = gameId;  // You can also create a match if needed
    }

    @When("I create GameStats for the player in the match with {int} goals, {int} assist, and {int} PIM")
    public void i_create_gameStats_for_the_player_in_the_match_with_goals_assist_and_pim(int goals, int assists, int pim) {
        gameStats = new GameStats();
        gameStats.setPlayer(player);
        gameStats.setGameId(gameId);
        gameStats.setGoals(goals);
        gameStats.setAssists(assists);
        gameStats.setPim(pim);
    }

    @Then("GameStats should contain the correct information for the player, the match, and the statistics")
    public void gameStats_should_contain_the_correct_information_for_the_player_the_match_and_the_statistics() {
        Assertions.assertEquals(player, gameStats.getPlayer());
        Assertions.assertEquals(gameId, gameStats.getGameId());
        Assertions.assertEquals(2, gameStats.getGoals());
        Assertions.assertEquals(1, gameStats.getAssists());
        Assertions.assertEquals(10, gameStats.getPim());
        Assertions.assertNotNull(gameStats.getGameDate());
    }
}
