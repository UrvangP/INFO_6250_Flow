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
public class TicketDAO extends DAO {
	
	public void createTicket(Ticket tic) throws FlowException {
	
    	try {
            begin();
            getSession().save(tic);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not add ticket " + tic.getTicketSubject(), e);
        }
    }
	
	public void updateTicket(Ticket tik) throws FlowException {
        
    	try {
            begin();
            getSession().update(tik);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not update ticket " + tik.getTicketSubject(), e);
        }
    }

    public void delete(Ticket tic) throws FlowException {
        
    	try {
            begin();
            getSession().delete(tic);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not delete ticket " + tic.getTicketSubject(), e);
        }
    }
    
    public List<Ticket> getOpenTickets() throws FlowException {
        
    	try {
            begin();
            String hql = "From Ticket where status='Open'";
            Query q = getSession().createQuery(hql);
            List<Ticket> tickets = (List<Ticket>)q.list();
            commit();
            return tickets;
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find tickets", e);
        }
    }
    
    public List<Ticket> getAllTickets() throws FlowException {
        
    	try {
            begin();
            String hql = "From Ticket";
            Query q = getSession().createQuery(hql);
            List<Ticket> tickets = (List<Ticket>)q.list();
            commit();
            return tickets;
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find tickets", e);
        }
    }
    
    public Ticket getTicketById( long tId ) throws FlowException {
        
    	try {
            begin();
            String hql = "From Ticket where id='" + tId + "'";
            Query q = getSession().createQuery(hql);
            List<Ticket> tickets = (List<Ticket>)q.list();
            
            if(tickets.size()==0) {
            	return null;
            }
            
            commit();
            return tickets.get(0);
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find tickets", e);
        }
    }
    
    public List<Ticket> getMyTickets( long tId ) throws FlowException {
        
    	try {
            begin();
            
            String hql2 = "From User where id = :u";
            Query qu = getSession().createQuery(hql2);
            qu.setLong("u", tId);
            User user = (User)qu.uniqueResult();
          
            String hql = "From Ticket where user_id = :u";
            qu = getSession().createQuery(hql);
            qu.setLong("u", user.getId());
            List<Ticket> tickets = (List<Ticket>)qu.list();
            commit();
            return tickets;
            
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find tickets", e);
        }
    }
    
    public List<Ticket> getTicketsByProject( long tId ) throws FlowException {
        
    	try {
            begin();
            
            String hql2 = "From Project where id = :u";
            Query qu = getSession().createQuery(hql2);
            qu.setLong("u", tId);
            Project project = (Project)qu.uniqueResult();
          
            String hql = "From Ticket where project_id = :u";
            qu = getSession().createQuery(hql);
            qu.setLong("u", project.getId());
            List<Ticket> tickets = (List<Ticket>)qu.list();
            commit();
            return tickets;
            
        } catch (HibernateException e) {
            rollback();
            throw new FlowException("Could not find tickets", e);
        }
    }
}
