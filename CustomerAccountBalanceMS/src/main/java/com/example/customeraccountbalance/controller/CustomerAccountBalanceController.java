package com.example.customeraccountbalance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.customeraccountbalance.service.CustomerAccountBalanceService;

@RestController
public class CustomerAccountBalanceController {
	
	@Autowired
	CustomerAccountBalanceService customerAccountBalanceService;
	
	@GetMapping("/customeraccountbalance/{customerId}")
	public Integer getCustomerAccountBalance(@PathVariable("customerId") Integer customerId) {
		return customerAccountBalanceService.getCustomerAccountBalance(customerId);
		
	}

}
