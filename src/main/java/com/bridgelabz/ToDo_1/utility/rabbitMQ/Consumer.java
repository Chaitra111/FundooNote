package com.bridgelabz.ToDo_1.utility.rabbitMQ;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.ToDo_1.userservice.dto.MailModel;
import com.bridgelabz.ToDo_1.utility.email.EmailServices;

/**
 * @author Chaitra Ankolekar
 * Purpose :Consumer class to send mail
 */
@Service
public class Consumer 
{
	@Autowired
	EmailServices emailService;
	@RabbitListener(queues="${jsa.rabbitmq.queue}")
	public void recivedMessage(MailModel mail) throws MessagingException 
	{
		emailService.sendMail(mail);
		System.out.println("mail= "+mail);
	}

}