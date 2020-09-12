package com.example.customeraccountbalance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.customeraccountbalance.dto.AccountBalanceDTO;
import com.example.customeraccountbalance.dto.CustomerDTO;

@Service
public class CustomerAccountBalanceService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public Integer getCustomerAccountBalance(Integer customerId) {
		
		
		//Call CustomerMS
		String customerMsUrl = "http://localhost:8081/customer/"+customerId;
		
		CustomerDTO customerDTO = restTemplate.getForObject(customerMsUrl, CustomerDTO.class);
		
		String accountBalanceMsUrl = "http://localhost:8082/accountBalance/" + customerDTO.getAccountId();
		
		AccountBalanceDTO accountBalanceDTO = restTemplate.getForObject(accountBalanceMsUrl, AccountBalanceDTO.class);
		
		System.out.println(customerDTO);
		
		System.out.println(accountBalanceDTO);
		
		kafkaTemplate.send("NotificationQueue", customerDTO.getCustomerId().toString());
		
		return accountBalanceDTO.getAccountBalance();
		
	}
	
	@KafkaListener(topics="ProcessedNotifications")
	public void returnMessage(String message) {
		System.out.println("CustomerAccountBalanceMS - "+ message);
	}

}
