package com.bridgelabz.ToDo_1.utility.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bridgelabz.ToDo_1.userservice.dto.MailModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :To send mail
 */
@Component
public class EmailServiceImplementation implements EmailServices {

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void sendMail(MailModel mail) throws MessagingException, MailException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

		message.setTo(mail.getToMailAddress());
		//message.setTo(mail.getSubject());
		message.setSubject(mail.getSubject());
		message.setText(mail.getBody());
		emailSender.send(mimeMessage);
	}
	/*@Override
	public void sendEmail(String to, String subject, String body) throws MessagingException
	{

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		emailSender.send(mimeMessage);
	}*/

}