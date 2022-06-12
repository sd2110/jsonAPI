Feature: Mobiquity QA assessment

  Scenario Outline: Test Json placeholder resources
    Given User is valid
    When User performs a GET call on the "<Users/Posts/Comments>" resource
    Then API call is successful with a status code "<statusCode>"
    Examples:
      | Users/Posts/Comments | statusCode |
      | users                | 200        |
      | posts                | 200        |
      | comments             | 200        |

  Scenario Outline: Test Users resource with a valid username in the query parameter
    Given User is valid
    When User performs a GET call on the "users" resource with username "<name>"
    Then API call is successful with a status code "<statusCode>"
    Examples:
      | statusCode |name|
      | 200        |Delphine|

  Scenario Outline: Test Users resource with an invalid username in the query parameter
    Given User is valid
    When User performs a GET call on the "users" resource with username "<name>"
    Then API call is successful with a status code "<statusCode>"
    Examples:
      | statusCode |name|
      | 204        |Sagar|

  Scenario Outline: Test posts resource with a valid userId in the query parameter
    Given User performs a GET call on the "users" resource with username "<name>"
    And Extracts the Id of this username
    When User performs a GET call on the "posts" resource with the extracted Id
    Then API call is successful with a status code "<statusCode>"
    Examples:
      | statusCode |name|
      | 200       |Delphine|

  Scenario Outline: Test comments resource with a valid postId in the query parameter
    Given User performs a GET call on the "users" resource with username "<name>"
    And Extracts the Id of this username
    And User performs a GET call on the "posts" resource with the extracted Id
    And Extract the postIds of this username
    When User performs a GET call on the "comments" resource with the extracted postIds
    Then API call is successful with a status code "<statusCode>"
    Examples:
      | statusCode |name|
      | 200        |Delphine|

  Scenario Outline: Test comments resource with a valid postId in the query parameter
    Given User performs a GET call on the "users" resource with username "<name>"
    And Extracts the Id of this username
    And User performs a GET call on the "posts" resource with the extracted Id
    And Extract the postIds of this username
    And User performs a GET call on the "comments" resource with the extracted postIds
    And Validates the email address in those comments
    Then API call is successful with a status code "<statusCode>"
    Examples:
      | statusCode |name|
      | 200        |Karianne|


