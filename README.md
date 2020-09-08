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

Run the Skeleton project, to make sure that everything is good to go.

Hit the Actuator Endpoint, 
`http://localhost:8080/actuator/health` and ensure the status is `up`

Lets first create a connectivity test for the database, this will make sure we will encounter no issues pertaining to connectivity later and can solely focus on the Business Logic Implementation.

## CustomerModel
Lets Start by creating the Model class `CustomerModel` in package `com.example.customer.model

CustomerModel.java file

```java
package com.example.customer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CustomerModel {
	
	@Id
	@GeneratedValue
	private Integer customerId;
	
	private String customerName;
	
	private Integer accountId;

	public Integer getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Integer getAccountId() {
		return accountId;
	}

	@Override
	public String toString() {
		return "CustomerModel [customerId=" + customerId + ", customerName=" + customerName + ", accountId=" + accountId
				+ "]";
	}

	public CustomerModel() {
		super();
	}

	public CustomerModel(String customerName, Integer accountId) {
		super();
		this.customerName = customerName;
		this.accountId = accountId;
	}
}
```
## CustomerRepository
Next we create the Interface `CustomerRepository` in package `com.example.customer.repository`

```java
package com.example.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.model.CustomerModel;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerModel, Integer>{

}
```

## Connectivity Test
TIme to test the connectovity to the database

Inside `CustomerMsApplication.java`, do following changes

```java
package com.example.customer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.customer.model.CustomerModel;
import com.example.customer.repository.CustomerRepository;

@SpringBootApplication
public class CustomerMsApplication implements CommandLineRunner{
	
	@Autowired
	CustomerRepository customerRepository;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerMsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CustomerMsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<CustomerModel> customers = new ArrayList<>();
		customers.add(new CustomerModel("Arijit", 12345));
		customers.add(new CustomerModel("Shreya", 54321));
		
		//Test the Save Operation to DB
		customerRepository.saveAll(customers);
		log.info("Data Saved");		
	}

}
```

Next we will update the application.properties file with the connection details for the database

```java
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
server.port=8081
```

Lets Start the server and chack if the data is getting saved correctly
Verify the Data saved Message in the Logs.
Connect to DB abd verify the data saved in DB
`http://localhost:8081/h2-console`

Next we will attempt to fetch one of the records

```java

    add the below call in the Aplication Class

 		log.info(customerRepository.findById(1).toString());
```
Restart the application and verify the customer is fetched in the logs


## CustomerController and CustomerService

Create class `CustomerController` in package `com.example.customer.controller`

Create a class `CustomerService` in package `com.example.customer.service`

Annotate the Controller class with @RestController annotation
### Add a getAllCustomers in controller

```java
@RestController
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customer")
	public List<CustomerModel> getAllCustomers(){
		return customerService.getAllCustomers();
	}

}
```

### add getAllCustomers method in the Service
```java

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;

	public List<CustomerModel> getAllCustomers() {
		List <CustomerModel> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customer -> customers.add(customer));		
		return customers;
	}

}
```

### Verify the GetALLCustomers using POSTMAN
Verify the Flow

Start the server

hit endpoint using Postman

GET to URL - http://localhost:8081/customer

Verify the Output




















