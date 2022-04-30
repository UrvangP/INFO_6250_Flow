package com.app.workflow.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private long id;
	String ticketSubject;
	String ticketDescription;
	String ticketType;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			 CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
	User assigedTo;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			 CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="project_id")
	Project project;
	
	String status;
	Date modifiedOn;
	Date createOn;
	
	@Transient
	String assignedUserName;
	@Transient
	long assignedProjectId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTicketSubject() {
		return ticketSubject;
	}
	public void setTicketSubject(String ticketSubject) {
		this.ticketSubject = ticketSubject;
	}
	public String getTicketDescription() {
		return ticketDescription;
	}
	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}
	public User getAssigedTo() {
		return assigedTo;
	}
	public void setAssigedTo(User assigedTo) {
		this.assigedTo = assigedTo;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	
	public Ticket() {
		
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getAssignedUserName() {
		return assignedUserName;
	}
	public void setAssignedUserName(String assignedUserName) {
		this.assignedUserName = assignedUserName;
	}
	public long getAssignedProjectId() {
		return assignedProjectId;
	}
	public void setAssignedProjectId(long assignedProjectName) {
		this.assignedProjectId = assignedProjectName;
	}
	
	
	
	
}
