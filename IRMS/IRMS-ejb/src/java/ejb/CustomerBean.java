/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Customer;
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
    Customer customer;
   
    //@EJB
    //SmsSessionBeanLocal smsSessionBeanLocal;
    
    
      
   
    @Override
    public List<Customer> getAllCustomers()
    {
        Query query = em.createNamedQuery("getAllCustomers");
        
        return query.getResultList();
    }
    
    
    
      @Override
  public Long createCustomer(String Username, String firstName, String lastName, String address, String email, String password, 
            String mobilePhoneCountryCode, String moilePhoneNumber, Integer lotaltyPointBalance)
    {
        try
        {customer = em.find(Customer.class, Username);
        
        if (customer != null) {
           
        System.out.println("Username already exist");
        } 
        else {
            customer = new Customer ();
           
           customer.create(Username, firstName, lastName, address, email, mobilePhoneCountryCode, moilePhoneNumber);
        }
        
            //customer.setPassword(RandomPasswordGenerator.Password());
            customer.setLoyaltyPointBalance(0);
          //  customer.setCustomerTier(CustomerTier.CLASSIC);
            customer.setRegistrationTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
 
        
        
            em.persist(customer);
            em.flush();
            em.refresh(customer);

            //smsSessionBeanLocal.sendNewCustomerPassword(customer);

            return customer.getCustomerId();
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    
    
    /*@Override
    public void updateCustomer(Customer customer)
    {
        em.merge(customer);        
    }*/

}
