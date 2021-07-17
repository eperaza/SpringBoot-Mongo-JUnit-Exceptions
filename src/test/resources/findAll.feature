Feature: the message can be retrieved
  Scenario: client makes call to GET /findall
    Given the client calls /findall
    When the client receives status code of 200
    Then the client receives size of 1