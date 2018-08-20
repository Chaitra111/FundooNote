package com.bridgelabz.ToDo_1.userservice.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.ToDo_1.note.repository.RedisRepository;
import com.bridgelabz.ToDo_1.userservice.dto.ForgotPasswordModel;
import com.bridgelabz.ToDo_1.userservice.dto.Response;
import com.bridgelabz.ToDo_1.userservice.model.User;
import com.bridgelabz.ToDo_1.userservice.services.UserService;
import com.bridgelabz.ToDo_1.utility.Utility;
import com.bridgelabz.ToDo_1.utility.exceptionService.LoginExceptionHandler;
import com.bridgelabz.ToDo_1.utility.exceptionService.ToDoException;
import com.bridgelabz.ToDo_1.utility.exceptionService.UserExceptionHandler;
/**
 * @author Chaitra Ankolekar
 * Purpose :UserController with API
 */
@RestController
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService ;
	
	Utility utility = new Utility();
	
	@Autowired
	RedisRepository<String, User> redisRepository;
	/**
	 * purpose:method to login
	 * @param checkUser
	 * @return response
	 * @throws ToDoException 
	 * @throws LoginExceptionHandling
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> login(@RequestBody User checkUser,HttpServletResponse resp)throws UserExceptionHandler, LoginExceptionHandler, ToDoException {
		logger.info("Logging User : {}", checkUser);
		userService.login(checkUser.getEmailId(), checkUser.getPassword());
		String jwtToken = utility.createToken(checkUser.getEmailId());
		Response response = new Response();
		response.setMessage("Login successfull");
		resp.setHeader("token", jwtToken);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * purpose:method to register
	 * @param checkUser
	 * @return response
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException 
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Response> register(@RequestBody User checkUser)
			throws UserExceptionHandler, MessagingException, ToDoException {

		logger.info("Register user : {}", checkUser);
		userService.registerUser(checkUser);
		Response response = new Response();
		response.setMessage("Registration Successfull");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * @param token
	 * @return response
	 * @throws UserExceptionHandler
	 * @throws LoginExceptionHandler
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/activateaccount", method = RequestMethod.GET)
	public ResponseEntity<Response> activateAccount(@RequestParam String token) throws UserExceptionHandler, LoginExceptionHandler, ToDoException {
		logger.info("check the user activation");
		//token = request.getQueryString();
		userService.activateAc(token);
		System.out.println(token);
		redisRepository.saveInRedis(token);
		Response response = new Response();
		response.setMessage("account activated successfully");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	/**
	 * method to send link if user forgot the password
	 * @param CheckUser
	 * @return
	 * @throws MessagingException
	 * @throws UserExceptionHandler
	 * @throws LoginExceptionHandler 
	 * @throws ToDoException 
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<Response> forgotPassword(@RequestParam String email)
			throws MessagingException, UserExceptionHandler, LoginExceptionHandler, ToDoException {

		logger.info("Reset the password");
		userService.forgotPassword(email);
		Response responseDTO = new Response();
		responseDTO.setMessage("send the user mailid to reset password");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	/**
	 * method to reset the password
	 * @param model
	 * @param 
	 * @return response
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> resetPassword(@RequestBody ForgotPasswordModel model, HttpServletRequest req)
			throws ToDoException {
		String token=req.getHeader("Authorization");
	//	String token = req.getQueryString();
		userService.setPassword(model, token);
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("password changed successfully!!!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}