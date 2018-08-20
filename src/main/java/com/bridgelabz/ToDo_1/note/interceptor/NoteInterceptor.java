package com.bridgelabz.ToDo_1.note.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgelabz.ToDo_1.userservice.model.User;
import com.bridgelabz.ToDo_1.userservice.repository.UserRepository;
import com.bridgelabz.ToDo_1.utility.Utility;
import com.bridgelabz.ToDo_1.utility.exceptionService.UserExceptionHandler;

import io.jsonwebtoken.Claims;

/*@Component
public class NoteInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userrepository;
    
    @Autowired
    Utility utility;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(request.getHeader("Authorization"));
		Claims email = utility.parseJwt(request.getHeader("Authorization"));
		System.out.println(email.getId());
		Optional<User> optional = userrepository.findById(email.getId());
  
		System.out.println(optional.get().getEmailId());
		
		if (!optional.isPresent()) {
			throw new UserExceptionHandler("Invalid Token");
		}
		request.setAttribute("Authorization", email.getId());
		logger.info("Before handling the request");
		return true;
	}*/

@Component
public class NoteInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private UserRepository userrepository;
    
    @Autowired
    Utility utility;

    
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("token:"+request.getHeader("Authorization"));
		Claims claims = utility.parseJwt(request.getHeader("Authorization"));
		//System.out.println("id:"+claims.getSubject());
		Optional<User> optional = userrepository.findByemailId(claims.getId());
		System.out.println("id"+optional.get().getUserId());
		if (!optional.isPresent()) {
			throw new UserExceptionHandler("Invalid Token");
		}
		request.setAttribute("Authorization", claims.getId());
		logger.info("Before handling the request");
		return true;
    }
}
