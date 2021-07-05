package com.example.mongo.controller;

import java.util.List;
import com.example.mongo.apierror.CustomException;
import com.example.mongo.model.User;
import com.example.mongo.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path="/api")
@Component
public class DataController {

    @Autowired
    IUserService userService;
    
    @PostMapping(path="/add")
    public ResponseEntity<Object> addUser(@RequestParam String name, @RequestParam String email){
		String response = userService.addUser(name, email);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    @GetMapping(path="/findall")
    public ResponseEntity<Object> findAll(){
		List<User> response = userService.findAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    @PutMapping(path="/update")
    public ResponseEntity<Object> update(@RequestParam String id, @RequestParam String name, @RequestParam String email){
		String response = userService.update(id, name, email);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping(path="/findbyid")
    public ResponseEntity<Object> findById(@RequestParam String id) throws CustomException{
		User response = userService.findById(id);
		System.out.println("Getting row with id: "+response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    /*
    @DeleteMapping(path="/delete")
    public ResponseEntity<Object> deleteUser(@RequestParam Integer id){
		String response = userService.deleteUser(id);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
    @GetMapping(path="/custom")
    public ResponseEntity<Object> custom(){
		List<User> response = userService.customquery();
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
    */
}
