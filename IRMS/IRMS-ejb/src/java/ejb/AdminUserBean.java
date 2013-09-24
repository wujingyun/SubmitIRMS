/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.UserAccount;
import entity.UserContact;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
       // ua.setPassword(hashPassword2(ua.getId(),pw));
       // em.flush();//
        //em.persist(ua);
    }
@Override
  public UserAccount findUser(String username) {
        Query q = em.createQuery("SELECT ua FROM UserAccount ua");
        
        for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
  
        return user;
    }


@Override
  public boolean checkUserExist(String username) {
        Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
         q.setParameter(1,username);
         
        if(q.getResultList().size()==0) {
            System.out.println( " No same user ==========================================================");
            return false;
           
        }
  System.out.println( " same user exist==========================================================");
        return true;
         
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
 public String hashPassword(String password){
      try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            System.out.println(hexString.toString() + " hashed password ");
            return hexString.toString();
            
        } catch (Exception e) {
            
            e.printStackTrace();return null;
        }
    
}

@Override
 public String hashPassword2(long uid, String password){
      try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
           hexString.append(uid);
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            System.out.println(hexString.toString() + " second hashed password ");
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();return null;
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
        }
        return false;
    }

@Override
 public   List<UserAccount> getAccountByDivisionToA(String division){
   
        ArrayList AccountByDivision = new ArrayList<UserAccount>();
    Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.division=?1 and ua.active=false");
         q.setParameter(1,division);
    List<UserAccount> userAccounts = q.getResultList();
System.out.println("=======================================================ActiveAccountByDivision"+q.getResultList().size());
                   
        for (UserAccount u : userAccounts) {
              AccountByDivision.add(u);
        
        }
    return AccountByDivision;
    
}

@Override
 public   List<UserAccount> getAccountByDivisionToDA(String division){
   
        ArrayList AccountByDivision = new ArrayList<UserAccount>();
    Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.division=?1 and ua.active=true");
         q.setParameter(1,division);
    List<UserAccount> userAccounts = q.getResultList();
System.out.println("========================================================DeactiveAccountByDivision"+q.getResultList().size());
                   
        for (UserAccount u : userAccounts) {
              AccountByDivision.add(u);
        
        }
    return AccountByDivision;
    
}



@Override
    public void activateAcct(String userName) {
       Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
          q.setParameter(1,userName);
        for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
 
        if(user==null)
            System.out.println("Member does not exist");
        
        else{
             user.setActive(true);
        em.flush();//
        em.persist(user);//persist
        }
    }


@Override
    public void deactivateAcct(String userName) {
         Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
 
        if(user==null)
            System.out.println("Member does not exist");
        
        else{
             user.setActive(false);
        em.flush();//
        em.persist(user);//persist
        }
    }
}
