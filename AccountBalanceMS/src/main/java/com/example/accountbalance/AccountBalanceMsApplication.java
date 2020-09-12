package com.example.accountbalance;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.accountbalance.model.AccountBalanceModel;
import com.example.accountbalance.repository.AccountBalanceRepository;

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
