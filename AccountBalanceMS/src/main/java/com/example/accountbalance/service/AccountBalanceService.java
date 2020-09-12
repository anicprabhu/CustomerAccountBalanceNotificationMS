package com.example.accountbalance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.accountbalance.model.AccountBalanceModel;
import com.example.accountbalance.repository.AccountBalanceRepository;

@Service
public class AccountBalanceService {

	@Autowired
	AccountBalanceRepository accountBalanceRepository;
	
	public AccountBalanceModel getAccountBalance(String accountId) {

		return accountBalanceRepository.findById(accountId).get();
	}
	
	public AccountBalanceModel saveOrUpdateAccountBalance(AccountBalanceModel accountBalanceModel) {
		return accountBalanceRepository.save(accountBalanceModel);
	}

	public void deleteAccountBalance(String accountId) {
		accountBalanceRepository.deleteById(accountId);
	}
}
