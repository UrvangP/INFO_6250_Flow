package com.app.workflow.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.workflow.dao.ProjectDAO;
import com.app.workflow.dao.UserDAO;
import com.app.workflow.exception.FlowException;
import com.app.workflow.pojo.Project;
import com.app.workflow.pojo.User;

@Controller
public class LoginController {
	
	@RequestMapping( value="/", method=RequestMethod.GET )
	public ModelAndView getLoginPage( ModelMap model, User user, HttpServletRequest request, ProjectDAO pDAO ) throws FlowException {
		
		User userData = (User)request.getSession().getAttribute("user");
		
		if(userData == null) {
			model.addAttribute("user", user);
			return new ModelAndView("login", "user", user);
		}
		
		if( "Admin".equals(userData.getRole())) {
			List<Project> projects = pDAO.getAllProjectss();
			return new ModelAndView("adminHome", "projects", projects);
		}
		else if( "Manager".equals(userData.getRole())) {
			return new ModelAndView("managerHome", "user", user);
		}
		else {
			return new ModelAndView("engineerHome", "user", user);
		}
		
	}
	
	@PostMapping("/")
	public ModelAndView verifyUser( @ModelAttribute("user")User user, UserDAO uDAO, HttpSession session, 
			HttpServletRequest request, ProjectDAO pDAO ) throws FlowException {
		
		//Verify the user, if valid
		User userData = uDAO.getUser(user.getEmail(), user.getPassword());
		
		session = request.getSession();
		
		if( userData == null ) {
			request.setAttribute("error", "Incorrect username or password");
			return new ModelAndView("error");
		}
		
		session.setAttribute("user", userData);
		
		if( "Admin".equals(userData.getRole())) {
			List<Project> projects = pDAO.getAllProjectss();
			return new ModelAndView("adminHome", "projects", projects);
		}
		else if( "Manager".equals(userData.getRole())) {
			return new ModelAndView("managerHome", "user", user);
		}
		else {
			return new ModelAndView("engineerHome", "user", user);
		}
	}
	
	@GetMapping("/logout")
	public ModelAndView logoutUser( @ModelAttribute("user")User user, UserDAO uDAO, HttpSession session, HttpServletRequest request ) throws FlowException {
		
		session.removeAttribute("user");
		
		return new ModelAndView("redirect:/");
	}
}

