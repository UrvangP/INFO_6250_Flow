package com.app.workflow.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.workflow.pojo.Project;

@Component
public class ProjectValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "projectName", "empty-project-name", "Project name cannot be empty");
		ValidationUtils.rejectIfEmpty(errors, "description", "empty-description", "Project Description cannot be empty");
		ValidationUtils.rejectIfEmpty(errors, "managerName", "empty-manager-name", "Manager name cannot be empty");
		//ValidationUtils.rejectIfEmpty(errors, "username", "empty-uname", "User Name cannot be empty");
		
	}
}
