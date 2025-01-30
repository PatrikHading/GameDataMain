Feature: Manage GameStats for players in matches

  Scenario: Create and retrieve GameStats for a player
    Given A player named "John Doe" exists in the system
    And A match with gameId "1234" has been created
    When I create GameStats for the player in the match with 2 goals, 1 assist, and 10 PIM
    Then GameStats should contain the correct information for the player, the match, and the statistics
