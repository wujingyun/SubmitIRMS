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
      List<Customer> getAllCustomers();
      //public void registerCustomer(String Username, String firstName, String lastName, String address, String email,
      // String password, String mobilePhoneCountryCode, String moilePhoneNumber, Integer lotaltyPointBalance);
     
    public Long createCustomer(String Username, String firstName, String lastName, String address, String email, String password, String mobilePhoneCountryCode, String moilePhoneNumber, Integer lotaltyPointBalance);
      
}
