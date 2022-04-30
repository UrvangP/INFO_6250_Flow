package com.app.workflow.pojo;

import org.springframework.stereotype.Component;

@Component
public class Error {

	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Error() {
	}
	
}
