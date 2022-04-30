package com.app.workflow.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Project {

	@Id
	@GeneratedValue
	private long id;
	String projectName;
	String description;
	
	//@OneToMany
	@ManyToOne
	@JoinColumn(name="manager_id")
	User manager;
	
	@Transient
	String managerName;
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	@Transient
	String[] selectedUser;

	@Transient
	String hiddenElem;

	//	
	@ManyToMany(fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			 CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="User_Project",
			joinColumns=@JoinColumn(name="project_id", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="ID")
			)	
	Set<User> user = new HashSet<User>();

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	public Project() {
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public String toString() {
	    return getProjectName();
	}

	public String getHiddenElem() {
		return hiddenElem;
	}

	public void setHiddenElem(String hiddenElem) {
		this.hiddenElem = hiddenElem;
	}

	public String[] getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(String[] selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	
	
}
