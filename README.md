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

Verify the Output as below

```json
[
    {
        "customerId": 1,
        "customerName": "Arijit",
        "accountId": 12345
    },
    {
        "customerId": 2,
        "customerName": "Shreya",
        "accountId": 54321
    }
]
```

### Add the GetCustomerbyID Method

add the below method in CustomerController

```java
	@GetMapping("/customer/{customerId}")
	public CustomerModel getCustomerById(@PathVariable("customerId") Integer customerId) {
		
		return customerService.getCustomerById(customerId);
	}
```

Add the corresponding calling method in CustomerService

```java
	public CustomerModel getCustomerById(Integer customerId) {
			return customerRepository.findById(customerId).get();
	}
```

Restart the server

Hit the postman GET Endpoint with URL `http://localhost:8081/customer/2`

the Output will be like below

```json
{
    "customerId": 2,
    "customerName": "Shreya",
    "accountId": 54321
}

```

### Add the saveorUpdateCustomer Method

Add the below method in CustomerController

```java
@PostMapping("/customer")
	public CustomerModel saveOrUpdateCustomer(@RequestBody CustomerModel customer) {
		return customerService.saveOrUpdateCustomer(customer);
	}

```

Add the corresponding calling method in CustomerService

```java
	public CustomerModel saveOrUpdateCustomer(CustomerModel customer) {
		return customerRepository.save(customer);
	}

```

restart the server

### Test the SaveCustomer Operation
Hit Get to `http://localhost:8081/customer/2`

Edit the response json to create new entity


In Postman, select Request Type as - POST, URL as `http://localhost:8081/customer`, Body as ray, content type as application/json

```json
{
    "customerName": "Sonu",
    "accountId": 11111
}
```

Ensure you get response as

```json
{
    "customerId": 3,
    "customerName": "Sonu",
    "accountId": 11111
}
```

reverify by hitting the GetAllCustomers `http://localhost:8081/customer`


### Test the UpdateCustomer Functionality

Send the POST request to `http://localhost:8081/customer` with Below Payload

```json
{
    "customerId": 3,
    "customerName": "Sonu",
    "accountId": 22222
}
```
reverify by hitting the GetAllCustomers `http://localhost:8081/customer`


### Add the DeleteCustomer Functionality

Add the below method in CustomerController

```java
@DeleteMapping("/customer/{customerId}")
	public void deleteCustomerById(@PathVariable("customerId") Integer customerId) {
		customerService.deleteCustomerbyId(customerId);
	}
```

Add the corresponding calling method in CustomerService

```java
public void deleteCustomerbyId(Integer id) {		
		customerRepository.deleteById(id);		 
	}
```
Test the Delete Endpoint in Postman

DELETE, URL - http://localhost:8081/customer/2

Verify - http://localhost:8081/customer


# AccountBalanceMS

Next we proceed to creating the AccountBalanceMS


## First we need to start by installing Couchbase

1. Download the Couchbase Community edition
2. Install with default config
3. Launch the Home page at http://localhost:8091/ui/index.html
4. create a new cluster named AccountBalanceCluster, with Usernme and pasword as Administrator
5. In the cluster, create bucket, AccountBalanceBucket, with default congigs.


## Next create a Spring boot Starter Project for AccountBalanceMS
1. Name as AccountBalanceMS, package as com.example.accoutbalance
2. Add dependencies as spring web, dev tools, actuator, Apring Data Couchbase


## RUn the skeleton

## Create AccountBalance Model
1. Create a new Class AccountBalanceModel in Package com.example.accoutbalance.model

2. Annotate the class with @Document Annotation

3. Add the below fields and Annotations

```java
	@Id
	private String accountID;
	
	@Field
	private Integer accountBalance;

```


## Create AccountBalance Repository
```java
	public interface AccountBalanceRepository extends CouchbaseRepository<AccountBalanceModel, String>{

}

```

	
## Test the repository connection

```java
@SpringBootApplication
public class AccountBalanceMsApplication implements CommandLineRunner{
	
	@Autowired
	AccountBalanceRepository accountBalanceRepository;
	
	private static final Logger log = LoggerFactory.getLogger(AccountBalanceMsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccountBalanceMsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<AccountBalanceModel> accountBalances = new ArrayList<>();
		accountBalances.add(new AccountBalanceModel("12345", 100000));
		accountBalances.add(new AccountBalanceModel("54321", 200000));
		//Save test data to repository
		accountBalanceRepository.saveAll(accountBalances);
		
		//Retrieve test data
		log.info("Fetched the record from Couchbase - "+ accountBalanceRepository.findById("12345").get());		
		
	}

}
```
### Add the config Data

