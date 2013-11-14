package ejb;

import entity.ShowContract;
import exception.ExistException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 * @author Jiao Shen
 */

@Remote
public interface ShowContractBeanRemote {
     //public void checkAvailability (String venueName, Calendar date) throws ExistException;
     //public void reserveVenue (String venueName, Calendar date) throws ExistException;
     public ShowContract signContract (String showName, Date showDate, String showVenue, Long staffId, Date signDate) throws ExistException;
     public void terminateContract (Long contractId) throws ExistException;
     public List<ShowContract> getContracts ();
}
