/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.DiscountScheme;
import exception.ExistException;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface HotelDiscountBeanRemote {
    public void addDiscountScheme(String hotelName, String name, String eligibility, String description, double discountRate, Calendar dateCreated) throws ExistException;
    public void editDiscountScheme(String hotelName,String name, String eligibility, String description, double discountRate) throws ExistException;
    public void removeDiscountScheme(String hotelName, String name) throws ExistException;
    public List<DiscountScheme> getDiscountSchemes(String hotelName)throws ExistException;
}
