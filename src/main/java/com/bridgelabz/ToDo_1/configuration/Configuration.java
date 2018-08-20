package com.bridgelabz.ToDo_1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Configuration {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		
		Resource resource;
		String activeProfile;

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

		// get active profile
		activeProfile = System.getProperty("spring.profiles.active");

		// choose different property files for different active profile
		if ("development".equals(activeProfile)) {
			resource = new ClassPathResource("/files/applicationDevelopment.properties");
			System.out.println(activeProfile + " profile selected");
		} else if ("test".equals(activeProfile)) {
			resource = new ClassPathResource("/files/applicationTest.properties");
			System.out.println(activeProfile + " profile selected");
		} else {
			resource = new ClassPathResource("/files/applicationProduction.properties");
			System.out.println(activeProfile + " profile selected");
		}

		// load the property file
		propertySourcesPlaceholderConfigurer.setLocation(resource);
		return propertySourcesPlaceholderConfigurer;
	}
}
