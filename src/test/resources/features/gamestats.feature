Feature: Retrieve distinct game IDs

  Scenario: Fetch all distinct game IDs from the database
    Given The database contains multiple game statistics with different game IDs
    When I retrieve all distinct game IDs
    Then I should get a list of unique game IDs
