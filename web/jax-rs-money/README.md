Java EE with JAX-RS using money api.
==================

This example has two subproject

---------
The marketplace:

The store has a goals to identify all products and do purchases.
This market is allowed just buy with local currency.
So you should chose with place and currency you will buy, there are five store available:

- Brazilian store that uses real (BRL) as currency
- European store that uses euro, EUR, as currency
- Argentine store that uses peso, ARS, as currency
- American store that uses dollar, USD, as currency


With the product list you can know the average, the cheaper, more expensive, beyond the summary of the products (to know the cheaper, more expensive, number of products, average).
This sample will use rest in Java EE container, so will use JAX-RS send the objects serializable as json.

To tools you can use Java EE wildfly as server container and as http client the poster a plugin to firefox.

The link to resource is:
http://localhost:8080/jar-rs-products/resources/COUNTRY/METHOD

Which COUNTRY may be:

- brazil
- america
- argentina
- europe

The resources are:

Get method with the link http://localhost:8080/jar-rs-products/resources/COUNTRY
Returns a list of products that may be used in another methods.


Post method with http://localhost:8080/jar-rs-products/resources/COUNTRY/buy send a list of product as json and the application/json  as content type.
Buy all products in the list and returns the sum.


Post method with http://localhost:8080/jar-rs-products/resources/COUNTRY/average send a list of product as json and the application/json  as content type.
Return a average of products


Método post com http://localhost:8080/jar-rs-products/resources/COUNTRY/cheaper send a list of product as json and the application/json  as content type.
Returns the cheapest price in the list.


Método post com http://localhost:8080/jar-rs-products/resources/COUNTRY/expensive send a list of product as json and the application/json  as content type.
Returns the more expensive price in the list


Método post com http://localhost:8080/jar-rs-products/resources/COUNTRY/summary send a list of product as json and the application/json  as content type.
Returns the brief of prices in the list.


---------
Bank.

Similar to store, the goal is simulate financial actions trivially done in the bank also will use JAX-RS, but may have money in different currencies.



Get method with http://localhost:8080/jar-rs-products/resources/bank
Returns a bank account that can be used in another methods.


Post method with http://localhost:8080/jar-rs-products/resources/bank/deposit send a bank account as json and the application/json  as content type.
Do a deposit, in other words, add money in the account. and returns the result of the operation in the currency used in this method.


Post method with http://localhost:8080/jar-rs-products/resources/bank/withdraw send a bank account as json and the application/json  as content type.
Do a withdraw, in other words, removes money of a account, and returns the result of the operation in the currency used in this method.


Post method with http://localhost:8080/jar-rs-products/resources/bank/all send a bank account as json and the application/json  as content type.
Returns all history in the account.

Post method http://localhost:8080/jar-rs-products/resources/bank/extract send a bank account as json and the application/json  as content type.
Returns the history from period.

