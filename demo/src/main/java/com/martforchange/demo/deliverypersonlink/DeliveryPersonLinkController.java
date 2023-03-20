package com.martforchange.demo.deliverypersonlink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryPersonLinkController {
	@Autowired 
	private DeliveryPersonLinkService deliveryPersonLinkService;
	
	@RequestMapping(method=RequestMethod.POST,value="/deliverylink")
	public ResponseEntity<DeliveryPersonLink> addAndLinkDelivery(@RequestBody DeliveryPersonLink linkObj) throws Exception {
		deliveryPersonLinkService.addAndLinkDelivery(linkObj);
		return new ResponseEntity<DeliveryPersonLink>(linkObj, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/deliverystatuschange")
	public ResponseEntity <DeliveryStatus>deliveryStatusChange(@RequestBody DeliveryStatus statusObj) {
		boolean isChanged = false;
		try {
			isChanged = deliveryPersonLinkService.editOrderStatus(statusObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isChanged) {
			return new ResponseEntity<DeliveryStatus>(statusObj, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<DeliveryStatus>(statusObj, HttpStatus.OK);
		}
	}
}

