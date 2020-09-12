package com.example.accountbalance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.accountbalance.model.AccountBalanceModel;
import com.example.accountbalance.service.AccountBalanceService;

@RestController
public class AccountBalanceController {
	
	@Autowired
	AccountBalanceService accountBalanceService;
	
	@GetMapping("/accountBalance/{accountId}")
	public AccountBalanceModel getAccountBalance(@PathVariable("accountId") String accountId) {
		return accountBalanceService.getAccountBalance(accountId);
	}
	
	@PostMapping("/accountBalance")
	public AccountBalanceModel saveOrUpdateAccountBalance(@RequestBody AccountBalanceModel accountBalanceModel) {		
		return accountBalanceService.saveOrUpdateAccountBalance(accountBalanceModel);		
	}

	@DeleteMapping("/accountBalance/{accountId}")
	public void deleteAccountBalance(@PathVariable("accountId") String accountId) {
		accountBalanceService.deleteAccountBalance(accountId);
	}
}
