package com.app.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan({"com.app.workflow.controller","com.app.workflow.dao","com.app.workflow.pojo","com.app.workflow.validator"})
public class WorkflowApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(WorkflowApplication.class, args);
	}
	
//	@Bean(name="miltipartResolver")
//	public CommonsMulti

}
