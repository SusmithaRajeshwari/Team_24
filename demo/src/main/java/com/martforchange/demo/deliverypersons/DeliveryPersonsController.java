package com.martforchange.demo.deliverypersons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class DeliveryPersonsController {

	@Autowired 
	private DeliveryPersonsService deliveryPersonsService;
	
	@RequestMapping(method=RequestMethod.GET,value="/DeliveryPersonsList")
	public ResponseEntity<List<DeliveryPersons>> getDeliveryPersonsList() {
		List<DeliveryPersons> deliveryPersonsList=null;
		deliveryPersonsList=deliveryPersonsService.getDeliveryPersonsList();
		return new ResponseEntity <List<DeliveryPersons>>(deliveryPersonsList,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/viewAllOrders")
	public ResponseEntity<List<ViewAllOrders>> getAllOrders() {
		List<ViewAllOrders> ordersList=null;
		ordersList=deliveryPersonsService.getAllOrders();
		return new ResponseEntity <List<ViewAllOrders>>(ordersList,HttpStatus.OK);
	}
	
}
