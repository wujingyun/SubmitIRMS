package ejb;

import entity.EntShow;
import entity.TicketCat;
import entity.TicketSeat;
import exception.ExistException;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 * @author WU JINGYUN
 */

@Remote
public interface ShowBeanRemote {
   public List<TicketSeat> getAllSeat (Long showid) throws ExistException;
  public List<EntShow> getShowByName (String showName) throws ExistException;
  public String getShowNameById(Long showId) throws ExistException ;
   public List<TicketCat> getCategoryInfo(Long showid) throws ExistException ;
    public void makeBooking(Long customerId, List<Long> selectedSeats) throws ExistException;

    public double getTotalAmount(List<Long> selectedSeats) throws ExistException;
}
