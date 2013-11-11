package ejb;

import entity.EntShow;
import exception.ExistException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * @author Jiao Shen
 */

@Local
public interface ShowExecutionBeanLocal {
    public void addShow (String showName, Date showDate, String showVenue, Date startSalesDate, Long duration, String language) throws ExistException;
    public void editShow (String showName, Date showDate, String showVenue, Date startSalesDate, Long duration, String language) throws ExistException;
    public void deleteShow (Long showId) throws ExistException;
    
}
