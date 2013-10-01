/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Staff;
import java.util.ArrayList;
import java.util.List;
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
     private List<Staff> housekeepingList;
     
     
     public List<Staff> getReceptionist(){
        String ejbql = "SELECT s FROM Staff s";
        Query q = em.createQuery(ejbql);
        receptionistList= new ArrayList();
        System.err.println("getting receptionist"+receptionistList.size());
        for(Object o: q.getResultList()){
             Staff s = (Staff)o;
            if(s.getStaffRole().substring(0, 11).equals("Receptionist")){ receptionistList.add(s);}
        }
        return receptionistList; 
                 
     }
     
      public List<Staff> getHousekeeping(){
        String ejbql = "SELECT s FROM Staff s";
        Query q = em.createQuery(ejbql);
        housekeepingList= new ArrayList();
        System.err.println("getting housekeeping"+housekeepingList.size());
        for(Object o: q.getResultList()){
             Staff s = (Staff)o;
           if(s.getStaffRole().equals("Housekeeping")){ housekeepingList.add(s);}
        }
        return receptionistList; 
                 
     }
      
   //  public void 

}
