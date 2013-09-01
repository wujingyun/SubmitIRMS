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
public interface CustomerSessionBeanRemote {
      List<Customer> getAllCustomers();
}
