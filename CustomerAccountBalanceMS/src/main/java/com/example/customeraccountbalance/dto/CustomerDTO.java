package com.example.customeraccountbalance.dto;

public class CustomerDTO {
	
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
		return "CustomerDTO [customerId=" + customerId + ", customerName=" + customerName + ", accountId=" + accountId
				+ "]";
	}

	public CustomerDTO(Integer customerId, String customerName, Integer accountId) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.accountId = accountId;
	}

	public CustomerDTO() {
		super();
	}
	
	

}
