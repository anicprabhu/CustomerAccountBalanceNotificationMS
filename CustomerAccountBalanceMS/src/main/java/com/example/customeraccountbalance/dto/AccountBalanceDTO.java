package com.example.customeraccountbalance.dto;

public class AccountBalanceDTO {


	private String accountID;	

	private Integer accountBalance;

	public String getAccountID() {
		return accountID;
	}

	public Integer getAccountBalance() {
		return accountBalance;
	}

	@Override
	public String toString() {
		return "AccountBalanceDTO [accountID=" + accountID + ", accountBalance=" + accountBalance + "]";
	}

	public AccountBalanceDTO(String accountID, Integer accountBalance) {
		super();
		this.accountID = accountID;
		this.accountBalance = accountBalance;
	}

	public AccountBalanceDTO() {
		super();
	}
	
	
}
