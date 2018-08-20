package com.bridgelabz.ToDo_1;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes=ToDo1Application.class)
public class ToDo_1ApplicationTest {

	  private MockMvc mockMvc;

	    @Autowired
	    private WebApplicationContext wac;

	    @Before
	    public void setup() {
	        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	    }

	    @Test
	    public void loginTest() throws Exception {
	        mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON)
	                .content("{ \"emailId\": \"111chaitra@gmail.com\", \"password\" : \"chaitra111\"}")
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(jsonPath("$.message").value("Login successfull"));
	    }

		/*@Test
	    public void registerTest() throws Exception {
	        mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON)
	        		.content("{\"emailId\" : \"medinipd@gmail.com\", \"password\" : \"medini13\",\"phoneNumber\":\"9866456878\",\"userId\": \"3\",\"userName\":\"medini\" }")
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(jsonPath("$.message").value("user with this email id already exists!!!"));
	                //.andExpect(jsonPath("$.status").value(200));
	    }*/
		
		/*@Test
	    public void  resetPasswordTest() throws Exception {
	        mockMvc.perform(MockMvcRequestBuilders.post("/save").contentType(MediaType.APPLICATION_JSON).content(
	        		 "{\"newPassword\" : \"111chaitra\",\"confirmPassword\": \"111chaitra\" }")
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(jsonPath("$.message").value("password changed successfully!!!"))
	                .andExpect(jsonPath("$.status").value(200));
	    }*/
}

