/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.UserAccount;
import entity.UserContact;
import java.util.List;
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
    private UserAccount user;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Override
    public void register(String name, long role, String pw, String division,Boolean active, String phone_no, String email){
        ua = new UserAccount();//initiate new user
        contact = new UserContact();
        ua.create(name, pw, role,division,active);//set user attribute
        contact.create( phone_no, email);
        ua.setContact(contact);
        em.flush();//
        em.persist(ua);//persist
      
    }
@Override
  public UserAccount findUser(String username) {
        Query q = em.createQuery("SELECT ua FROM UserAccount ua");
        
        for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
  
        return user;
    }

  
       @TransactionAttribute(TransactionAttributeType.REQUIRED)
@Override
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

      

@Override
 public boolean verifyPassword(String userName, String password){
        Query q = em.createQuery("SELECT u from UserAccount u");
        List<UserAccount> userAccounts = q.getResultList();

        for (UserAccount u : userAccounts) {
//            UserAccount u = (UserAccount) o;
                      //  System.out.println("========================================================1password");
            if(u.getUserName().equals(userName)){
            System.out.println("========================================================2password"+userName);
                if(u.getPassword().equals(password))
                {    System.out.println("========================================================3password"+password);
                    return true;}
                break;
                 
            }
        }//System.out.println("========================================================4password");
        return false;
    }


@Override
    public void activateAcct(long userId) {
        ua = em.find(UserAccount.class, userId);
        long contactID;
        if(ua==null)
            System.out.println("Member does not exist");
        
        else{
             ua.setActive(true);
        em.flush();//
        em.persist(ua);//persist
        }
    }


@Override
    public void deactivateAcct(long userId) {
        ua = em.find(UserAccount.class, userId);
        long contactID;
        if(ua==null)
            System.out.println("Member does not exist");
        
        else{
             ua.setActive(false);
        em.flush();//
        em.persist(ua);//persist
        }
    }
}
