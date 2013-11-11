package ejb;

import exception.ExistException;
import java.util.Calendar;
import javax.ejb.Local;

/* 
 * @author Jiao Shen
 */

@Local
public interface ShowTheatreBeanLocal {
    public void add (String name, String status, String address, double price) throws ExistException;
    public void edit (String name, String address, double price) throws ExistException;
    public void remove (String name) throws ExistException;
    public void updateStatus(String name,String newStatus) throws ExistException;
}