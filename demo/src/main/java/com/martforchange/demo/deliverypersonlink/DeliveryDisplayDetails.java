package com.martforchange.demo.deliverypersonlink;

import java.util.Optional;

import com.martforchange.demo.customers.Customer;
import com.martforchange.demo.customers.CustomerDTO;

public class DeliveryDisplayDetails {

	private DeliveryPersonLink deliveryDetails;
	private Optional<CustomerDTO> customerDetails;
	private int orderId;
	public DeliveryDisplayDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeliveryPersonLink getDeliveryDetails() {
		return deliveryDetails;
	}
	public void setDeliveryDetails(DeliveryPersonLink deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}
	public Optional<CustomerDTO> getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(Optional<CustomerDTO> customer) {
		this.customerDetails = customer;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	
}