```java
spring.couchbase.connection-string=couchbase://127.0.0.1
spring.couchbase.username=Administrator
spring.couchbase.password=Administrator
spring.data.couchbase.bucket-name=AccountBalanceBucket
spring.data.couchbase.auto-index=true
server.port=8082

```
### Run the application 

1. Verify in the Logs that the Record is printed.

2. Verify in the Couchbase console that the Records are available

### Add the Controller and Service Classes

Create Controller and Service Class

Annotate both classes

Write the service class
```java
@Service
public class AccountBalanceService {

	@Autowired
	AccountBalanceRepository accountBalanceRepository;
	
	public AccountBalanceModel getAccountBalance(String accountId) {

		return accountBalanceRepository.findById(accountId).get();
	}
}

```

Write the Controller Class
```java
@RestController
public class AccountBalanceController {
	
	@Autowired
	AccountBalanceService accountBalanceService;
	
	@GetMapping("/accountBalance/{accountId}")
	public AccountBalanceModel getAccountBalance(@PathVariable("accountId") String accountId) {
		return accountBalanceService.getAccountBalance(accountId);
	}	
}
```

Hit the Request

http://localhost:8082/accountBalance/12345


Get the error below

```json
{
    "timestamp": "2020-09-12T07:18:45.312+00:00",
    "status": 404,
    "error": "Not Found",
    "message": "No message available",
    "path": "/accountBalance/12"
}
```

It is because we do not have the index,

use the below command to define the index.
```sql
CREATE INDEX `AccountBalanceBucket` ON `AccountBalanceBucket`(`_class`) WHERE (`_class` = "AccountBalanceBucket.AccountBalanceModel") 
```

Restart the service 

Hit the Request

http://localhost:8082/accountBalance/12345

```json
	{
		"accountID": "12345",
		"accountBalance": 100000
	}
```

Next Implement the saveOrUpdateAccountBalance

Below is the code in Service class

```java
	public AccountBalanceModel saveOrUpdateAccountBalance(AccountBalanceModel accountBalanceModel) {
		return accountBalanceRepository.save(accountBalanceModel);
	}


```

Add below code in Controller Class

```java
	@PostMapping("/accountBalance")
	public AccountBalanceModel saveOrUpdateAccountBalance(@RequestBody AccountBalanceModel accountBalanceModel) {		
		return accountBalanceService.saveOrUpdateAccountBalance(accountBalanceModel);		
	}

```

Test the code

Start the service

Get the request
http://localhost:8082/accountBalance/12345


Post a new request with below Payload

{
    "accountID": "11111",
    "accountBalance": 100000
}

URL as http://localhost:8082/accountBalance

Verify with the Get Rquest

http://localhost:8082/accountBalance/11111


#Delete Code

Write Below code in Service Class
```Java
	public void deleteAccountBalance(String accountId) {
		accountBalanceRepository.deleteById(accountId);
	}

```
Write below code in controller class

```java
	@DeleteMapping("/accountBalance/{accountId}")
	public void deleteAccountBalance(@PathVariable("accountId") String accountId) {
		accountBalanceService.deleteAccountBalance(accountId);
	}
```


Test the code

GET http://localhost:8082/accountBalance/11111


DELETE http://localhost:8082/accountBalance/11111


GET http://localhost:8082/accountBalance/11111

#CustomerAccountBalanceMS

next we will create a MS which will orchestrate the Microservices.

this MS will call customerMS with the given customerId, and find out the corresponding accountId, the using the accountId, I will call AccountBalanceMS to fetch the Balance in the account of the Customer,

Lets start with the MS skeleton,

start a new SpringBoot project with dependencies as spring web, actuator, dev tools, Kafka

Name: CustomerAccountBalanceMS



Create Service and Controller Classes

In the Controller Class
```java
@RestController
public class CustomerAccountBalanceController {
	
	@Autowired
	CustomerAccountBalanceService customerAccountBalanceService;
	
	@GetMapping("/customeraccountbalance/{customerId}")
	public String getCustomerAccountBalance(@PathVariable("customerId") Integer customerId) {
		return customerAccountBalanceService.getCustomerAccountBalance(customerId);
		
	}

}

```


