package com.martforchange.demo.login;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.martforchange.demo.deliverypersonlink.DeliveryDisplayDetails;
import com.martforchange.demo.deliverypersonlink.DeliveryPersonLink;
import com.martforchange.demo.register.Register;
import com.martforchange.demo.register.RegisterDTO;
import com.martforchange.demo.register.RegisterService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
	
	@Autowired 
	private LoginService loginService;
	@RequestMapping(method=RequestMethod.POST,value="/login")
	public ResponseEntity<DeliveryDisplayDetails> getDeliveryOrderList(@RequestBody Login user) {
		DeliveryDisplayDetails deliveryDisplayObj=loginService.checkAndGetDeliveryDetails(user);
		if(deliveryDisplayObj!=null)
		return new ResponseEntity <DeliveryDisplayDetails>(deliveryDisplayObj,HttpStatus.OK);
		else
			return null;
	}
	

	@GetMapping("/loginmethod/{username}/{password}")
	public ResponseEntity<RegisterDTO> getAuthentication(HttpServletRequest request,
			@PathVariable("username") String username,
			@PathVariable("password") String password) throws IOException{
		RegisterDTO registeredUser=loginService.authenticate(username,password);
		return new ResponseEntity <RegisterDTO>(registeredUser,HttpStatus.OK);
	}

}
