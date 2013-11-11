package ejb;

import entity.ShowContract;
import entity.Venue;
import exception.ExistException;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Jiao Shen
 */

@Stateless
public class ShowContractBean implements ShowContractBeanLocal {
    
    @PersistenceContext()
    EntityManager em;
    
    private ShowContract showContract; 
    private Venue venue;
    
    public ShowContractBean(){
    }
    
    @Override
    public void checkAvailability (String venueName, Calendar date) throws ExistException{
        venue = em.find(Venue.class, venueName);
        if (venue == null) {
            throw new ExistException("VENUE DOES NOT EXIST.");
        }
        //find the date --> check status
    }
    
    @Override
    public void reserveVenue (String venueName, Calendar date) throws ExistException{
        venue = em.find(Venue.class, venueName);
        if (venue == null) {
            throw new ExistException("VENUE DOES NOT EXIST.");
        }
        //find the date --> update status from "vacant" to "reserved"
    }
    
    @Override
    public void signContract (String showName, Calendar showDate, String showVenue, Calendar signDate) throws ExistException{
        showContract = em.find(ShowContract.class, showName);
        if (showContract != null) {
            throw new ExistException("CONTRACT ALREADY EXISTS.");
        }
        showContract = new ShowContract();
        showContract.createShowContract(showName, showDate, showVenue, signDate);
        em.persist(showContract);
    }
    
    @Override
    public void terminateContract (Long contractId) throws ExistException{
        showContract = em.find(ShowContract.class, contractId);
        if (showContract == null) {
            throw new ExistException("CONTRACT DOES NOT EXIST.");
        }
        em.remove(showContract);
        em.flush();
    }
   
}
