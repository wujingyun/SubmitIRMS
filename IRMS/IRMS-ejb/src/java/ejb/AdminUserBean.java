/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.UserAccount;
import entity.UserContact;
import exception.ExistException;
import java.util.Collection;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author WU JINGYUN
 */
@Stateless
public class AdminUserBean implements AdminUserBeanRemote {
@PersistenceContext()
    EntityManager em;
    UserAccount ua;
     UserContact contact;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void register(String name, Collection role, String pw, String department, String phone_no, String email){
        ua = new UserAccount();//initiate new user
        contact = new UserContact();
        ua.create(name, pw, role);//set user attribute
        contact.create(department, phone_no, email);
        ua.setContact(contact);
        em.flush();//
        em.persist(ua);//persist
      
    }

  
       @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void terminate(long userId) {
        ua = em.find(UserAccount.class, userId);
        long contactID;
        if(ua==null)
            System.out.println("Member does not exist");
        
        else{
            contactID = ua.getContact().getId();
            contact = em.find(UserContact.class, contactID);
            em.remove(ua);
            em.remove(contact);
        }
    }

      

 public boolean verifyPassword(long memId, String password){
        Query q = em.createQuery("SELECT u from UserAccount u");

        for (Object o : q.getResultList()) {
            UserAccount u = (UserAccount) o;
                      //  System.out.println("========================================================1password");
            if(u.getId().equals(memId)){
            // System.out.println("========================================================2password");
                if(u.getPassword().equals(password))
                {    // System.out.println("========================================================3password");
                    return true;}
                break;
                 
            }
        }//System.out.println("========================================================4password");
        return false;
    }

}
