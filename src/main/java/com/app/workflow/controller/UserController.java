package com.app.workflow.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.validation.BindingResult;

import com.app.workflow.dao.UserDAO;
import com.app.workflow.exception.FlowException;
import com.app.workflow.pojo.Ticket;
import com.app.workflow.pojo.User;
import com.app.workflow.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	UserValidator uValidator;

	@GetMapping("/user/register")
	public ModelAndView getUserPage( ModelMap model, User user, HttpServletRequest request ) {
		
		model.addAttribute("user", user);
		return new ModelAndView("createUser", "user", user);
	}
	
	@PostMapping("/user/register")
	public ModelAndView newUser( @ModelAttribute("user")User user, UserDAO uDAO, 
			HttpServletRequest request, BindingResult result, SessionStatus status) throws FlowException {
		
		boolean err = false;
		if(user.getUserName() == "") {
			request.setAttribute("error", "Username cannot be empty. ");
			err = true;
		}
		if(user.getEmail() == "") {
			String temp = "";
			if(err) temp = String.valueOf(request.getAttribute("error"));
			temp += "Email cannot be empty. ";
			request.setAttribute("error", temp);
			err = true;
		}
		if(user.getPassword() == "") {
			String temp = "";
			if(err) temp = String.valueOf(request.getAttribute("error"));
			temp += "Password cannot be empty.";
			request.setAttribute("error", temp);
			err = true;
			
		}
		
		if( uDAO.getUserByName(user.getUserName()) != null) {
			String temp = "";
			if(err) temp = String.valueOf(request.getAttribute("error"));
			temp += "Username shoud be unique.";
			request.setAttribute("error", temp);
			err = true;
		}
		
		if(err) return new ModelAndView("error");
		
//		uValidator.validate(this, result);
//		
//		if (result.hasErrors())
//			return new ModelAndView("createUser");
//
//		status.setComplete();
		
		uDAO.createUser(user);
		return new ModelAndView("redirect:/");
	}
	
	@GetMapping("/user/view")
	public ModelAndView getUserList( UserDAO uDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		List<User> userList = uDAO.getAllUsers();
		return new ModelAndView("showUsers", "users", userList);
	}
	
	@GetMapping("/user/edit")
	public ModelAndView getUpdateUser( UserDAO uDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		if(!"Admin".equals(loggedIn.getRole())) {
			StringBuilder msg = new StringBuilder();
			msg.append("Only admins can edit users");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		long uId = Long.parseLong(String.valueOf(request.getParameter("userId")));
		User editUser = uDAO.getUserById( uId );
		
		System.out.println(editUser.getUserName());
		
		return new ModelAndView("editUser", "user", editUser);
	}
	
	@PostMapping("/user/edit")
	public ModelAndView updateUser( @ModelAttribute("user")User user, UserDAO uDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		long uId = Long.parseLong(String.valueOf(request.getParameter("userId")));
		User editUser = uDAO.getUserById( uId );
		
		editUser.setPassword(user.getPassword());
		editUser.setRole(user.getRole());
		
		uDAO.updateUser(editUser);
		
		return new ModelAndView("redirect:/");
	}
	
	//@GetMapping("/user/delete")
	public ModelAndView userDelete( UserDAO uDAO, HttpServletRequest request ) throws FlowException{
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		if(!"Admin".equals(loggedIn.getRole())) {
			StringBuilder msg = new StringBuilder();
			msg.append("Only admins can delete users");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		long uId = Long.parseLong(String.valueOf(request.getParameter("userId")));
		
		if(uId == loggedIn.getId() ) {
			StringBuilder msg = new StringBuilder();
			msg.append("Cannot delete the logged in user");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		if(uId == 1 ) {
			StringBuilder msg = new StringBuilder();
			msg.append("Cannot delete the root admin user");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		User deleteUser = uDAO.getUserById( uId );
		
		if( deleteUser == null ) {
			StringBuilder msg = new StringBuilder();
			msg.append("Unable to fetch user details");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		uDAO.delete(deleteUser);
		
		return new ModelAndView("redirect:/");
	}
}
