package com.martforchange.demo.deliverypersonlink;

import java.util.List;

import com.martforchange.demo.items.Item;
public class DeliveryPersonLink {

	private int customerId;
	private int deliveryPersonId;
	private String orderStatus;
	private List<Item> items;
	
	
	public DeliveryPersonLink(int customerId, int deliveryPersonId,String orderStatus, List<Item> items) {
		super();
		this.customerId = customerId;
		this.deliveryPersonId=deliveryPersonId;
		this.orderStatus = orderStatus;
		this.items = items;
	}
	public DeliveryPersonLink() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getDeliveryPersonId() {
		return deliveryPersonId;
	}
	public void setDeliveryPersonId(int deliveryPersonId) {
		this.deliveryPersonId = deliveryPersonId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> itemsList) {
		this.items = itemsList;
	}
	
	
}
