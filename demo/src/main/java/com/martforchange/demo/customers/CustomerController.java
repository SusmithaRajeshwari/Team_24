package com.martforchange.demo.customers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.martforchange.demo.deliverypersonlink.DeliveryPersonLink;
import com.martforchange.demo.deliverypersons.DeliveryPersons;
import com.martforchange.demo.login.LoginService;
import com.martforchange.demo.register.RegisterDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CustomerController {

	@Autowired 
	private  CustomerService customerService;
	
	@RequestMapping(method=RequestMethod.GET,value="/fetchCusomerDetails/{customeridentity}")
	public ResponseEntity<CustomerDTO> getAuthentication(HttpServletRequest request,
			@PathVariable("customeridentity") String customeridentity) throws IOException{
		CustomerDTO customer=null;
		customer=customerService.getCustomerDetails(customeridentity);
		if(customer!=null) {
			return new ResponseEntity <CustomerDTO>(customer,HttpStatus.OK);
		}
		else {
			return new ResponseEntity <CustomerDTO>(customer,HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/addNewCustomer")
	public ResponseEntity<CustomerDTO> addNewCustomer(@RequestBody Customer customerObj) throws Exception {
		CustomerDTO cus=customerService.addNewCustomer(customerObj);
		if(cus!=null)
			return new ResponseEntity<CustomerDTO>(cus, HttpStatus.OK);
		else
			return new ResponseEntity<CustomerDTO>(cus, HttpStatus.EXPECTATION_FAILED);
	}

}
