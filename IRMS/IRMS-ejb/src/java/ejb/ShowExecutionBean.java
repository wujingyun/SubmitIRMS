package ejb;

import entity.EntShow;
import exception.ExistException;
import java.util.ArrayList;
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
public class ShowExecutionBean implements ShowExecutionBeanLocal {
    
    @PersistenceContext()
    EntityManager em;
    
    private EntShow show;
 
    @Override
    public void addShow (String showName, Date showDate, String showVenue, Date startSalesDate, Long duration, String language) throws ExistException{
        show = em.find(EntShow.class, showName);
        if (show != null) {
            throw new ExistException("SHOW ALREADY EXISTS.");
        }
        show = new EntShow();
        show.createShow(showName, showDate, showVenue, startSalesDate, duration, language);
        em.persist(show);
    }
    
    @Override
    public void editShow (String showName, Date showDate, String showVenue, Date startSalesDate, Long duration, String language) throws ExistException{
        show = em.find(EntShow.class, showName);
        if (show == null) {
            throw new ExistException("SHOW DOES NOT EXIST.");
        }
        show.setShowName(showName);
        show.setShowDate(showDate);
        show.setShowVenue(showVenue);
        show.setStartSalesDate(startSalesDate);
        show.setDuration(duration);
        show.setLanguage(language);
        em.flush();
    }
    
    @Override
    public void deleteShow (Long showId) throws ExistException{
        show = em.find(EntShow.class, showId);
        if (show == null) {
            throw new ExistException("SHOW DOES NOT EXIST.");
        }
        em.remove(show);
        em.flush();
    }
    
   
        
}
