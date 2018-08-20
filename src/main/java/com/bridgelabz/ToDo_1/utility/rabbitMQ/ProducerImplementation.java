package com.bridgelabz.ToDo_1.utility.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bridgelabz.ToDo_1.userservice.dto.MailModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :ProducerImplementation
 */
@Service
public class ProducerImplementation implements Producer {

	@Autowired 
	private AmqpTemplate amqpTemplate;
	
	@Value("${jsa.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${jsa.rabbitmq.routingkey}")
	private String routingKey;
	
	@Override
	public void produceMail(MailModel mail){
		amqpTemplate.convertAndSend(exchange, routingKey, mail);
		System.out.println("Email sent");
	}
}