Feature: Managing players

  Scenario: Player instantiation
    Given We create a player with name "John" and lastname "Doe" and jerseynumber 99
    When  We get that players name and lastname and jerseynumber
    Then The retrieved values are "John" and "Doe" and 99
