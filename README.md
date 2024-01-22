# Trade Store Microservice
Microservice to simulate a Trade store developed using Spring Boot.

This application acts as a store for the trades.
Every trade record consists of below fields
- trade id
- version
- counter party id
- book id
- maturity date
- created date
- expired (flag)

This application stores the records into MYSQL Database table **trade**. 

For every trade id, there can be multiple records with different versions.

Below are the important  APIs  of the store application :

1. Store the Trade Entry : This API stores the new trade entry in the database table.


POST : /api/trades
Payload :

{
"trade_id": "<trade_id>",
"version": <version>,
"counter_party_id": "<counter_party_id>",
"book_id": "<book_id>",
"maturity_date": "<maturity_date>",
"created_date": "<created_date>",
"expired": "<Y/N>"
}

Response : 200 OK 
( along with the trade entry)

2. Get all the trade entries : This API returns all the trade entries

GET : /api/trades

Response : 200 OK

[
{
"trade_id": "<trade_id>",
"version": <version>,
"counter_party_id": "<counter_party_id>",
"book_id": "<book_id>",
"maturity_date": "<maturity_date>",
"created_date": "<created_date>",
"expired": "<Y/N>"
},

{
"trade_id": "<trade_id>",
"version": <version>,
"counter_party_id": "<counter_party_id>",
"book_id": "<book_id>",
"maturity_date": "<maturity_date>",
"created_date": "<created_date>",
"expired": "<Y/N>"
}]

3. Get the trade entries with given tradeId and version

GET : /api/trades/{tradeId}/{version}

Response : 200 OK
{
"trade_id": "<trade_id>",
"version": <version>,
"counter_party_id": "<counter_party_id>",
"book_id": "<book_id>",
"maturity_date": "<maturity_date>",
"created_date": "<created_date>",
"expired": "<Y/N>"
}

4. Get list of trades with previous maturity date and expired flag is still N

GET : /api/trades/expired?expired=true

Response : 200 OK

[
{
"trade_id": "<trade_id>",
"version": <version>,
"counter_party_id": "<counter_party_id>",
"book_id": "<book_id>",
"maturity_date": "<maturity_date>",
"created_date": "<created_date>",
"expired": "<Y/N>"
}]


While storing the trade entry into the store below validations are performed :
- Trade with previous maturity date is not added
- Trade with same Trade Id but lower version is not added, error is thrown
- If Trade with same Trade Id and same version is received, the Trade is overwritten

**Updating the Expired Flag of Trades** 

This application consists of an asynchronous scheduled task, which periodically checks if there are any Trade entries where the maturity date has passed, but still the expired flag is still 'N'.
For all such entries the expired flag is set to 'Y', by this task.

**Building and Running the application**

For building the application, maven tool is used.

_$ mvn install_ 

For running the application from command line, below command is used

$  java -jar tradestorems-0.0.1-SNAPSHOT.jar

