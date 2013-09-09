/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.DiscountScheme;
import exception.ExistException;
import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface HotelDiscountBeanRemote {
    public void addDiscountScheme(String hotelName, String name, String eligibility, String description, double discountRate) throws ExistException;
    public void editDiscountScheme(String hotelName,String name, String eligibility, String description, double discountRate) throws ExistException;
    public void removeDiscountScheme(String hotelName, String name) throws ExistException;
    public Collection<DiscountScheme> getDiscountSchemes(String hotelName)throws ExistException;
}
