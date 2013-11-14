package ejb;

import entity.Venue;
import exception.ExistException;
import java.util.List;
import javax.ejb.Remote;

/* 
 * @author Jiao Shen
 */

@Remote
public interface ShowTheatreBeanRemote {
    public void add (String name, String address, double price) throws ExistException;
    public void edit (String name, String address, double price) throws ExistException;
    public void remove (String name) throws ExistException;
    //public void updateStatus (String name,String newStatus) throws ExistException;
    public List<Venue> getVenues ();
}