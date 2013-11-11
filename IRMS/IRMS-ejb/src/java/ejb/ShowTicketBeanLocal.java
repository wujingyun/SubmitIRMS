package ejb;

import exception.ExistException;
import javax.ejb.Local;

/**
 * @author Jiao Shen
 */

@Local
public interface ShowTicketBeanLocal {
    public void addCategory (String catName, double catPrice, int totalNum, int availNum) throws ExistException;
    public void editCatName (String oldName, String newName) throws ExistException;
    public void editCatPrice (String catName, double newPrice) throws ExistException;
    public void updateNum (String catName, int availNum, int soldNum) throws ExistException;
    public void deleteCategory (String catName) throws ExistException;
}
