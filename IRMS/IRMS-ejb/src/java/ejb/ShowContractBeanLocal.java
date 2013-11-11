package ejb;

import exception.ExistException;
import java.util.Calendar;
import javax.ejb.Local;

/**
 * @author Jiao Shen
 */

@Local
public interface ShowContractBeanLocal {
     public void checkAvailability (String venueName, Calendar date) throws ExistException;
     public void reserveVenue (String venueName, Calendar date) throws ExistException;
     public void signContract (String showName, Calendar showDate, String showVenue, Calendar signDate) throws ExistException;
     public void terminateContract (Long contractId) throws ExistException;
}
