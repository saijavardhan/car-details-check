Feature: Car details check

  Scenario: Check the car details are correctly displayed in output file
    Given I have read car registration numbers from files like name "car_input" in "./src/main/resources/" directory
    When I get the car details from cartaxcheck website
    Then the car details should match with details in "car_output.txt" file in "./src/main/resources/" directory