package ejb;

import entity.EntShow;
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
public class ShowExecutionBean implements ShowExecutionBeanRemote {
    
    @PersistenceContext()
    EntityManager em;
    
    private EntShow show;
    
    @Override
    public EntShow addShow (String showName, String showVenue, Date showDate, Date startSalesDate, Long duration, String description) throws ExistException{
        //show = em.find(EntShow.class, showId);
        //if (show != null) {
            //throw new ExistException("SHOW ALREADY EXISTS.");
        //}
        Query q = em.createQuery("SELECT e FROM EntShow e WHERE e.showVenue=:venue AND e.showDate=:date");
        //q.setParameter("name", showName);
        q.setParameter("venue", showVenue);
        q.setParameter("date", showDate);
        if(!q.getResultList().isEmpty()){
            throw new ExistException("CLASH EXIST");
        }
        
        show = new EntShow();
        System.out.println("Session-Execution-1======================================");
        show.createShow(showName, showVenue, showDate, startSalesDate, duration, description);
        System.out.println("Session-Execution-2======================================");
        em.persist(show);
        em.flush();
        return show;
    }
    
    @Override
    public void editShow (Long showId, String showName, String showVenue, Date showDate, Date startSalesDate, Long duration, String description) throws ExistException{
       //System.out.println("SESSION: editShow");
        show = em.find(EntShow.class, showId);
        if (show == null) {
            throw new ExistException("SHOW DOES NOT EXIST.");
        }
        //System.out.println("showName = "+showName);
        //System.out.println("startSalesDate ="+startSalesDate);
        //System.out.println("duration ="+duration);
        show.setShowName(showName);
        show.setShowVenue(showVenue);
        show.setShowDate(showDate);
        show.setStartSalesDate(startSalesDate);
        show.setDuration(duration);
        show.setDescription(description);
        //System.out.println("SESSION: editShow finished");
        em.flush();
        //return show;
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
    
    @Override
    public List<EntShow> getShows (){
        Query q = em.createQuery("SELECT s FROM EntShow s");
        return q.getResultList();
    }
    
}
