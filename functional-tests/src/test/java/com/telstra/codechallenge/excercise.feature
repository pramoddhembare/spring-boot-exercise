# See
# https://github.com/intuit/karate#syntax-guide
# for how to write feature scenarios
Feature: As an api user I want to retrieve some hottest repositories created in the last week and github accounts

  Scenario: Get a hottest repositories created in the last week
    Given url microserviceUrl
    Given param size = 1
    And path '/github/repositories'
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    # see https://github.com/intuit/karate#schema-validation
    And match response ==
    """
    [
    {
        html_url : '#string',
        watchers_count : '#number',
        language : '#string',
        description : '#string',
        name : '#string'
      }
    ]
    """

  Scenario: Find the oldest user accounts with zero followers
    Given url microserviceUrl
    Given param size = 1
    And path '/github/accounts'
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    # see https://github.com/intuit/karate#schema-validation
    # Define the required schema
    * def quoteSchema = [{ id : '#number',login : '#string', html_url : '#string' } ]


