package com.martforchange.demo.login;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.martforchange.demo.customers.CustomerDTO;
import com.martforchange.demo.customers.CustomerRepository;
import com.martforchange.demo.deliverypersonlink.DeliveryDisplayDetails;
import com.martforchange.demo.deliverypersonlink.DeliveryPersonLink;
import com.martforchange.demo.deliverypersonlink.DeliveryPersonLinkDTO;
import com.martforchange.demo.deliverypersonlink.DeliveryPersonLinkRepository;
import com.martforchange.demo.exceptions.InvalidDataExceptions;
import com.martforchange.demo.exceptions.NullPointerExceptions;
import com.martforchange.demo.items.Item;
import com.martforchange.demo.register.RegisterDTO;
import com.martforchange.demo.register.RegisterRepository;

@Service
public class LoginService {
	
	@Autowired
	private RegisterRepository registerRepository;
	@Autowired
	private DeliveryPersonLinkRepository deliveryPersonLinkRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	
	public DeliveryDisplayDetails checkAndGetDeliveryDetails(Login user) {
		RegisterDTO registeredUser=registerRepository.findByEmailId(user.getUserName());
		if(registeredUser==null) {
			throw new NullPointerExceptions("User is not present");
		}
		else if((registeredUser!=null) && (!registeredUser.getPassword().equals(user.getPassword()))){
			throw new InvalidDataExceptions("Invalid User Credentials");
		}
		else if(registeredUser!=null && registeredUser.getRole().toUpperCase().equals("DELIVERY")) {
			DeliveryPersonLinkDTO deliveryPersonLinkDTO=deliveryPersonLinkRepository.findByDeliveryPersonID(registeredUser.getUserId());
		/*	if(deliveryPersonLinkDTO==null) {
				throw new NullPointerExceptions("User is not present");
			}*/
			Optional<CustomerDTO> customer=customerRepository.findById(deliveryPersonLinkDTO.getCustomer_id());
			if(customer==null) {
				throw new NullPointerExceptions("Customer is not present");
			}
			DeliveryDisplayDetails deliveryDisplayDetails=new DeliveryDisplayDetails();
			deliveryDisplayDetails.setDeliveryDetails(convertFromDTO(deliveryPersonLinkDTO));
			deliveryDisplayDetails.setCustomerDetails(customer);
			deliveryDisplayDetails.setOrderId(deliveryPersonLinkDTO.getId());
			return deliveryDisplayDetails;
		}
		return null;
	}
	public RegisterDTO authenticate(String userName,String password) {
		RegisterDTO registeredUser=registerRepository.findByEmailId(userName);
		//String decodedPassword = new String(Base64.getDecoder().decode(registeredUser.getPassword()));
		if((registeredUser!=null) && (!registeredUser.getPassword().equals(password))){
			throw new InvalidDataExceptions("Invalid User Credentials");
		}
		return registeredUser;
	}
	private DeliveryPersonLink convertFromDTO(DeliveryPersonLinkDTO deliveryPersonLinkDTO) {
		DeliveryPersonLink deliveryPerson=new DeliveryPersonLink();
		deliveryPerson.setCustomerId(deliveryPersonLinkDTO.getCustomer_id());
		deliveryPerson.setDeliveryPersonId(deliveryPersonLinkDTO.getDelivery_person_id());
		deliveryPerson.setOrderStatus(deliveryPersonLinkDTO.getOrder_status());
		String clobData = deliveryPersonLinkDTO.getConfigJson();

		ObjectMapper objectMapper = new ObjectMapper();
		//JsonNode jsonNode = objectMapper.readTree(clobData);
		
		try {
			List<Item> itemList=objectMapper.readValue(clobData, new TypeReference<List<Item>>(){});
			deliveryPerson.setItems(itemList);
			//deliveryPerson.setItems(itemsList);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deliveryPerson;
	}
}

