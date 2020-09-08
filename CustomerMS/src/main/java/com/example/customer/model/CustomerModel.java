package com.example.customer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CustomerModel {
	
	@Id
	@GeneratedValue
	private Integer customerId;
	
	private String customerName;
	
	private Integer accountId;

	public Integer getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Integer getAccountId() {
		return accountId;
	}

	@Override
	public String toString() {
		return "CustomerModel [customerId=" + customerId + ", customerName=" + customerName + ", accountId=" + accountId
				+ "]";
	}

	public CustomerModel() {
		super();
	}

	public CustomerModel(String customerName, Integer accountId) {
		super();
		this.customerName = customerName;
		this.accountId = accountId;
	}
	
	

}
