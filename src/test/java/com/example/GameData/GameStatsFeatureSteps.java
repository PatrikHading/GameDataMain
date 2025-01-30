package com.example.GameData;

import com.example.GameData.repository.GameStatsRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class GameStatsFeatureSteps {

    private final GameStatsRepository gameStatsRepository = mock(GameStatsRepository.class);
    private List<String> retrievedGameIds;

    @Given("The database contains multiple game statistics with different game IDs")
    public void the_database_contains_multiple_game_statistics_with_different_game_IDs() {
        List<String> mockGameIds = Arrays.asList("GAME123", "GAME456", "GAME789");
        when(gameStatsRepository.findDistinctGameIds()).thenReturn(mockGameIds);
    }

    @When("I retrieve all distinct game IDs")
    public void i_retrieve_all_distinct_game_IDs() {
        retrievedGameIds = gameStatsRepository.findDistinctGameIds();
    }

    @Then("I should get a list of unique game IDs")
    public void i_should_get_a_list_of_unique_game_IDs() {
        Assertions.assertNotNull(retrievedGameIds);
        Assertions.assertEquals(3, retrievedGameIds.size());
        Assertions.assertTrue(retrievedGameIds.contains("GAME123"));
        Assertions.assertTrue(retrievedGameIds.contains("GAME456"));
        Assertions.assertTrue(retrievedGameIds.contains("GAME789"));
    }
}
