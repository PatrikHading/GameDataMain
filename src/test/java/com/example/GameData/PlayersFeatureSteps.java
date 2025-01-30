package com.example.GameData;

import com.example.GameData.model.Player;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayersFeatureSteps {

    Player player;
    String name;
    String lastname;
    int jerseynumber;


    @Given("We create a player with name {string} and lastname {string} and jerseynumber {int}")
    public void we_create_a_player_with_name_and_lastname_and_jerseynumber(String name, String lastname, Integer jerseynumber) {
        player = new Player(name, lastname, jerseynumber);
    }
    @When("We get that players name and lastname and jerseynumber")
    public void we_get_that_players_name_and_lastname_and_jerseynumber() {
        name = player.getName();
        lastname = player.getLastname();
        jerseynumber = player.getJerseynumber();
    }
    @Then("The retrieved values are {string} and {string} and {int}")
    public void the_retrieved_values_are_and_and(String name, String lastname, Integer jerseynumber) {

        Assertions.assertEquals(name, this.name);
        Assertions.assertEquals(lastname, this.lastname);
        Assertions.assertEquals(jerseynumber, this.jerseynumber);
    }
}
