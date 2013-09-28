/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Customer;
import entity.UserAccount;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
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

public class CustomerBean implements CustomerBeanRemote
{
    @PersistenceContext
    EntityManager em;
    Customer cu;
  private Customer customer;
    
      @Override
  public void createCustomer(String userName, String password, String firstName, String lastName, String address, String email, 
       String ageGroup, String gender, String moilePhoneNumber)
    {    //cu = new Customer();
        //try{
            Query q = em.createQuery("SELECT c FROM Customer c where c.userName=?1");
         q.setParameter(1,userName);
      
      //  System.out.println( " find user result=========================================================="+q.getResultList().size());
        if(q.getResultList().size()!=0) {
        System.out.println("Username already exist");
        } 
        else {
            cu = new Customer ();

           cu.create(userName, password, firstName, lastName, address, email,ageGroup , gender,moilePhoneNumber);
        }
        
            //customer.setPassword(RandomPasswordGenerator.Password());
            cu.setLoyaltyPointBalance(0);
          //  customer.setCustomerTier(CustomerTier.CLASSIC);
            Calendar cal = Calendar.getInstance();
            cu.setRegistrationTimestamp(cal);
            cu.setLast_attemp(cal);
        
        
            em.persist(cu);
            em.flush();
            em.refresh(cu);

            //smsSessionBeanLocal.sendNewCustomerPassword(customer);

          /*  return customer.getCustomerId();
        }
        catch(Exception ex)
        {
           // return null;
        }*/
    }
    
    
    @Override
  public Customer findCustomer(String username) {
           Query q = em.createQuery("SELECT c FROM Customer c where c.userName=?1");
         q.setParameter(1,username);
      
        System.out.println( " find user result=========================================================="+q.getResultList().size());
        if(q.getResultList().size()!=0) {
               for (Object o : q.getResultList()) {
             customer = (Customer) o;
        }return customer;
        }
      
        else {
        return null;}
    }

    
    @Override
  public boolean checkUserExist(String username) {
        Query q = em.createQuery("SELECT c FROM Customer c where c.userName=?1");
         q.setParameter(1,username);
         
        if(q.getResultList().size()==0) {
            System.out.println( " No same user ==========================================================");
            return false;
           
        }
  System.out.println( " same user exist==========================================================");
        return true;
         
    }
    /*@Override
    public void updateCustomer(Customer customer)
    {
        em.merge(customer);        
    }*/
      
      
           
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
    
    Query q = em.createQuery("SELECT ua FROM Customer ua where ua.userName=?1");
          q.setParameter(1,userName);
        for (Object o : q.getResultList()) {
             customer = (Customer) o;
        }
        if(customer==null)
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
              customer.setPassword(hexString.toString());
        em.flush();//
        em.persist(customer);//persist
     return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
            
        }
     
    
}



@Override
 public boolean verifyPassword(String userName, String password){
        Query q = em.createQuery("SELECT u from Customer u");
        List<Customer> customers = q.getResultList();

        for (Customer u : customers) {
//            UserAccount u = (UserAccount) o;
                      //  System.out.println("========================================================1password");
            if(u.getUserName().equals(userName)){
            System.out.println("========================================================2verifypass"+userName);
                if(u.getPassword().equals(password))
                {    System.out.println("========================================================3verifypass"+password);
                    return true;}
                break;
                 
            }
        }
        return false;
    }
//=================================Accout lock-out========================================== 
//get  number of login attemped
@Override
    public int getLoginAttemp(String userName) {
     int loginAttemp=0;
         Query q = em.createQuery("SELECT c FROM Customer c where c.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             customer = (Customer) o;
        }
 
        if(customer==null)
        {  System.out.println("Customer does not exist");
       
        }
        else
        {
            loginAttemp=customer.getLogginAttemp();
           
        } return loginAttemp;
    }


//update number of login attemped
@Override
    public void updateLoginAttemp(String userName) {
      System.out.println("0==================================");
         Query q = em.createQuery("SELECT ua FROM Customer ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             customer = (Customer) o;
        }
   System.out.println("1==================================");
        if(customer==null)
            System.out.println("Member does not exist");
        
        else
        {  System.out.println("2==================================");
            int loginAttemp=customer.getLogginAttemp();
              System.out.println("3==================================");
            loginAttemp=loginAttemp+1;
              System.out.println("4==================================");
             customer.setLogginAttemp(loginAttemp);
        em.flush();//
        em.persist(customer);//persist
        }
    }




@Override
    public void setLoginAttempToZero(String userName) {
         Query q = em.createQuery("SELECT ua FROM Customer ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             customer = (Customer) o;
        }
 
        if(customer==null)
            System.out.println("Member does not exist");
        
        else
        {
           
             customer.setLogginAttemp(0);
        em.flush();//
        em.persist(customer);//persist
        }
    }

//attemp time 
//update login attemp time
@Override
    public void updateLoginAttempTime(String userName) {
        System.out.println("0==================================");
         Query q = em.createQuery("SELECT ua FROM Customer ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             customer = (Customer) o;
        }
    System.out.println("1==================================");
        if(customer==null)
            System.out.println("Member does not exist");
        
        else{
          
           Calendar cal = Calendar.getInstance();
           
               System.out.println("ah========================================"+cal);  
               customer.setLast_attemp(cal);
        em.flush();//
        em.persist(customer);//persist
        }
    }

//check if the different between current attemp and last failed attemp is more than 10 mins
@Override
    public boolean checkLockOut(String userName) {
    System.out.print("=========================================checkLock0");
    boolean AccountUnlock=false;
    System.out.print("=========================================checkLock11");
         Query q = em.createQuery("SELECT ua FROM Customer ua where ua.userName=?1");
          q.setParameter(1,userName);
          for (Object o : q.getResultList()) {
             customer = (Customer) o;
        }
System.out.print("=========================================checkLock111"); 
        if(customer==null)
            System.out.println("Member does not exist");
        
        else
        {System.out.print("=========================================checkLock1");
           Calendar lastAttemp=customer.getLast_attemp();
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
