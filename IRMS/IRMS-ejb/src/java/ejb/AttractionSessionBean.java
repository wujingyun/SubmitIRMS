/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Attraction;
import entity.AttractionPass;
import entity.AttractionPassTrans;
import entity.AttractionTicket;
import entity.AttractionTicketTrans;
import entity.Customer;
import exception.ExistException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class AttractionSessionBean implements AttractionSessionBeanRemote {

     @PersistenceContext()
     EntityManager em;
     //@EJB
    //CustomerBean cb = new CustomerBean();
     private Customer purchaseCustomer;
     private Attraction attractions;
     private AttractionPass pass;
     private AttractionTicket ticket;
     private AttractionPassTrans attractionPassTrans;
     
   private  List<AttractionPassTrans> attractionPassTransList;
      private AttractionTicketTrans attractionTicketTrans;
    private  List<AttractionTicketTrans> attractionTicketTransList;
     private List<Attraction> attractionList;
     private List<AttractionPass> passList;
     private List<AttractionTicket> ticketList;
     
     
     
     @Override
     public List<AttractionPass> getPass(String name){
         attractions = new Attraction();
         passList = new ArrayList();
         System.err.println("Atraction session bean..."+name);
         attractions = em.find(Attraction.class, name);
         for (AttractionPass a : attractions.getPass()) {
             passList.add(a);
         }
         return passList;
     }
     
     @Override
     public List<AttractionTicket> getTicket(String name){
         attractions = new Attraction();
         ticketList = new ArrayList();
         System.err.println("Atraction session bean..."+name);
         attractions = em.find(Attraction.class, name);
         for (AttractionTicket a : attractions.getTicket()) {
             ticketList.add(a);
         }
         return ticketList;
     }
     
     @Override
     public void createPass (String attractionName,String name, double price ,String type,String remarks) throws ExistException{
         pass = new AttractionPass();
         attractions = new Attraction();
         
         if(em.find(AttractionPass.class, name)!=null) throw new ExistException("Pass already exists！");
         attractions = em.find(Attraction.class,attractionName);
         if(attractions ==null) throw new ExistException("Can't find the attraction");
         
         pass.createPass(name, price, type,remarks);
         attractions.getPass().add(pass);
         em.persist(pass);
         
     }
     
     @Override
     public void createTicket(String attractionName,String name, double price , String type, String remarks) throws ExistException{
          ticket = new AttractionTicket();
          attractions = new Attraction();
          
          if(em.find(AttractionTicket.class, name)!=null) throw new ExistException("Ticket already exists！");
           attractions = em.find(Attraction.class,attractionName);
         if(attractions ==null) throw new ExistException("Can't find the attraction");
         
          ticket.createTicket(name, price, type, remarks);
          attractions.getTicket().add(ticket);
          em.persist(ticket);
     }
     
     @Override
     public void editTicket(String name , double price, String type, String remarks) throws ExistException{
         ticket =new AttractionTicket();            
         ticket = em.find(AttractionTicket.class, name);
         if(ticket ==null) throw new ExistException("Can't find the ticket");        
         ticket.editTicket(price, type,remarks);
         em.flush();
     }
     
     @Override
     public void editPass(String name , double price, String type,String remarks) throws ExistException{
         pass =new AttractionPass();
         pass = em.find(AttractionPass.class, name);
         if(pass ==null) throw new ExistException("Can't find the pass");
         pass.editPass(price, type,remarks );
         em.flush();
     }
     
     @Override
     public void deleteTicket(String attractionName,String name)throws ExistException{
         ticket =new AttractionTicket();   
         attractions = new Attraction();
         
         attractions = em.find(Attraction.class,attractionName);
         if(attractions ==null) throw new ExistException("Can't find the attraction");
         ticket = em.find(AttractionTicket.class, name);
         if(ticket ==null) throw new ExistException("Can't find the ticket");
         attractions.getTicket().remove(ticket);       
         em.remove(ticket);
         em.flush();
     }
     
      @Override
     public void deletePass(String attractionName,String name)throws ExistException{
         pass =new AttractionPass();   
         attractions = new Attraction();
         
         attractions = em.find(Attraction.class,attractionName);
         if(attractions ==null) throw new ExistException("Can't find the attraction");
         pass = em.find(AttractionPass.class, name);
         if(pass ==null) throw new ExistException("Can't find the ticket");
         attractions.getPass().remove(pass);
         em.remove(pass);
         em.flush();
     }
     
     
     
     @Override
     public void createAttraction(String name,int maxQuota,String operatingHours,String location,String descriptions) throws ExistException{
         attractions = new Attraction ();
         System.err.println("Atraction session bean..."+name+" Op hours"+operatingHours);
         if(em.find(Attraction.class, name)!=null) throw new ExistException("Attraction already exists！");
         attractions.createAttraction(name, maxQuota, operatingHours, location, descriptions);
         em.persist(attractions);
                  
     }
     
     
     @Override
     public List<Attraction> getAttractions(){
         attractionList = new ArrayList();
         String ejbql ="SELECT a FROM Attraction a";
         Query q = em.createQuery(ejbql);
        for(Object o: q.getResultList()){
             Attraction  a =(Attraction)o;
            attractionList.add(a);       
        }
        em.flush();
       return attractionList;
     }
     
     @Override
     public void editAttraction(String name,int maxQuota,String operatingHours,String location,String descriptions) throws ExistException{
         attractions =new Attraction();
         attractions = em.find(Attraction.class, name);
         if(attractions ==null) throw new ExistException("Can't find the attraction");
         attractions.editAttraction(maxQuota, operatingHours, location, descriptions);
         em.flush();
     }
      @Override
     public void deleteAttraction(String name) throws ExistException{
         attractions =new Attraction();
         attractions = em.find(Attraction.class, name);
         if(attractions ==null) throw new ExistException("Can't find the attraction");
         em.remove(attractions);
         em.flush();
     }
     
           @Override
     public void buyPass(Long customerId,int quantity,  String attractionPassId) throws ExistException{
         attractionPassTrans =new AttractionPassTrans();
        pass = new AttractionPass();
        purchaseCustomer=new Customer();
        attractionPassTransList=new ArrayList();
         pass = em.find(AttractionPass.class, attractionPassId);
         if(pass ==null) throw new ExistException("Can't find the pass");
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	   
	   Date date = new Date();
	  String purchaseDate= dateFormat.format(date);
           Query query = em.createQuery("SELECT c from Customer c where c.Id=?1");
        query.setParameter(1, customerId);

        purchaseCustomer = (Customer) query.getSingleResult();
  
         attractionPassTrans.createAttractionPassTrans(purchaseCustomer, quantity, purchaseDate, pass);
         attractionPassTransList.add(attractionPassTrans);
         
         purchaseCustomer.setAttractionPassTrans(attractionPassTransList);
         em.persist(purchaseCustomer);
 em.persist(attractionPassTrans);
            em.flush();
         
     }
             
     @Override
     public void purchaseTicket(Long customerId,int quantity,  String ticketId) throws ExistException{
         
         System.out.println("purchaseTicket");
         attractionTicketTrans =new AttractionTicketTrans();
        ticket = new AttractionTicket();
        purchaseCustomer=new Customer();
        attractionTicketTransList=new ArrayList();
         System.out.println("purchaseTicket2");
         ticket = em.find(AttractionTicket.class, ticketId);
         if(ticket ==null) throw new ExistException("Can't find the pass");
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	   
	   Date date = new Date();
	  String purchaseDate= dateFormat.format(date);
          System.out.println(purchaseDate);
           Query query = em.createQuery("SELECT c from Customer c where c.Id=?1");
        query.setParameter(1, customerId);

        purchaseCustomer = (Customer) query.getSingleResult();
  System.out.println(purchaseCustomer.getUserName()+quantity+purchaseDate+ticket.getTicketType());
         attractionTicketTrans.createAttractionTicketTrans(purchaseCustomer, quantity, purchaseDate, ticket);
         attractionTicketTransList.add(attractionTicketTrans);
         System.out.println(attractionTicketTransList.size());
         purchaseCustomer.setAttractionTicketTrans(attractionTicketTransList);
         em.persist(purchaseCustomer);
 em.persist(attractionTicketTrans);
            em.flush();
         
     }
     
}
