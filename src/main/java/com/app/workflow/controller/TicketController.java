package com.app.workflow.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.workflow.dao.ProjectDAO;
import com.app.workflow.dao.TicketDAO;
import com.app.workflow.dao.UserDAO;
import com.app.workflow.exception.FlowException;
import com.app.workflow.pojo.Project;
import com.app.workflow.pojo.Ticket;
import com.app.workflow.pojo.User;

@Controller
public class TicketController {
	
	@GetMapping("/ticket/create")
	public ModelAndView getTicketCreatePage( ModelMap model, Ticket ticket, UserDAO uDAO, ProjectDAO pDAO, HttpServletRequest request ) throws FlowException {
		model.addAttribute("ticket", ticket);
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		long projNum = Long.parseLong(String.valueOf(request.getParameter("projId")));
		Project project = pDAO.getProjectById(projNum);
		
		model.addAttribute("myproject",  project);
		
		return new ModelAndView("createTicket", "ticket", ticket);
	}
	
	@PostMapping("/ticket/create")
	public ModelAndView newUser( @ModelAttribute("ticket")Ticket ticket, TicketDAO tDAO, ProjectDAO pDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		boolean err = false;
		if(ticket.getTicketSubject() == "") {
			request.setAttribute("error", "Ticket subject cannot be empty. ");
			err = true;
		}
		if(ticket.getTicketDescription() == "") {
			String temp = "";
			if(err) temp = String.valueOf(request.getAttribute("error"));
			temp += "Desctiption cannot be empty. ";
			request.setAttribute("error", temp);
			err = true;
		}
		
		if(err) return new ModelAndView("error");
		
		ticket.setStatus("Open");
		
		ticket.setCreateOn(new Date());
		ticket.setModifiedOn(new Date());
		
		System.out.println("Ticket project id: " + ticket.getAssignedProjectId());
		ticket.setProject(pDAO.getProjectById(ticket.getAssignedProjectId()));
		
		tDAO.createTicket(ticket);
		return new ModelAndView("redirect:/", "ticket", ticket);
	}
	
	@GetMapping("/ticket/assign")
	public ModelAndView getTicketList( TicketDAO tDAO, ProjectDAO pDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		

		User engg = (User)request.getSession().getAttribute("user");
		
		List<Long> myProjects = pDAO.getMyProjects(engg.getId());
		
		List<Ticket> ticketList = tDAO.getOpenTickets();
		
		Set<Long> projects = new HashSet<Long>();
		for(long id : myProjects ) {
			projects.add(id);
		}
		
		List<Ticket> result = new ArrayList<Ticket>();
		for(Ticket tic : ticketList) {
			if(projects.contains(tic.getProject().getId())) {
				result.add(tic);
			}
		}
		
		return new ModelAndView("openTickets", "tickets", result);
	}
	
	@PostMapping("/ticket/assign")
	public ModelAndView assignTicket( TicketDAO tDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		long ticketNum = Long.parseLong(String.valueOf(request.getParameter("ticketNum")));
		Ticket ticket = tDAO.getTicketById( ticketNum );
		
		if( ticket == null ) {
			StringBuilder msg = new StringBuilder();
			msg.append("Unable to fetch ticket details");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		User engg = (User)request.getSession().getAttribute("user");
		
		ticket.setAssigedTo(engg);
		ticket.setModifiedOn(new Date());
		ticket.setStatus("In Progress");
		
		tDAO.updateTicket(ticket);
		
		return new ModelAndView("redirect:/");
	}
	
	@GetMapping("/ticket/view")
	public ModelAndView viewMyTicket( ModelMap model, TicketDAO tDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User engg = (User)request.getSession().getAttribute("user");
		
		List<Ticket> ticket = tDAO.getMyTickets(engg.getId());
		
		return new ModelAndView("viewMyTickets", "tickets", ticket);
	}
	
	@GetMapping("/ticket/details")
	public ModelAndView ticketDetails( TicketDAO tDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		long ticketNum = Long.parseLong(String.valueOf(request.getParameter("ticketNum")));
		Ticket ticket = tDAO.getTicketById( ticketNum );
		
		if( ticket == null ) {
			StringBuilder msg = new StringBuilder();
			msg.append("Unable to fetch ticket details");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		return new ModelAndView("viewTicketDetails", "ticket", ticket);
	}
	
	@GetMapping("/ticket/edit")
	public ModelAndView editMyTicket( @ModelAttribute("ticket")Ticket ticket, TicketDAO tDAO, HttpServletRequest request ) throws FlowException {
		
		//System.out.println(request.getSession().getAttribute("user"));
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		//User engg = (User)request.getSession().getAttribute("user");
		
		long ticketNum = Long.parseLong(String.valueOf(request.getParameter("ticketNum")));
		ticket = tDAO.getTicketById( ticketNum );
		
		if( ticket == null ) {
			StringBuilder msg = new StringBuilder();
			msg.append("Unable to fetch ticket details");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		return new ModelAndView("editTicket", "tickets", ticket);
	}
	
	@PostMapping("/ticket/edit")
	public ModelAndView editedTicket( @ModelAttribute("ticket")Ticket ticket, TicketDAO tDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}

		Ticket oldTicket = tDAO.getTicketById( ticket.getId() );
		
		if( oldTicket == null ) {
			StringBuilder msg = new StringBuilder();
			msg.append("Unable to fetch ticket details");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		oldTicket.setStatus(ticket.getStatus());
		oldTicket.setTicketDescription(ticket.getTicketDescription());
		oldTicket.setTicketSubject(ticket.getTicketSubject());
		oldTicket.setModifiedOn(new Date());

		tDAO.updateTicket(oldTicket);

		return new ModelAndView("redirect:/");
	}
	
	@GetMapping("/ticket/delete")
	public ModelAndView projectDelete( TicketDAO tDAO, HttpServletRequest request ) throws FlowException {
		
		if(request.getSession().getAttribute("user") == null ) {
			return new ModelAndView("redirect:/");
		}
		
		User loggedIn = (User)request.getSession().getAttribute("user");
		
		if("Engineer".equals(loggedIn.getRole())) {
			StringBuilder msg = new StringBuilder();
			msg.append("Only admins or managers can delete tickets");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		long ticketNum = Long.parseLong(String.valueOf(request.getParameter("ticketNum")));
		Ticket ticket = tDAO.getTicketById( ticketNum );
		
		if( ticket == null ) {
			StringBuilder msg = new StringBuilder();
			msg.append("Unable to fetch ticket details");
			request.setAttribute("error", msg.toString());
			return new ModelAndView("error");
		}
		
		tDAO.delete(ticket);
		
		return new ModelAndView("redirect:/");
	}
}