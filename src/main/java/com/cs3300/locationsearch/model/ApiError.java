package com.cs3300.locationsearch.model;

import org.springframework.http.HttpStatus;

import com.google.appengine.repackaged.org.joda.time.LocalDateTime;

public class ApiError {
	private HttpStatus status;
	private LocalDateTime timestamp;
	private String message;
	
	private ApiError() {
		timestamp = LocalDateTime.now();
	}
	
	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}
	
	public ApiError(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
}
