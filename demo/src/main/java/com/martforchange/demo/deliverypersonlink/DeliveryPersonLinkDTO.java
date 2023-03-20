package com.martforchange.demo.deliverypersonlink;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "Order_Delivery_Link")
public class DeliveryPersonLinkDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String order_status;
	private int customer_id;
	private int delivery_person_id;
	
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	@Lob
	@Column(name = "CONFIG_JSON", columnDefinition = "LONGTEXT")
	@JsonIgnore
	private String configJson;
	public DeliveryPersonLinkDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeliveryPersonLinkDTO(int id, int delivery_person_id, String order_status, int customer_id, String configJson) {
		super();
		this.id = id;
		this.delivery_person_id = delivery_person_id;
		this.order_status = order_status;
		this.customer_id = customer_id;
		this.configJson = configJson;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDelivery_person_id() {
		return delivery_person_id;
	}
	public void setDelivery_person_id(int delivery_person_id) {
		this.delivery_person_id = delivery_person_id;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getConfigJson() {
		return configJson;
	}
	public void setConfigJson(String configJson) {
		this.configJson = configJson;
	}
	
	
	
}
