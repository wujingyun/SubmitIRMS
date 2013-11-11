/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Customer;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author WU JINGYUN
 */
@Remote
public interface CustomerBeanRemote {
      
      //public void registerCustomer(String Username, String firstName, String lastName, String address, String email,
      // String password, String mobilePhoneCountryCode, String moilePhoneNumber, Integer lotaltyPointBalance);
     
       public void createCustomer(String userName, String password, String firstName, String lastName, String address, String email, 
       String ageGroup, String gender, String moilePhoneNumber,String sq, String answer); 
public List<Customer> getCustomerList() ;
    public String hashPassword(String password);

    public String hashPassword2(long uid, String password);

    public boolean verifyPassword(String userName, String password);

    public int getLoginAttemp(String userName);

    public void updateLoginAttemp(String userName);

    public boolean setHashPassword(String userName, String password);

    public void setLoginAttempToZero(String userName);

    public void updateLoginAttempTime(String userName);

    public boolean checkLockOut(String userName);

    public Customer findCustomer(String username);

   
    public Customer getCustomerById(long Id);

    public boolean checkCustomerExist(String username);
}
