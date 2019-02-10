Feature: Get the weather details depending on the query parameter

  Scenario Outline: Get the state code for given latitude and longitude
     Given I have the api key
     And I have Latitude as <latitude> and longitude as <longitude>
     When I call GET on weatherLatLong with values
     Then I receive the weather forecast with state code as <stateCode>
     Examples:
     | latitude   | longitude  | stateCode |
     | 40.730610  | -73.935242 | NY        |


  Scenario Outline: Get the time stamp and weather details for given postal code
    Given I have the api key
    And I have the post code <postCode> to check three hourly weather report
    When I do GET on weatherPostcode with postcode
    Then I receive the utc time stamp
    And I receive the weather
    Examples:
      |postCode|
      |28546|