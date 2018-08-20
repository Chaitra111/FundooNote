package com.bridgelabz.ToDo_1.utility;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * @author Chaitra Ankolekar
 * Purpose :class declares one or more @Bean methods
 */
@Configuration
public class PasswordConfiguration
{
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

}