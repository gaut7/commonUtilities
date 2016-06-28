package com.mindbowser.CommonUtilities.util;

import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.FORGOT_PASSWORD_EMAIL_PATH;
import static com.mindbowser.CommonUtilities.constant.CommonUtilitiesConstants.REGISTRATION_CONFIRMATION_EMAIL_PATH;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
// Constants


import com.mindbowser.CommonUtilities.dto.EmailDTO;
import com.mindbowser.CommonUtilities.dto.UserDTO;


public class EmailSender {
	Logger logger = Logger.getLogger(EmailSender.class);

	//private MailSender mailSender;
	
    private JavaMailSender mailSender;
    

	@Autowired
    private VelocityEngine velocityEngine;	
	

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	
	/*
	 * Amazon simple mail service starts
	*/	
	//static final String FROM = "pbmindbowser@gmail.com";   // Replace with your "From" address. This address must be verified.
    //static final String TO = "gaurav.tripathi@mindbowser.com";  // Replace with a "To" address. If your account is still in the 
                                                       // sandbox, this address must be verified.
    
    static final String BODY = "This email was sent through the Amazon SES SMTP interface by using Java.";
    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
   
    
	public EmailDTO sendRegistrationConfirmationMail( final EmailDTO email , UserDTO userDTO) throws Exception{
		try{
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
		    
				public void prepare(MimeMessage mimeMessage) throws Exception {
		             MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		             message.setTo(email.getTo());
		             message.setSubject(email.getSubject());
		             message.setSentDate(new Date());
		             
		             
		             Map<String, Object> model = new HashMap<String, Object>();	             
		             String text = VelocityEngineUtils.mergeTemplateIntoString(
				                velocityEngine , 
				                String.format(ResourceManager.getMessage(REGISTRATION_CONFIRMATION_EMAIL_PATH,
				                		null, REGISTRATION_CONFIRMATION_EMAIL_PATH, null)) , 
				                "UTF-8", model);
		             text = text.replace("userName" , email.getUsername());
		             message.setText(text, true);
		          }
		       };
		       mailSender.send(preparator);			
		       return email;
		}catch(Exception ex){
			logger.error(ex.getStackTrace(), ex);
			 return email;
		}
		
		
	}
	
	
	public EmailDTO sendForgotPasswordMail(final EmailDTO email) throws Exception{
		try{
		  MimeMessagePreparator preparator = new MimeMessagePreparator() {
		        //@SuppressWarnings({ "rawtypes" })
				public void prepare(MimeMessage mimeMessage) throws Exception {

		             MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		             message.setTo(email.getTo());
		             message.setSubject("Forgot Password Mail");
		             Map<String, Object> model = new HashMap<String, Object>();	             
		             		             
		             String text = VelocityEngineUtils.mergeTemplateIntoString(
				                velocityEngine , 
				                String.format(ResourceManager.getMessage(FORGOT_PASSWORD_EMAIL_PATH,
				                		null, FORGOT_PASSWORD_EMAIL_PATH, null)) , 
				                "UTF-8", model);
		             text = text.replace("[name]", email.getUsername());
		             text = text.replace("[emailId]", email.getTo());
		             text = text.replace("[password]", email.getPassword());
		             message.setText(text, true);
		          }
		       };
		       mailSender.send(preparator);			
		       return email;
		}catch(Exception ex){
			throw ex;
		}
	}
		
	
	public String readHtmlTemplate(String path)
	{
    	StringBuilder contentBuilder = new StringBuilder();
     try {
         BufferedReader in = new BufferedReader(new FileReader(path));
         String str;
         while ((str = in.readLine()) != null) {
             contentBuilder.append(str);
         }
         in.close();
     } catch (IOException e) {
     }
     String content = contentBuilder.toString();
	return content;
	}
}
