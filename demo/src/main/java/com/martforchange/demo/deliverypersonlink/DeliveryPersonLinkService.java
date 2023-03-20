package com.martforchange.demo.deliverypersonlink;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.martforchange.demo.common.CommonService;
import com.martforchange.demo.customers.CustomerDTO;
import com.martforchange.demo.customers.CustomerRepository;
import com.martforchange.demo.items.Item;
import com.martforchange.demo.register.Register;
import com.martforchange.demo.register.RegisterDTO;
import com.martforchange.demo.register.RegisterRepository;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class DeliveryPersonLinkService {
	@Autowired
	private DeliveryPersonLinkRepository deliveryPersonLinkRepository;
	@Autowired
	private RegisterRepository registerRepository;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public DeliveryPersonLink addAndLinkDelivery(DeliveryPersonLink linkObj) throws Exception {
		
		DeliveryPersonLinkDTO deliveryPersonLinkDTOConverted= convertIntoDTO(linkObj);
		RegisterDTO registeredUser=registerRepository.findById(linkObj.getDeliveryPersonId()).get();
		registeredUser.setAccountStatus("InActive");
		try {
			deliveryPersonLinkRepository.save(deliveryPersonLinkDTOConverted);
			registerRepository.save(registeredUser);
			String subject="MART FOR CHANGE-ORDER ASSIGNED";
			//sendNotification();
			sendEmail(registeredUser.getEmailAddress(),registeredUser.getFirstName(),"orderassigned",subject,null);
		}
		catch(Exception exception){
			throw exception;
		}
		return linkObj;
		
		
	}
	public void sendNotification() {
		Session session=getSMTPSession();
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("martforchange@gmail.com"));
			message.addRecipients(Message.RecipientType.TO,"divyat50@gmail.com");
			Transport transport = session.getTransport("smtp");   
			transport.send(message);
			transport.close();  
			//message.setFrom(new InternetAddress(SMTP_FROM_ADDRESS));
		}catch (MessagingException ex) {
			ex.printStackTrace();
		}catch (Exception ae) {
			ae.printStackTrace();
		} 
	}
	public Session getSMTPSession()
	{
		Properties props = new Properties();
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port",587);
		

		return Session.getInstance(props);		
	}
	public DeliveryPersonLinkDTO convertIntoDTO(DeliveryPersonLink linkObj ) {
		DeliveryPersonLinkDTO deliveryPersonLinkDTO=new DeliveryPersonLinkDTO();
		ObjectMapper objectMapper = new ObjectMapper();
		
		List<Item> items=linkObj.getItems();
		deliveryPersonLinkDTO.setCustomer_id(linkObj.getCustomerId());
		deliveryPersonLinkDTO.setDelivery_person_id(linkObj.getDeliveryPersonId());
		deliveryPersonLinkDTO.setOrder_status(linkObj.getOrderStatus());
		try {
			deliveryPersonLinkDTO.setConfigJson(objectMapper.writeValueAsString(items));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deliveryPersonLinkDTO;
	}
	public Boolean editOrderStatus(DeliveryStatus statusObj) throws Exception {
		
		DeliveryPersonLinkDTO deliveryDetailsObj=deliveryPersonLinkRepository.findById(statusObj.getOrder_id()).get();
		deliveryDetailsObj.setOrder_status(statusObj.getOrder_status());
		try {
			deliveryPersonLinkRepository.save(deliveryDetailsObj);
			CustomerDTO customer=customerRepository.findById(deliveryDetailsObj.getCustomer_id()).get();
			String subject="Order Status Change";
			sendEmail(customer.getCustomer_email(),customer.getCustomer_name(),"orderstatuschange",subject,statusObj);
			
		}
		catch(Exception exception){
			throw exception;
		}
		
		return true;
	}
	public void sendEmail(String to, String name,String templateName,String subject,DeliveryStatus statusObj) throws MessagingException {
	    MimeMessage message = null;
		try {
			message = javaMailSender.createMimeMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    helper.setTo(to);
	    helper.setSubject(subject);
	    Context context = new Context();
	    context.setVariable("name", name);
	    if(templateName.toUpperCase().equals("ORDERSTATUSCHANGE")) {
	    	  context.setVariable("orderID", statusObj.getOrder_id());
	    	  context.setVariable("status", statusObj.getOrder_status());
	    }
	    String html = templateEngine.process(templateName, context);
	    helper.setText(html, true);
	    javaMailSender.send(message);
	}
}
