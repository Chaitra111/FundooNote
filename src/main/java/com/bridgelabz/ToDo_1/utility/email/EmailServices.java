package com.bridgelabz.ToDo_1.utility.email;

import javax.mail.MessagingException;

import com.bridgelabz.ToDo_1.userservice.dto.MailModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :EmailService class
 */
public interface EmailServices {
	
	//public void sendEmail(String to, String subject, String body) throws MessagingException;
	void sendMail(MailModel mail) throws MessagingException;
}