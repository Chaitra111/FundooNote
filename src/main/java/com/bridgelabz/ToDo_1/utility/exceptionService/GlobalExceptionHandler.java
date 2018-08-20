package com.bridgelabz.ToDo_1.utility.exceptionService;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.ToDo_1.userservice.dto.Response;
/**
 * @author Chaitra Ankolekar
 * Purpose :GlobalExceptionHandler class
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * @param exception
	 * @return response
	 */
	@ExceptionHandler(UserExceptionHandler.class)
	public ResponseEntity<Response> handleRegistrationException(UserExceptionHandler exception) {
		logger.info("Error occured for: " + exception.getMessage(), exception);
		Response response = new Response();
		response.setMessage(exception.getMessage());
		response.setStatus(-3);
		System.out.println("global exception");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param exception
	 * @return response
	 */
	@ExceptionHandler(LoginExceptionHandler.class)
	public ResponseEntity<Response> handleLoginException(UserExceptionHandler exception) {
		logger.info("Error occured for: " + exception.getMessage(), exception);
		Response response = new Response();
		response.setMessage(exception.getMessage());
		response.setStatus(-3);
		System.out.println("global exception");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param exception
	 * @return response
	 */
	@ExceptionHandler(ToDoException.class)
	public ResponseEntity<Response> handlesetPasswordException(ToDoException exception) {
		logger.info("Error occured: " + exception.getMessage(), exception);
		Response response = new Response();
		response.setMessage(exception.getMessage());
		response.setStatus(-3);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param exception
	 * @param request
	 * @param reqId
	 * @return response
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception exception, HttpServletRequest request) {
		logger.error("Error occured for: " + exception.getMessage(), exception);
		Response response = new Response();
		response.setMessage(exception.getMessage());
				//"Something went wrong");
		response.setStatus(-1);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}