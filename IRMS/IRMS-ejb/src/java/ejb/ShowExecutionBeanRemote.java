package ejb;

import entity.EntShow;
import exception.ExistException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 * @author Jiao Shen
 */

@Remote
public interface ShowExecutionBeanRemote {
    public EntShow addShow (String showName, String showVenue, Date showDate, Date startSalesDate, Long duration, String description) throws ExistException;
    public void editShow (Long showId, String showName, String showVenue, Date showDate, Date startSalesDate, Long duration, String description) throws ExistException;
    public void deleteShow (Long showId) throws ExistException;
    public List<EntShow> getShows();
    //public void addShow(String showName, String showVenue, Date showDate, Date startSalesDate, Long duration, String description) throws ExistException;
}
