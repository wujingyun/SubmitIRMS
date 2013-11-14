package ejb;

import entity.TicketCat;
import exception.ExistException;
import java.util.List;
import javax.ejb.Remote;

/**
 * @author Jiao Shen
 */

@Remote
public interface ShowTicketBeanRemote {
    public TicketCat addCategory (Long showId, String catName, double catPrice, int totalNum, int availNum) throws ExistException;
    public void editCategory (Long catId, String catName, double catPrice, int totalNum, int availNum) throws ExistException;
    public void deleteCategory (Long catId) throws ExistException;
    public List<TicketCat> getTicketCats ();
    //public void addSeat (long seatId, String seatStatus) throws ExistException;
}
