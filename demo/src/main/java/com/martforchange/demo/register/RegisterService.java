package com.martforchange.demo.register;

import  static com.martforchange.demo.utils.AppConstants.REGISTER_SUCCESS;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.martforchange.demo.common.CommonService;
import com.martforchange.demo.exceptions.InvalidDataExceptions;
import com.martforchange.demo.login.Login;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Service
public class RegisterService {

	@Autowired
	private RegisterRepository registerRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public Boolean registerUser(Register user) throws MessagingException {
		RegisterDTO registerDTO=converToDTO(user);
		RegisterDTO registeredUser=registerRepository.findByEmailId(user.getEmailAddress());
		//String decodedPassword = new String(Base64.getDecoder().decode(registeredUser.getPassword()));
		if((registeredUser!=null)){
			throw new InvalidDataExceptions("Email ID already Exists");
		}
		RegisterDTO respObj=registerRepository.save(registerDTO);
		if(respObj!=null) {
			sendEmail(registerDTO.getEmailAddress(),user.getFirstName(),"registration_successful");
		 return true;
		}
		else
			return false;
	}
	
	public void sendEmail(String to, String name,String templateName) throws MessagingException {
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    helper.setTo(to);
	    helper.setSubject("Welcome");
	    Context context = new Context();
	    context.setVariable("name", name);
	    String html = templateEngine.process("registration_successful", context);
	    helper.setText(html, true);
	    javaMailSender.send(message);
	}

	public RegisterDTO converToDTO(Register user) {	
		RegisterDTO userDTO=new RegisterDTO();
		userDTO.setEmailAddress(user.getEmailAddress());
		userDTO.setPassword(user.getPassword());
		userDTO.setAddress(user.getAddress());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setGender(user.getGender());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setRole(user.getRole());
		userDTO.setAccountStatus("Active");
		userDTO.setVerificationStatus("");
		return userDTO;
	}
	
}
