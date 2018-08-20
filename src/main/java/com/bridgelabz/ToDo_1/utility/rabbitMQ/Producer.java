package com.bridgelabz.ToDo_1.utility.rabbitMQ;

import com.bridgelabz.ToDo_1.userservice.dto.MailModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :Producer to receive mail
 */
public interface Producer 
	{
		void produceMail(MailModel model);

	}