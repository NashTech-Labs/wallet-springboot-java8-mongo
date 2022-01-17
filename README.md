## About

This project is about  *Building a User Wallet system where a user can come and register for a wallet*

This is a spring boot application using *mongoDB* as a databse


## Problem Statement

Create a User Wallet system where a user can come and register for a wallet. Users should
be able to make transactions using their wallets.

## How to run the project

Following steps illustrate procedures you need to follow to run the code :

`Step 1` : Download the repository

```{shell}
$ git clone https://github.com/Munandermaan/virtual-wallet.git
$ cd virtual-wallet
```
`Step 2` : Run the docker-compose file with this command 

```
docker-compose up-d
```

`Step 3` : Build the project using maven

```{shell}
$ mvn clean install
```

`Step 4` : run the project

```
$ mvn spring-boot:run
```

Congratulations. You have successfully cloned the repo.

## Functionality
I used mongoDB as a database. Rest endpoints are secured with HTTP basic authentication. In order to use Rest-endpoints
Use below credentials to access endpoints
```
username: admin
password: password
```

* ### a) Create a new customer :

Endpoint: 
```http://localhost:8080/api/customer/register```

Use post request as
```
{
 "email" : "email",
 "fname": "first name",
 "lname": "last name",
 "password": "pass"  
}
```
Above Api will return ```customerId```. Use this id to create wallet in next request. Also ```customerId = walletId```

* ### b) Sign In:

Endpoint:
``` 
http://localhost:8080/api/customer/login
```
Use post request as

```
{
  "email": "email",
  "password": "password
}
```

* ### c) Get a customer detail by email:

```
http://localhost:8080/api/customer/{email}
```

* ### d) Create a new wallet for a user :

I am assuming a user can have a single account lined with wallet.

Provided endpoint for creating new wallet :
```
http://localhost:8080/api/wallet/{customerId}
```

I used `Postman` for this, since it provides easy interface for sending post request.

* ### e) Perform a deposit transaction on a wallet :

Provided endpoint : `http://localhost:8080//api/wallet/{customerId}/deposit/{amount}`

Allows one to deposit amount into a wallet.

* ### f) Perform a transfer from one wallet to another wallet :

Provided endpoint :
`http://localhost:8080/api/wallet/{fromWalletId}/transfer/{toWalletId}/amount/{amount}`.

Allows one to transfer money  one wallet to  another wallet.

Since one user only have one wallet , So we keep customerId equal to walletId. It means ```customerId = walletId```.
* ### g) Return all transactions for a wallet :

Provided endpoint :  `http://localhost:8080/api/wallet/{walletId}/transactions`.

Allows one to check their all transaction statement.
