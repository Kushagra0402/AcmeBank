# AcmeBank

The application can build and run on maven or gradle with one click, when using IntelliJ.

to run the H2-Database console on browser, try going to:http://localhost:8080/h2-console on browser. and signing in with the credentials provided in the applications.properties file, including the JDBC URL, username and password mentioned in the file. 

The tests have been written in the AccountServiceTest.java file.

1. Check Balance API
For calling the balance api, kindly follow the given format:

http://localhost:8080/account-manager/{accountNumber}/balance

For example, for account number 88888888, this will be the GET API call,
http://localhost:8080/account-manager/88888888/balance.

2. Transfer Money Api:

This is a POST API and requires a JSON payload. Here is the format of the endpoint and payload. 

Endpoint: POST http://localhost:8080/account-manager/transfer
JSON Payload: 
{
    "debitAccountNumber" :"88888888",
    "creditAccountNumber":"12345678",
    "transferAmount": 1.00
}

The Database supports only 2 accounts 12345678 and 88888888.



