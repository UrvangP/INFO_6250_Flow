package com.app.workflow.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.app.workflow.exception.FlowException;
import com.app.workflow.pojo.Project;
import com.app.workflow.pojo.User;

@Component
public class ProjectDAO extends DAO {

	public void createProject(Project proj) throws FlowException {
        
    	try {
            begin();
            getSession().save(proj);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not add project " + proj.getProjectName(), e);
        }
    }
	
	public void updateProject(Project proj) throws FlowException {
        
    	try {
            begin();
            getSession().update(proj);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not add project " + proj.getProjectName(), e);
        }
    }

    public void delete(Project proj) throws FlowException {
        
    	try {
            begin();
            getSession().delete(proj);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not delete project " + proj.getProjectName(), e);
        }
    }
    
    public List<Project> getAllProjectss() throws FlowException {
        
		try {
            begin();
            String hql = "From Project";
            System.out.println(hql);
            Query q = getSession().createQuery(hql);
            List<Project> proj = (List<Project>) q.list();
            commit();
            return proj;
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }
    
    public List<Long> getMyProjects( long uId ) throws FlowException {
        
		try {
            begin();
            
            String hql = "select p.id from Project p join p.user pu where pu.id = :u";
            //System.out.println(hql);
	        Query q = getSession().createQuery(hql);
	        q.setLong("u", uId);
	        List<Long> proj = (List<Long>) q.list();
	        System.out.println(proj.size());
	        commit();
	        return proj;
	        
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }
    
    public Project getProjectByName( String name ) throws FlowException {
        
		try {
            begin();
            String hql = "From Project p where projectName='"+name+"'";
            System.out.println(hql);
            Query q = getSession().createQuery(hql);
            List<Project> proj = (List<Project>) q.list();
            System.out.println(proj.size());
            
            if(proj.size()==0) {
            	rollback();
            	return null;
            }
            
            commit();
            return proj.get(0);
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }
    
    public Project getProjectById( long id ) throws FlowException {
        
		try {
            begin();
            String hql = "From Project p where id= :i";
            System.out.println(hql);
            Query q = getSession().createQuery(hql);
            q.setLong("i", id);
            List<Project> proj = (List<Project>) q.list();
            System.out.println(proj.size());
            
            if(proj.size()==0) {
            	rollback();
            	return null;
            }
            
            commit();
            return proj.get(0);
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }
}
