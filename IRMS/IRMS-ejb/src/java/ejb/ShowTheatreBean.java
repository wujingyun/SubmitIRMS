package ejb;

import entity.Venue;
import exception.ExistException;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
 * @author Jiao Shen
 */

@Stateless
public class ShowTheatreBean implements ShowTheatreBeanLocal {

    @PersistenceContext()
    EntityManager em;
    
    Venue venue;

    public ShowTheatreBean() {
    }
    
    @Override
    public void add (String name, String status, String address, double price) throws ExistException{
        venue = em.find(Venue.class, name);
        if (venue != null) {
            throw new ExistException("VENUE ALREADY EXISTS.");
        }
        venue = new Venue();
        venue.createVenue(name, status, address, price);
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
    
    @Override
    public void updateStatus(String name,String newStatus) throws ExistException{
        venue = em.find(Venue.class, name);
        if (venue == null) {
            throw new ExistException("VENUE DOES NOT EXIST.");
        }
        venue.setVenueStatus(newStatus);
        em.flush();
    }
    
}
