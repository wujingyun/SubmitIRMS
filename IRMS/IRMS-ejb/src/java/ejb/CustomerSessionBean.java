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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author WU JINGYUN
 */
@Stateless

public class CustomerSessionBean implements CustomerSessionBeanRemote
{
    @PersistenceContext
    private EntityManager em;
    
    //@EJB
    //SmsSessionBeanLocal smsSessionBeanLocal;
    
    
    
    @Override
    public List<Customer> getAllCustomers()
    {
        Query query = em.createNamedQuery("getAllCustomers");
        
        return query.getResultList();
    }
    
    
    
    /*  @Override
  public Long createCustomer(Customer customer)
    {
        try
        {
            customer.setPassword(RandomPasswordGenerator.Password());
            customer.setLoyaltyPointBalance(0);
            customer.setCustomerTier(CustomerTier.CLASSIC);
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
    }*/
    
    
    
    /*@Override
    public void updateCustomer(Customer customer)
    {
        em.merge(customer);        
    }*/
}
