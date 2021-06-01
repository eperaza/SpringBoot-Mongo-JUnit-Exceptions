package com.example.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.apache.commons.io.IOUtils;
import java.util.Optional;
import com.example.mongo.apierror.CustomException;
import com.example.mongo.controller.DataController;
import com.example.mongo.model.User;
import com.example.mongo.model.UserRepo;
import com.example.mongo.service.IUserService;
import com.example.mongo.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class MongoApplicationTests {

	@Autowired
	@InjectMocks
	private UserService service;
	
	@Autowired
	DataController controller;

	
	
	@Autowired
	@MockBean
	private UserRepo repo;

	@Autowired 
	ObjectMapper objectMapper;

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void given_ValidUserId_when_calling_getAll_then_return_userDetails() {
		
		try {
			Optional<User> userResponse = null;
			String userString = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("UserResponse.json"), "UTF-8");
			userResponse = objectMapper.readValue(userString, new TypeReference<Optional<User>>() {});
			
			when(repo.findById((String) "101")).thenReturn(userResponse);
			ResponseEntity<Object> response = controller.findById((String)"101");
			
			assertNotNull(response);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		
	}
	/*
	@Test
	public void given_InValidUserId_when_calling_updateUser_then_return_ErrorDetails() {
		
		try {
			 
			when(service.update(eq("-101"), eq("x"), eq("x"))).thenThrow(new RuntimeException());
			ResponseEntity<Object> response = controller.update(eq("-101"), eq("asd"), eq("as"));
			when(repo.findById(String id).thenThrow(new RuntimeException());

			
			
		} catch (CustomException ex) { 
			assertNotNull(ex.getMessage());
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
			assertEquals("Employee Id Should be positive number", ex.getMessage());
		}
		
	}*/

}
