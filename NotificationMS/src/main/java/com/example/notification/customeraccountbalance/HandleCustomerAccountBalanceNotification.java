package com.example.notification.customeraccountbalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class HandleCustomerAccountBalanceNotification {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@KafkaListener(topics="NotificationQueue")
	public void listener(String customerId) {
		System.out.println("HandleCustomerAccountBalanceNotification - Sending Notification from Customer "+ customerId);
		kafkaTemplate.send("ProcessedNotifications", "Notification handled Successfully for customer - "+ customerId);
	}

}
