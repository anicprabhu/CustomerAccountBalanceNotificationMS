# Application Architecture

# Prerequisites
Please ensure you have below tools installed on your system before you proceed with the lab.
1. Java 8
2. Maven
3. STS or any IDE for Java
4. Postman REST Client


# CustomerMS
We will first start by creating the CustomerMS. This MS will talk to an in memory instance of SQL Database- H2 to fetch the customer details.
We will be exposing all the CRUD methods on a Customer, 
The purpose of this MS will be to take Customer ID as input and return his AccountID.

## Create a SpringBoot Project

 Use either from start.spring.io or do directly from inside the STS - Spring Started Project

 