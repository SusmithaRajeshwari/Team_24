package com.martforchange.demo.deliverypersons;

import com.martforchange.demo.customers.CustomerDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="order_delivery_link")
public class ViewAllOrders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int orderId;
	private String order_Status;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DELIVERY_PERSON_ID", referencedColumnName = "USER_ID")
	private  DeliveryPersons deliveryPerson;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "id")
	private  CustomerDTO customer;

	
	public ViewAllOrders() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrder_Status() {
		return order_Status;
	}

	public void setOrder_Status(String order_Status) {
		this.order_Status = order_Status;
	}

	public DeliveryPersons getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(DeliveryPersons deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
	
	
}
