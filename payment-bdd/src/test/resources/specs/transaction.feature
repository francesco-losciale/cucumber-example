Feature: Handle transaction requests from merchants
  A person that runs a business will use the system to handle payments from their client

Scenario: New transaction
  Given the customer has a bank account
  When the transaction is requested by the merchant
  Then the amount will be debited
