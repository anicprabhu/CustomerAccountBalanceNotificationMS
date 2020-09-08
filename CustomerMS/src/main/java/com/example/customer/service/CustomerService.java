package com.example.customer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.model.CustomerModel;
import com.example.customer.repository.CustomerRepository;

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
