package com.example.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.mongo.apierror.CustomException;
import com.example.mongo.controller.DataController;
import com.example.mongo.model.User;
import com.example.mongo.model.UserRepo;
import com.example.mongo.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoApplicationTests {

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
	/*
	@Test
	public void contextLoads() {
		
	}
	*/
	@Test
	public void findAllTest() throws IOException {

		// create object mapper instance
		//ObjectMapper mapper = new ObjectMapper();
		// convert JSON array to list of books
		//List<User> response = Arrays.asList(mapper.readValue(Paths.get("UserResponse.json").toFile(), User[].class));
		
			//String userString = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("UserResponse.json"), "UTF-8");
			//List<User> userResponse  = objectMapper.readValue(userString, new TypeReference<List<User>>() {});
		
		String jsonArray = "[{\"id\":\"ford\"}, {\"name\":\"Fiat\"}, {\"email\":\"Fiat\"}]";
		ObjectMapper objectMapper = new ObjectMapper();
		List<User> res = objectMapper.readValue(jsonArray, new TypeReference<List<User>>(){});
		//when(repo.findAll()).thenReturn(userResponse);
		Mockito.doReturn(res).when(repo).findAll();

		ResponseEntity<Object> response = controller.findAll();

			assertNotNull(response);
			assertEquals(HttpStatus.OK, response.getStatusCode());

			
	}
	

	/*
	@Test
	public void findAllTest() throws IOException {
		Optional<User> userResponse = null;
			String userString = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("UserResponse.json"), "UTF-8");
			userResponse = objectMapper.readValue(userString, new TypeReference<Optional<User>>() {});
		when(repo.findAll()).thenReturn(Stream.of(userResponse.get()).collect(Collectors.toList()));
		ResponseEntity<Object> response = controller.findAll();
			
			assertNotNull(response);
			assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void given_ValidUserId_when_calling_getAll_then_return_userDetails() {
		
		try {
			Optional<User> userResponse = null;
			String userString = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("UserResponse.json"), "UTF-8");
			userResponse = objectMapper.readValue(userString, new TypeReference<Optional<User>>() {});
			
			when(repo.findById((String) "609aa61ad0369e034baa5e6a")).thenReturn(userResponse);
			ResponseEntity<Object> response = controller.findById((String)"609aa61ad0369e034baa5e6a");
			
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
			 
			when(repo.update((String) "101","as","asd")).thenThrow(new RuntimeException());
			ResponseEntity<Object> response = controller.update(eq("-101"), eq("x"), eq("x"));
			when(repo.findById((String) "101")).thenThrow(new RuntimeException());
			assertNotNull(response);

			
			
		} catch (CustomException ex) { 
			assertNotNull(ex.getMessage());
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
			assertEquals("Employee Id Should be positive number", ex.getMessage());
		}
		
	}*/

}
