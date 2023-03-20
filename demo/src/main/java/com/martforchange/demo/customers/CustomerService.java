package com.martforchange.demo.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.martforchange.demo.deliverypersonlink.DeliveryDisplayDetails;
import com.martforchange.demo.exceptions.InvalidDataExceptions;
import com.martforchange.demo.login.Login;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	public CustomerDTO getCustomerDetails(String customerIdentity)
	{
		CustomerDTO customer=customerRepository.findByIdOrEmail(customerIdentity);
		return customer;
	}
	public CustomerDTO addNewCustomer(Customer cusObj)
	{
		CustomerDTO cusObjDTO=new CustomerDTO();
		cusObjDTO.setCustomer_address(cusObj.getCustomer_address());
		cusObjDTO.setCustomer_email(cusObj.getCustomer_email());
		cusObjDTO.setCustomer_name(cusObj.getCustomer_name());
		cusObjDTO.setCustomer_phone_number(cusObj.getCustomer_phone_number());
		CustomerDTO customer=null;
		try {
			customer=customerRepository.findByEmailId(cusObjDTO.getCustomer_email());
			if(customer!=null) {
				throw new InvalidDataExceptions("Email ID already Exists");
			}
			else
				customer=customerRepository.save(cusObjDTO);
		// customer=customerRepository.findByEmailId(cusObjDTO.getCustomer_email());
		 
		}
		catch(Exception exception){
			throw exception;
		}
		return customer;
	}
	

}
