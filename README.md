# Overview

# Application Architecture

# Prerequisites
Please ensure you have below tools installed on your system before you proceed with the lab.
1. Java 8
2. Maven
3. STS or any IDE for Java
4. Postman REST Client
5. Couchbase Community Edition


# CustomerMS
We will first start by creating the CustomerMS. This MS will talk to an in memory instance of SQL Database- H2 to fetch the customer details.
We will be exposing all the CRUD methods on a Customer, 
The purpose of this MS will be to take Customer ID as input and return his AccountID.

## Create a SpringBoot Project Skeleton Project

 Use either from start.spring.io or do directly from inside the STS - Spring Started Project

GroupIs - com.example
ArtifactID - CustomerMS
Package - com.example.customer

Add the dependencies as:
Spring Version - 2.3.3
1. Spring web
2. Spring DevTools
3. SpringBoot Actuator
4. Spring Data JPA
5. H2 Database

Let the initial build run in the IDE. THis will take some time.









