package com.app.workflow.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.app.workflow.dao.ProjectDAO;
import com.app.workflow.dao.TicketDAO;
import com.app.workflow.dao.UserDAO;
import com.app.workflow.exception.FlowException;
import com.app.workflow.pojo.Project;
import com.app.workflow.pojo.Ticket;
import com.app.workflow.pojo.User;
import com.app.workflow.validator.ProjectValidator;

@Controller
public class ProjectController {
	
	@Autowired
	ProjectValidator pValidator;

	@GetMapping("/project/create")
	public ModelAndView getProjectPage( ModelMap model, Project project, UserDAO uDAO, HttpServletRequest request, User user) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		if(!"Admin".equals(loggedIn.getRole())) {
			StringBuilder msg = new StringBuilder();
			msg.append("Only admins can create a new project");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		model.addAttribute( "project", project );
		
		model.addAttribute("managers", uDAO.getManagers());
		return new ModelAndView( "createProject", "project", project );
	}
	
	@PostMapping("/project/create")
	public ModelAndView addProject( @ModelAttribute("project")Project proj, ProjectDAO pDAO, UserDAO uDAO, BindingResult result, 
			SessionStatus status, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User manag = uDAO.getUserByName(proj.getManagerName());
		
		if(manag == null) {
			request.setAttribute("error", "Project must have a manager");
			return new ModelAndView("error");
		}
		
//		pValidator.validate(proj, result);
//		
//		if (result.hasErrors()) return new ModelAndView("createProject");
//
//		status.setComplete();
		
		if(proj.getProjectName() == "") {
			request.setAttribute("error", "Project subject cannot be empty");
			return new ModelAndView("error");
		}
		
		Set<User> projectUsers = new HashSet<User>();
		projectUsers.add(manag);
		
		proj.setUser(projectUsers);
		
		proj.setManager(manag);
		pDAO.createProject(proj);
		return new ModelAndView( "redirect:/", "project", proj );
	}
	
	@GetMapping("/project/view")
	public ModelAndView viewProjects( ProjectDAO pDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		if(!"Admin".equals(loggedIn.getRole())) {
			StringBuilder msg = new StringBuilder();
			msg.append("Only admins can view all the project");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		List<Project> projects = pDAO.getAllProjectss();
		return new ModelAndView( "viewProjects", "projects", projects );
	}
	
	@GetMapping("/project/myprojects")
	public ModelAndView viewMyProjects( ModelMap model, ProjectDAO pDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		List<Long> projects = pDAO.getMyProjects(loggedIn.getId());
		List<Project> result = new ArrayList<Project>();
		for( Long pro : projects ) {
			result.add(pDAO.getProjectById(pro));
		}
		
		return new ModelAndView( "viewProjects", "projects", result );
	}
	
	@GetMapping("/project/select")
	public ModelAndView selectProject( ModelMap model, Project project, ProjectDAO pDAO, UserDAO uDAO, HttpServletRequest request, TicketDAO tDAO ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		if("Engineer".equals(loggedIn.getRole())) {
			StringBuilder msg = new StringBuilder();
			msg.append("Only admins or managers can add users to a project");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		model.addAttribute( "project", project );
		
		long projectNum = Long.parseLong(String.valueOf(request.getParameter("projId")));
		
		model.addAttribute("projId", projectNum);
		
		List<User> allUsers = uDAO.getAllUsers();
		List<Long> projectUsers = uDAO.getEngineersInProject(projectNum);
		
		Set<Long> uniqueUsers = new HashSet<Long>();
		for( long id : projectUsers ){
			uniqueUsers.add(id);
		}
		
		System.out.println("FirstCheck:" + uniqueUsers.size());
		
		List<String> engId = new ArrayList<String>();
		for(User use : allUsers ) {
			if( !uniqueUsers.contains(use.getId()) && "Engineer".equals(use.getRole()) ) {
				engId.add(use.getUserName());
			}
		}

		System.out.println("SecondCheck:" + uniqueUsers.size());
		
		return new ModelAndView( "addUserToProject", "engineers", engId );
	}
	
	@PostMapping("/project/select")
	public ModelAndView addUserProject( @ModelAttribute("project")Project proj, ProjectDAO pDAO, UserDAO uDAO, HttpServletRequest request, ModelMap model ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}

		String[] toBeAdded = proj.getSelectedUser();
		
		Project projData = pDAO.getProjectById(proj.getId());
		
		if( projData == null ) {
			StringBuilder msg = new StringBuilder();
			msg.append("Unable to fetch project details");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		for(String name : toBeAdded) {
			User member = uDAO.getUserByName(name);
			projData.getUser().add(member);
		}
		
		pDAO.updateProject(projData);
		
		return new ModelAndView( "redirect:/" );
		
		
	}
	
	@GetMapping("/project/tickets")
	public ModelAndView viewTicketsByProjects( ProjectDAO pDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		List<Long> projects = pDAO.getMyProjects(loggedIn.getId());
		List<Project> result = new ArrayList<Project>();
		for( Long pro : projects ) {
			result.add(pDAO.getProjectById(pro));
		}
		
		return new ModelAndView( "viewProjects", "projects", result );
	}
	
	@PostMapping("/project/tickets")
	public ModelAndView ticketsByProjects( @ModelAttribute("project")Project proj, ProjectDAO pDAO, TicketDAO tDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		long projectNum = Long.parseLong(String.valueOf(request.getParameter("projId")));
		List<Ticket> ticket = tDAO.getTicketsByProject( projectNum );
		
		
		return new ModelAndView( "viewMyTickets", "tickets", ticket );
	}
	
	@GetMapping("/project/users")
	public ModelAndView viewProjectUsers( ModelMap model, ProjectDAO pDAO, UserDAO uDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		long projectNum = Long.parseLong(String.valueOf(request.getParameter("projId")));
		
		model.addAttribute("projId", projectNum);
		
		List<Long> projectUsers = uDAO.getEngineersInProject(projectNum);
		
		List<User> users = new ArrayList<>();
		
		for( long num : projectUsers ) {
			users.add(uDAO.getUserById(num));
		}
		
		return new ModelAndView( "viewProjectUsers", "users", users );
	}
	
	@PostMapping("/project/users")
	public ModelAndView removeProjectUsers( ProjectDAO pDAO, UserDAO uDAO, HttpServletRequest request ) throws FlowException {
		System.out.println("first");
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		if("Engineer".equals(loggedIn.getRole())) {
			StringBuilder msg = new StringBuilder();
			msg.append("Only admins or managers can add users to a project");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		long projectNum = Long.parseLong(String.valueOf(request.getParameter("projectId")));
		long userNum = Long.parseLong(String.valueOf(request.getParameter("userId")));
		
		Project projDets = pDAO.getProjectById(projectNum);
		
		for( User u : projDets.getUser()) {
			if(u.getId()==userNum) {
				projDets.getUser().remove(u);
				break;
			}
		}
		
		pDAO.updateProject(projDets);
		
		return new ModelAndView( "redirect:/" );
	}
	
	
}
