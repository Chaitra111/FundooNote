package com.bridgelabz.ToDo_1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bridgelabz.ToDo_1.note.interceptor.NoteInterceptor;
import com.bridgelabz.ToDo_1.userservice.loggerinterceptor.LoggerInterceptor;


@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer{
   
    @Autowired
    private LoggerInterceptor loggerInterceptor;
    
   	@Autowired
    private NoteInterceptor noteInterceptor;
   	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      
        registry.addInterceptor( loggerInterceptor);
        registry.addInterceptor( noteInterceptor).addPathPatterns("/notes/**");
    }
   
}
