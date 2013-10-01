/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;
import exception.ExistException;
import entity.UserAccount;
import entity.UserContact;
import entity.UserRole;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
     UserRole role;
    private UserAccount user;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Override
    public void register(String name, long role, String pw, String division,Boolean active, String phone_no, String email){
        ua = new UserAccount();//initiate new user
       
        contact = new UserContact();
          System.out.println( " register system user1=========");
        ua.create(name, pw, role,division,active);//set user attribute
        System.out.println( " register system user create=========");
        contact.create( phone_no, email);
        System.out.println( " register system user contact create=========");
        ua.setContact(contact);
        ua.setLogginAttemp(0);
        Calendar cal = Calendar.getInstance();
        ua.setLast_attemp(cal);
       
        em.flush();//
        em.persist(ua);//persist
           System.out.println( " register system user  created=========");
       // ua.setPassword(hashPassword2(ua.getId(),pw));
       // em.flush();//
        //em.persist(ua);
    }
@Override
  public UserAccount findUser(String username) {
           Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
         q.setParameter(1,username);
      
        System.out.println( " find user first=========================================================="+q.getResultList().size());
        if(q.getResultList().size()!=0) {
               for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }return user;
        }
      
        else {
        return null;}
    }



@Override
  public String getUserRole(String username) {
       long  roleId = 0;
       String  roleName = null;
           Query q1 = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
           q1.setParameter(1,username);
          for (Object o : q1.getResultList()) {
             user = (UserAccount) o;
            roleId=user.getUserrole();
               System.out.println( "getUserROle find user role id=========================================================="+roleId);
        }
          Query q2 = em.createQuery("SELECT ur FROM UserRole ur where ur.role_id=?2");
           q2.setParameter(2,roleId);
       for (Object o : q2.getResultList()) {
             role = (UserRole) o;
              roleName=role.getRole_name();
            
        } 
        System.out.println( "getUserROle find user role =========================================================="+roleName);
        return roleName;
    }
@Override
public List<UserAccount> getAllUsers() throws ExistException{
        Query q = em.createQuery("SELECT ua FROM  UserAccount ua");
        List userList = new ArrayList<UserAccount>();
         for (Object o: q.getResultList()) { 
            UserAccount m = (UserAccount) o; 
            userList.add(m); 
        } 
        if(userList.isEmpty())  throw new ExistException("UserAccount database is empty!");
        return userList;     
    }
@Override
public UserAccount getUserById(long employeeId) {
        user = em.find(UserAccount.class, employeeId);
        if(user == null)  System.out.println("UserAccount does not exist!");
        return user;
    }


@Override
public void updateUserById(long userId, String userName,String email, String phone) throws ExistException {
        user = em.find(UserAccount.class, userId);
        if (user==null)   {System.out.println("no such user===================");}
        //contact = new UserContact();
        contact=user.getContact();
        contact.setEmail(email);
        contact.setPhone(phone);
        user.setUserName(userName);
        user.setContact(contact);
        if (user==null) throw new ExistException("User does not exist!");
         em.flush();//
        em.persist(user);//p
    }


@Override
public UserAccount getUser(String receiverName) throws ExistException{
       Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
          q.setParameter(1,receiverName);
        for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
        if(user == null)  throw new ExistException("Receiver does not exist!");
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
 public boolean setHashPassword(String userName, String password){
    
    Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
          q.setParameter(1,userName);
        for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
        if(user==null)
        {System.out.println("Member does not exist");
        return false;}
        else{ try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
         
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            System.out.println(hexString.toString() + "  hashed password ");
              user.setPassword(hexString.toString());
        em.flush();//
        em.persist(user);//persist
     return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
            
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
             System.out.println("========================================================3password"+password);
              System.out.println("========================================================3password"+u.getPassword()+password); 
          
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

//=================================Accout lock-out========================================== 
//get  number of login attemped
@Override
    public int getLoginAttemp(String userName) {
     int loginAttemp=0;
         Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
 
        if(user==null)
        {  System.out.println("Member does not exist");
       
        }
        else
        {
            loginAttemp=user.getLogginAttemp();
           
        } return loginAttemp;
    }


//update number of login attemped
@Override
    public void updateLoginAttemp(String userName) {
      System.out.println("0==================================");
         Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
   System.out.println("1==================================");
        if(user==null)
            System.out.println("Member does not exist");
        
        else
        {  System.out.println("2==================================");
            int loginAttemp=user.getLogginAttemp();
              System.out.println("3==================================");
            loginAttemp=loginAttemp+1;
              System.out.println("4==================================");
             user.setLogginAttemp(loginAttemp);
        em.flush();//
        em.persist(user);//persist
        }
    }




@Override
    public void setLoginAttempToZero(String userName) {
         Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
 
        if(user==null)
            System.out.println("Member does not exist");
        
        else
        {
           
             user.setLogginAttemp(0);
        em.flush();//
        em.persist(user);//persist
        }
    }

//attemp time 
//update login attemp time
@Override
    public void updateLoginAttempTime(String userName) {
        System.out.println("0==================================");
         Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
    System.out.println("1==================================");
        if(user==null)
            System.out.println("Member does not exist");
        
        else{
          
           Calendar cal = Calendar.getInstance();
           
               System.out.println("ah========================================"+cal);  
               user.setLast_attemp(cal);
        em.flush();//
        em.persist(user);//persist
        }
    }

//check if the different between current attemp and last failed attemp is more than 10 mins
@Override
    public boolean checkLockOut(String userName) {
    System.out.print("=========================================checkLock0");
    boolean AccountUnlock=false;
    System.out.print("=========================================checkLock11");
         Query q = em.createQuery("SELECT ua FROM UserAccount ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             user = (UserAccount) o;
        }
System.out.print("=========================================checkLock111"); 
        if(user==null)
            System.out.println("Member does not exist");
        
        else
        {System.out.print("=========================================checkLock1");
           Calendar lastAttemp=user.getLast_attemp();
           System.out.print("=========================================checkLock2");
           Calendar cal = Calendar.getInstance();
           
           System.out.print("=========================================checkLock3");
           System.out.print("=========================================checkLock"+lastAttemp);
           System.out.print("=========================================checkLock"+lastAttemp.getTimeInMillis());
           System.out.print("=========================================checkLock"+cal);
           System.out.print("=========================================checkLock"+cal.getTimeInMillis());
      long  diffMins = ( cal.getTimeInMillis()-lastAttemp.getTimeInMillis()) / (60 * 1000);
      
System.out.print("=========================================checkLock"+diffMins);
       if (diffMins>5)      
       { AccountUnlock= true;
       System.out.print("=========================================checkLock lock");}
        }
    return AccountUnlock;
    }


//=================================Accout lock-out==========================================




}
