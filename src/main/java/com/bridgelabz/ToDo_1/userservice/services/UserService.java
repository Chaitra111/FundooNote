package com.bridgelabz.ToDo_1.userservice.services;

import java.util.Optional;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.ToDo_1.userservice.dto.ForgotPasswordModel;
import com.bridgelabz.ToDo_1.userservice.dto.MailModel;
import com.bridgelabz.ToDo_1.userservice.model.User;
import com.bridgelabz.ToDo_1.userservice.repository.UserRepository;
import com.bridgelabz.ToDo_1.utility.Messages;
import com.bridgelabz.ToDo_1.utility.PreConditions;
import com.bridgelabz.ToDo_1.utility.Utility;
import com.bridgelabz.ToDo_1.utility.email.EmailServices;
import com.bridgelabz.ToDo_1.utility.exceptionService.LoginExceptionHandler;
import com.bridgelabz.ToDo_1.utility.exceptionService.ToDoException;
import com.bridgelabz.ToDo_1.utility.exceptionService.UserExceptionHandler;
import com.bridgelabz.ToDo_1.utility.rabbitMQ.Producer;

import io.jsonwebtoken.Claims;

/**
 * @author Chaitra Ankolekar
 * Purpose :UserService to perform CRUD operation 
 */

@Service
//@PropertySource("classpath:messages.properties")
public class UserService {
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	EmailServices emailService;
	@Autowired
	Producer producer;
	/*
	 * @Autowired Environment environment;
	 */
	@Autowired
	Messages messages;

	@Autowired
	MailModel mailDto;

	public static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	Utility utility = new Utility();
	ForgotPasswordModel password = new ForgotPasswordModel();
	User users = new User();

	/**
	 * purpose : method to login in to page
	 * @param emailId
	 * @param password
	 * @return User
	 * @throws ToDoException 
	 * @throws UserExceptionHandler 
	 * @throws LoginExceptionHandling
	 */
	
	public void login(String emailId, String password) throws ToDoException  {

		Optional<User> user = repo.findByemailId(emailId);
		System.out.println(emailId);
		PreConditions.isPresentInDb(user.isPresent(), messages.get("12"));
		if (encoder.encode(user.get().getPassword()).equals(password)) {
			logger.info("looged in sucessfully!! continue ");
		}
	}

	/**
	 * purpose:method to register in to a page
	 * @param user
	 * @return boolean
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException 
	 */

	public void registerUser(User user) throws UserExceptionHandler, MessagingException, ToDoException {
		
		Optional<User> checkUser = repo.findById(user.getEmailId());
		PreConditions.isPresentInDb(!checkUser.isPresent(), messages.get("7"));
		user.setEmailId(user.getEmailId());
		user.setUserName(user.getUserName());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setPhoneNumber(user.getPhoneNumber());
		user.setActivate("false");
		repo.save(user);
		repo.findById(user.getEmailId());
		String token = utility.createTokens(user);
		System.out.println(token);
		mailDto.setToMailAddress(user.getEmailId());
		mailDto.setSubject("Hi " + user.getUserName());
		mailDto.setBody("Activation link:" +messages.get("6") + token);
		// emailService.sendMail(mailDto);
		producer.produceMail(mailDto);
		logger.info("mail is sent to emailid");
	}

	/**
	 * method to activate the account
	 * @param jwt
	 * @throws UserExceptionHandler 
	 * @throws LoginExceptionHandler 
	 * @throws ToDoException 
	 */
	public void activateAc(String jwt) throws LoginExceptionHandler, UserExceptionHandler, ToDoException {

		Claims claims = utility.parseJwt(jwt);
		Optional<User> user = repo.findById(claims.getSubject());
		System.out.println(claims.getIssuer());
	    System.out.println(user);
		/*if (!user.isPresent()) {
			throw new UserExceptionHandler(messages.get("9"));
		}*/
		PreConditions.isPresentInDb(user.isPresent(), messages.get("9"));
		user.get().setActivate("true");
		repo.save(user.get());
	}

					
	/**
	 * @param fp
	 * @param tokenJwt
	 * @throws ToDoException
	 */
	public void setPassword(ForgotPasswordModel fp, String tokenJwt) throws ToDoException {

		if (!fp.getNewPassword().equals(fp.getNewPassword())) {
			throw new ToDoException(messages.get("10"));
		}
		Claims claims = utility.parseJwt(tokenJwt);
		Optional<User> checkUser = repo.findById(claims.getIssuer());
		System.out.println(claims.getIssuer());
		User user = checkUser.get();
		user.setPassword(encoder.encode(fp.getConfirmPassword()));
		repo.save(user);
		logger.info("password is set");
	}

	/**
	 * @param emailId
	 * @throws MessagingException
	 * @throws UserExceptionHandler 
	 * @throws LoginExceptionHandler 
	 * @throws ToDoException 
	 */
	public void forgotPassword(String emailId) throws MessagingException, LoginExceptionHandler, UserExceptionHandler, ToDoException {
					
		String jwtToken = utility.createToken(emailId);
		Optional<User> optionalUser = repo.findByemailId(emailId);
		//System.out.println("emailid :"+emailId);
		/*if (!optionalUser.isPresent()) {
			throw new UserExceptionHandler(messages.get("11"));
		}*/
		PreConditions.isPresentInDb(optionalUser.isPresent(), messages.get("12"));
		mailDto.setToMailAddress(emailId);
		mailDto.setSubject("recover your password");
		mailDto.setBody("recover your password by clicking :" + messages.get("8") + jwtToken);
		producer.produceMail(mailDto);
		logger.info("check mail to reset the password");
	}
}
	