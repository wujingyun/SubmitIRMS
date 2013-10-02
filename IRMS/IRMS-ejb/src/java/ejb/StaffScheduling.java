/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Hotel;
import entity.Staff;
import exception.ExistException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class StaffScheduling implements StaffSchedulingRemote {
     @PersistenceContext()
     EntityManager em;
     
     private Staff staffMember;
     private List<Staff> receptionistList;
     private List<Hotel> hotel;
     private Hotel htl;
     
     @Override
     public List<Staff> getStaffList(){
        String ejbql = "SELECT s FROM Staff s";
        Query q = em.createQuery(ejbql);
        receptionistList= new ArrayList();
        
        for(Object o: q.getResultList()){
             Staff s = (Staff)o;
             receptionistList.add(s);
        }
        System.err.println("getting receptionist"+receptionistList.size());
        return receptionistList; 
                 
     }
     
     @Override
     public void addStaff(String name, String hotelName){
         htl= new Hotel();
         htl =em.find(Hotel.class, hotelName);
         staffMember= new Staff();
         staffMember.createStaff(name);
         em.persist(staffMember);
         htl.getStaffMembers().add(staffMember);
         staffMember.setHotel(htl);
         em.flush();
     }
    /* @Override
     public void getStaffListInHotel(String HotelName){
         
     }*/
     
     
     @Override
     public List<Staff> getStaff(String hotelName){
         htl= new Hotel();
         htl =em.find(Hotel.class, hotelName);
         receptionistList =new ArrayList();
         receptionistList = (List<Staff>) htl.getStaffMembers();
         receptionistList.size();
         return receptionistList;
     }
     
     @Override
     public List<Hotel> getHotel(){
         String ejbql ="SELECT h FROM Hotel h";
         Query q = em.createQuery(ejbql);
         hotel = new ArrayList();
         
         for(Object o: q.getResultList()){
             Hotel h = (Hotel)o;
             hotel.add(h);
         }
         return hotel;
     }
     
    
     @Override
     public void  setShifts(Long staffID,String staffRole,String shift,String floorLevel,String day){
         System.err.println("yeah I am here!");
   /*  for(int i=0; i<day.size(); i++){
           System.err.println(day.get(i));
        }*/
        System.err.println(staffRole+" "+shift+" "+floorLevel);
         staffMember = new Staff();
         staffMember = em.find(Staff.class,staffID);
         staffMember.setStaffRole(staffRole);
         staffMember.setShift(shift);
         staffMember.setFloorLevel(floorLevel);
         
      /*   if(day.size()>=6)try {
             throw new ExistException("Cannot work more than 5 days");
         } catch (ExistException ex) {
             Logger.getLogger(StaffScheduling.class.getName()).log(Level.SEVERE, null, ex);
         }*/
         staffMember.setWeek_day(day);
         em.flush(); 
     }

}
