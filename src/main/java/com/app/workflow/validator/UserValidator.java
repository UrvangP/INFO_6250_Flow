package com.app.workflow.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.workflow.pojo.User;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "userName", "empty-uname", "Username cannot be empty");
		ValidationUtils.rejectIfEmpty(errors, "email", "empty-email", "Email cannot be empty");
		ValidationUtils.rejectIfEmpty(errors, "password", "empty-password", "Password cannot be empty");
		//ValidationUtils.rejectIfEmpty(errors, "username", "empty-uname", "User Name cannot be empty");
		
	}

}
