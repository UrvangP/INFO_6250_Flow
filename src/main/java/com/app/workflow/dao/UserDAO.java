package com.app.workflow.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.app.workflow.exception.FlowException;
import com.app.workflow.pojo.Project;
import com.app.workflow.pojo.Ticket;
import com.app.workflow.pojo.User;

@Component
public class UserDAO extends DAO {

	public UserDAO() {
    }
	
	public User getUser(String mail, String password) throws FlowException {
        
		try {
            begin();
            String hql = "From User where email='" + mail + "' and password='" + password + "'";
            System.out.println(hql);
            Query q = getSession().createQuery(hql);
            List<User> use = (List<User>) q.list();
            
            if(use.size()==0) {
            	rollback();
            	return null;
            }
            
            commit();
            return use.get(0);
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }
	
	public List<User> getAllUsers() throws FlowException {
        
		try {
            begin();
            String hql = "From User";
            System.out.println(hql);
            Query q = getSession().createQuery(hql);
            List<User> use = (List<User>) q.list();
            commit();
            return use;
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }
	
	public List<User> getManagers() throws FlowException {
        
		try {
            begin();
            String hql = "From User where role='Manager'";
            System.out.println(hql);
            Query q = getSession().createQuery(hql);
            List<User> use = (List<User>) q.list();
            commit();
            return use;
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }

	public List<User> getEngineers() throws FlowException {
        
		try {
            begin();
            String hql = "From User where role='Engineer'";
            System.out.println(hql);
            Query q = getSession().createQuery(hql);
            List<User> use = (List<User>) q.list();
            commit();
            return use;
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }
	
	public User getUserByName( String name ) throws FlowException {
        
		try {
            begin();
            String hql = "From User where USERNAME='" + name + "'" ;
            System.out.println(hql);
            Query q = getSession().createQuery(hql);
            List<User> use = (List<User>) q.list();
            
            if(use.size()==0) {
            	rollback();
            	return null;
            }
            
            commit();
            return use.get(0);
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }
	
	public User getUserById( long name ) throws FlowException {
        
		try {
            begin();
            String hql = "From User where id='" + name + "'" ;
            System.out.println(hql);
            Query q = getSession().createQuery(hql);
            List<User> use = (List<User>) q.list();
            
            System.out.println(use.size());
            
            if(use.size()==0) {
            	rollback();
            	return null;
            }
            
            commit();
            return use.get(0);
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find a user with the given combination", e);
        }
    }

	public void createUser(User user) throws FlowException {
        
    	try {
            begin();
            getSession().save(user);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not add user " + user.getUserName(), e);
        }
    }

    public void delete(User user) throws FlowException {
        
    	try {
            begin();
            getSession().delete(user);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not delete user " + user.getUserName(), e);
        }
    }
    
//    public void getUserByProject( long pId ) throws FlowException{
//    	try {
//            begin();
//            String hql = "select p.projectName from Project p join p.user pu where pu.id = :u";
//            //System.out.println(hql);
////	        Query q = getSession().createQuery(hql);
////	        q.setLong("u", pId);
////	        List<Project> proj = (List<Project>) q.list();
////	        System.out.println(proj.size());
//	        commit();
//        } catch (HibernateException e) {
//            rollback();
//            throw new FlowException("Could not delete user ", e);
//        }
//    }
    
    public List<Long> getEngineersInProject( long pId ) throws FlowException{
    	try {
            begin();
            
            String hql = "select pu.id from Project p join p.user pu where p.id = :uid";
            Query q = getSession().createQuery(hql);
            q.setLong("uid", pId);
            List<Long> users = (List<Long>)q.list();
            System.out.println(users.size());
	        commit();
	        return users;
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not delete user ", e);
        }
    }
    
    public void updateUser(User user) throws FlowException {
        
    	try {
            begin();
            getSession().update(user);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not update user " + user.getUserName(), e);
        }
    }
}