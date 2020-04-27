# Exchange app

App with simple integration for transferring money between user's subaccounts in different currencies.

It allows to create and display user account and subaccounts, transfer money with current rate downloaded from NBP API service.

All needed requests are in "request" folder in main project folder. It is in JSON Postman format, so just import this file into Postman app and try it!

###### Example requests:
1. POST _/exchange/accounts_ <br>
Allows register your account with pesel. Two subaccounts are made with this register - in PLN and USD currency. Value of "amount" field is saved in PLN subaccount <br>
Example body: <br>
{<br>
"pesel": "87042051771", <br>
    "name": "Jan", <br>
    "surname": "Kowalski", <br>
    "amount": 100.05 <br>
}<br>

2. GET _/exchange/accounts/\<pesel\>_ <br>
Displays information of account and all subaccounts for given user identified by pesel.

3. POST /exchange <br>
Provied a transaction between subaccounts of PLN and given currency. You can either BUY (paying by PLN) or SELL given currency. In current version only USD currency is supported.
Example BUY body: <br>
{<br>
    "pesel": "87042051771", <br>
    "exchangeType": "BUY", <br>
    "currency": "USD", <br>
    "amount": 20.05 <br>
}<br>
Above request will buy 20.05 dollars with current buy rate taken from NBP API Service and takes
funds from PLN account.
<br> 
**Attention** - Please remember that you have to have enough funds in PLN to do the transaction! 
<br><br>
Example SELL body: <br>
{<br>
    "pesel": "87042051771", <br>
    "exchangeType": "BUY", <br>
    "currency": "USD", <br>
    "amount": 10.75 <br>
}<br>
Above request will sell 10.75 dollars with current sell rate taken from NBP API Service and add proper amount
to PLN subaccount.
<br> 
**Attention** - Please remember that you have to have enough funds in USD to do the transaction!

###### How to build:
Required JAVA version: 11 <br>
You can run the application by using 
<code> ./mvnw spring-boot:run </code> 
Alternatively, you can build the JAR file with 
<code>./mvnw clean package</code> 
and then run the JAR file, as follows:
<code>java -jar target/exchange-0.0.1-SNAPSHOT.jar</code>
