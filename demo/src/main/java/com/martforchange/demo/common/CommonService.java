package com.martforchange.demo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CommonService {

	@Autowired
	private static JavaMailSender javaMailSender;
	
	@Autowired
	private static TemplateEngine templateEngine;
	
	public static void sendEmail1(String to, String name,String templateName) throws MessagingException {
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    helper.setTo(to);
	    helper.setSubject("Welcome");
	    Context context = new Context();
	    context.setVariable("name", name);
	    String html = templateEngine.process(templateName, context);
	    helper.setText(html, true);
	    javaMailSender.send(message);
	}
}