In the Service Class

```java
@Service
public class CustomerAccountBalanceService {

	public String getCustomerAccountBalance(Integer customerId) {
		
		
		//Call CustomerMS
		
		
		//Fetch the AccountId for the Customer
		
		//Call AccountBalanceMS
		
		//Fetch and return the Balance
		
		
		return null;
		
	}

}
```
Create the DTO Classes

CustomerDTO

```java
	private Integer customerId;
	
	private String customerName;
	
	private Integer accountId;
```

AccountBalanceDTO

```java
	private String accountID;	

	private Integer accountBalance;
```


In the Service class, Autowire the rest template

```java
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

```

In the postman, hit request

http://localhost:8081/customer/1

Copy this URL ang wrie below in the service Class

```java
public Integer getCustomerAccountBalance(Integer customerId) {

		String customerMsUrl = "http://localhost:8081/customer/"+customerId;
		
		CustomerDTO customerDTO = restTemplate.getForObject(customerMsUrl, CustomerDTO.class);
		
		String accountBalanceMsUrl = "http://localhost:8082/accountBalance/" + customerDTO.getAccountId();
		
		AccountBalanceDTO accountBalanceDTO = restTemplate.getForObject(accountBalanceMsUrl, AccountBalanceDTO.class);
		
		System.out.println(customerDTO);
		
		System.out.println(accountBalanceDTO);
				
		return accountBalanceDTO.getAccountBalance();
		
	}
```

Send a GET request to http://localhost:8080/customeraccountbalance/1

Resonse should be 100000

# NotificationMS

Next and the last phase is to have notification sent to the customer that his account balance is accessed,

DOwnload Kafka

unzip the distribution

Start the Zookeeper Server
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

Start the Kafka Server
bin\windows\kafka-server-start.bat config\server.properties

Create a topic
bin\windows\kafka-topics.bat --create --topic NotificationQueue --bootstrap-server localhost:9092

Create a listener
bin\windows\kafka-console-consumer.bat --topic NotificationQueue --bootstrap-server localhost:9092

create a producer
bin\windows\kafka-console-producer.bat --topic NotificationQueue --bootstrap-server localhost:9092

send message to from producer
Write Below code in the CuetomerAccountBalanceMS service

```java
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;


	kafkaTemplate.send("NotificationQueue", customerDTO.getCustomerId().toString());

```
Start the Servers, send the request - http://localhost:8080/customeraccountbalance/1

validate the message in the kafka consumer

# NotificationMS

Create a new project with spring web actuator, devtools and kafka as listener,

Creatye a new class HandleCustomerAccountBalanceNotification with Below Code.

```java
@Component
public class HandleCustomerAccountBalanceNotification {

	@KafkaListener(topics="NotificationQueue")
	public void listener(String customerId) {
		System.out.println("Sending Notofication from Customer "+ customerId);
		System.out.println("Notification handled Successgully for customer"+ customerId);
		//return "Notification handled Successgully for customer"+ customerId;
	}

}

```

Add the application.properties
```json
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=myGroup
server.port=8083
```

Start the servers

Hit the request aain and check the MS console logs for the Data
http://localhost:8080/customeraccountbalance/1

configure the class to again call back the producer through message


##Send back the Notification tha the reqeust is handled successfully

Createw New for ProcessedNotifications

Kill the Producer.bat

Create new Producer
bin\windows\kafka-topics.bat --create --topic ProcessedNotifications --bootstrap-server localhost:9092

Create a listener
bin\windows\kafka-console-consumer.bat --topic ProcessedNotifications --bootstrap-server localhost:9092

Alter the HandleCustomerAccountBalanceNotification class

```java
@Component
public class HandleCustomerAccountBalanceNotification {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@KafkaListener(topics="NotificationQueue")
	public void listener(String customerId) {
		System.out.println("HandleCustomerAccountBalanceNotification - Sending Notification from Customer "+ customerId);
		kafkaTemplate.send("ProcessedNotifications", "Notification handled Successfully for customer - "+ customerId);
	}

}
```

Add a listener in the Producer CustomerAccountBalanceService class as well.


spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=myGroup

```java
	@KafkaListener(topics="ProcessedNotifications")
	public void returnMessage(String message) {
		System.out.println("CustomerAccountBalanceMS - "+ message);
	}
```

































Final Integration Test

GET http://localhost:8080/customeraccountbalance/1