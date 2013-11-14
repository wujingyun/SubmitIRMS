package ejb;

import entity.Venue;
import exception.ExistException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/*
 * @author Jiao Shen
 */

@Stateless
public class ShowTheatreBean implements ShowTheatreBeanRemote {

    @PersistenceContext()
    EntityManager em;
    
    Venue venue;

    public ShowTheatreBean() {
    }
    
    @Override
    public void add (String name, String address, double price) throws ExistException{
        venue = em.find(Venue.class, name);
        if (venue != null) {
            throw new ExistException("VENUE ALREADY EXISTS.");
        }
        venue = new Venue();
        System.out.println("Session-Theatre-1======================================");
        venue.createVenue(name, address, price);
        System.out.println("Session-Theatre-2======================================");
        em.persist(venue);
    }
    
    @Override
    public void edit (String name, String address, double price) throws ExistException{
        venue = em.find(Venue.class, name);
        if (venue == null) {
            throw new ExistException("VENUE DOES NOT EXIST.");
        }
        venue.setVenueName(name);
        venue.setVenueAddr(address);
        venue.setPrevailingRates(price);
        em.flush();
    }
    
    @Override
    public void remove (String name) throws ExistException{
        venue = em.find(Venue.class, name);
        if (venue == null) {
            throw new ExistException("VENUE DOES NOT EXIST.");
        }
        em.remove(venue);
        em.flush();
    }
    
    /*
    @Override
    public void updateStatus(String name,String newStatus) throws ExistException{
        venue = em.find(Venue.class, name);
        if (venue == null) {
            throw new ExistException("VENUE DOES NOT EXIST.");
        }
        venue.setVenueStatus(newStatus);
        em.flush();
    }
    */
    
    @Override
    public List<Venue> getVenues (){
        Query q = em.createQuery("SELECT v FROM Venue v");
        return q.getResultList();
    }
    
}
