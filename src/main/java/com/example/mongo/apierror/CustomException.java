package com.example.mongo.apierror;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private final String message;
	private final HttpStatus status;
	
	public CustomException(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	
}
