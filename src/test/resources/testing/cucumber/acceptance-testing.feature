Feature: Acceptance testing for Henry's groceries

Scenario: Price a basket containing: 3 tins of soup and 2 loaves of bread, bought today,
    Given the date is today
    And the shopping basket is empty
    And 3 tins of soup are added to basket
    And 2 loaves of bread are added to basket
    When when the basket is calculated
    Then the total cost is £3.15

Scenario: Price a basket containing: 6 apples and a bottle of milk, bought today,
    Given the date is today
    And the shopping basket is empty
    And 6 single apples are added to basket
    And 1 bottles of milk are added to basket
    When when the basket is calculated
    Then the total cost is £1.90

Scenario: Price a basket containing: 6 apples and a bottle of milk, bought in 5 days time,
    Given the date is today plus 5 days
    And the shopping basket is empty
    And 6 single apples are added to basket
    And 1 bottles of milk are added to basket
    When when the basket is calculated
    Then the total cost is £1.84

Scenario: Price a basket containing: 3 apples, 2 tins of soup and a loaf of bread, bought in 5 days time,
    Given the date is today plus 5 days
    And the shopping basket is empty
    And 3 single apples are added to basket
    And 2 tins of soup are added to basket
    And 1 loaves of bread are added to basket
    When when the basket is calculated
    Then the total cost is £1.97