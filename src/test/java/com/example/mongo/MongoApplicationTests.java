package com.example.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ch.qos.logback.core.status.Status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoApplicationTests {

	@Autowired                           
    private DataController controller;  
                                                 
    @MockBean                          
    private UserService userService; 
                                               
    private List<User> userList;       
                                            
    @BeforeEach                           
    public void setUp() {                               
      	this.userList = new ArrayList<>();                                    
	   	this.userList.add(new User("1234", "user1@gmail.com", "pwd1"));   
		this.userList.add(new User("321314", "user2@gmail.com", "pwd2"));
		this.userList.add(new User("12312", "user3@gmail.com", "pwd3"));                                                       
    }

	@Test
	public void findAll() throws IOException{
		when(userService.findAll()).thenReturn(userList);
		ResponseEntity<Object> response = controller.findAll();
		System.out.println("respuesta"+response.getBody());
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void addUser() throws IOException{
		when(userService.addUser("rafa","x@x.com")).thenReturn("Saved");
		ResponseEntity<Object> response = controller.addUser("rafa","x@x.com");
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void update() throws IOException{
		when(userService.update("1234", "rafa", "x@x.com")).thenReturn("Updated");
		ResponseEntity<Object> response = controller.update("1234", "rafa", "x@x.com");
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void findById() throws IOException, CustomException{
		when(userService.findById("1234")).thenReturn(new User("1234", "user1@gmail.com", "pwd1"));
		ResponseEntity<Object> response = controller.findById("1234");
		verify(userService).findById("1234");
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
