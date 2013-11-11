package ejb;

import entity.EntShow;
import entity.TicketCat;
import exception.ExistException;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Jiao Shen
 */

@Stateless
public class ShowTicketBean implements ShowTicketBeanLocal {
    
    @PersistenceContext()
    EntityManager em;
    
    private TicketCat ticketCat;
    
    @Override
    public void addCategory (String catName, double catPrice, int totalNum, int availNum) throws ExistException{
        ticketCat = em.find(TicketCat.class, catName);
        if (ticketCat != null) {
            throw new ExistException("CATEGORY ALREADY EXISTS.");
        }
        ticketCat = new TicketCat();
        ticketCat.createTicketCat(catName, catPrice, totalNum, availNum);
        em.persist(ticketCat);
    }
    
    @Override
    public void editCatName (String oldName, String newName) throws ExistException{
        ticketCat = em.find(TicketCat.class, oldName);
        if (ticketCat == null) {
            throw new ExistException("CATEGORY DOES NOT EXIST.");
        }
        ticketCat.setCatName(newName);
        em.flush();
    }
    
    @Override
    public void editCatPrice (String catName, double newPrice) throws ExistException{
        ticketCat = em.find(TicketCat.class, catName);
        if (ticketCat == null) {
            throw new ExistException("CATEGORY DOES NOT EXIST.");
        }
        ticketCat.setCatPrice(newPrice);
        em.flush();
    }
    
    @Override
    public void updateNum (String catName, int availNum, int soldNum) throws ExistException{
        ticketCat = em.find(TicketCat.class, catName);
        if (ticketCat == null) {
            throw new ExistException("CATEGORY DOES NOT EXIST.");
        }
        ticketCat.setAvailNum(availNum-soldNum);
        em.flush();
    }
    
    @Override
    public void deleteCategory (String catName) throws ExistException{
        ticketCat = em.find(TicketCat.class, catName);
        if (ticketCat == null) {
            throw new ExistException("CATEGORY DOES NOT EXIST.");
        }
        em.remove(ticketCat);
        em.flush();
    }
    
}
