package com.example.accountbalance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
public class AccountBalanceModel {
	
	@Id
	private String accountID;
	
	@Field
	private Integer accountBalance;

	public String getAccountID() {
		return accountID;
	}

	public Integer getAccountBalance() {
		return accountBalance;
	}

	@Override
	public String toString() {
		return "AccountBalanceModel [accountID=" + accountID + ", accountBalance=" + accountBalance + "]";
	}

	public AccountBalanceModel(String accountID, Integer accountBalance) {
		super();
		this.accountID = accountID;
		this.accountBalance = accountBalance;
	}

	public AccountBalanceModel() {
		super();
	}
	
	

}
