package ejb;

import entity.ShowContract;
import entity.Venue;
import exception.ExistException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Jiao Shen
 */

@Stateless
public class ShowContractBean implements ShowContractBeanRemote {
    
    @PersistenceContext()
    EntityManager em;
    
    private ShowContract showContract; 
    private Venue venue;
    
    public ShowContractBean(){
    }
    
    /*
    @Override
    public void checkAvailability (String venueName, Calendar date) throws ExistException{
        venue = em.find(Venue.class, venueName);
        if (venue == null) {
            throw new ExistException("VENUE DOES NOT EXIST.");
        }
        //list all "showContract" with same "showVenue" --> check if "showDate" is taken
    }
    */
    
    /*
    @Override
    public void reserveVenue (String venueName, Calendar date) throws ExistException{
        venue = em.find(Venue.class, venueName);
        if (venue == null) {
            throw new ExistException("VENUE DOES NOT EXIST.");
        }
        //find the date --> update status from "vacant" to "reserved"
    }
    */
    
    @Override
    public ShowContract signContract (String showName, Date showDate, String showVenue, Long staffId, Date signDate) throws ExistException{
        //check availability
        Query q = em.createQuery("SELECT c FROM ShowContract c WHERE AND c.showVenue=:venue AND c.showDate=:date");
        //q.setParameter("name", showName);
        q.setParameter("venue", showVenue);
        q.setParameter("date", showDate);
        if(!q.getResultList().isEmpty()){
            throw new ExistException("CLASH EXIST");
        }
        
        showContract = new ShowContract();
        System.out.println("Session-Contract-1======================================");
        showContract.createShowContract(showName, showDate, showVenue, staffId, signDate);
        System.out.println("Session-Contract-2======================================");
        em.persist(showContract);
        em.flush();
        return showContract;
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
   
    @Override
    public List<ShowContract> getContracts (){
        Query q = em.createQuery("SELECT c FROM ShowContract c");
        return q.getResultList();
    }
    
}
