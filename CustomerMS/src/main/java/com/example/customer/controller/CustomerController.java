package com.example.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.model.CustomerModel;
import com.example.customer.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customer")
	public List<CustomerModel> getAllCustomers(){
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/customer/{customerId}")
	public CustomerModel getCustomerById(@PathVariable("customerId") Integer customerId) {
		
		return customerService.getCustomerById(customerId);
	}
	
	@PostMapping("/customer")
	public CustomerModel saveOrUpdateCustomer(@RequestBody CustomerModel customer) {
		return customerService.saveOrUpdateCustomer(customer);
	}
	
	@DeleteMapping("/customer/{customerId}")
	public void deleteCustomerById(@PathVariable("customerId") Integer customerId) {
		customerService.deleteCustomerbyId(customerId);
	}

}
