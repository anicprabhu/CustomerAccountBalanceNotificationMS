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
import com.sun.tools.sjavac.Log;

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
		
		//Test the Fetch Operation from DB
		log.info(customerRepository.findById(1).toString());
	}

}
