/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.DiscountScheme;
import entity.Hotel;
import exception.ExistException;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yang Zhennan
 */
@Stateless
public class HotelDiscountBean implements HotelDiscountBeanRemote {

    @PersistenceContext()
    EntityManager em;
    Hotel hotel;
    DiscountScheme discountScheme;

    public HotelDiscountBean() {
    }

    @Override
    public void addDiscountScheme(String hotelName, String name, String eligibility, String description, double discountRate) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        discountScheme = hotel.findDiscountScheme(name);
        if (discountScheme != null) {
            throw new ExistException("DISCOUNT SCHEME ALREADY EXISTS.");
        }
        discountScheme = new DiscountScheme();
        discountScheme.create(name, eligibility, description, discountRate);
        discountScheme.setHotel(hotel);
        hotel.getDiscountSchemes().add(discountScheme);
        hotel.setDiscountSchemes(hotel.getDiscountSchemes());
        em.persist(discountScheme);
    }

    @Override
    public void editDiscountScheme(String hotelName, String name, String eligibility, String description, double discountRate) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        discountScheme = hotel.findDiscountScheme(name);
        if (discountScheme == null) {
            throw new ExistException("DISCOUNT SCHEME NOT EXIST.");
        }
        discountScheme.setName(name);
        discountScheme.setEligibility(eligibility);
        discountScheme.setDescription(description);
        discountScheme.setDiscountRate(discountRate);
        discountScheme.setDateCreated(Calendar.getInstance());
        em.flush();
    }

    @Override
    public void removeDiscountScheme(String hotelName, String name) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        discountScheme = hotel.findDiscountScheme(name);
        if (discountScheme == null) {
            throw new ExistException("DISCOUNT SCHEME NOT EXIST.");
        }
        hotel.getDiscountSchemes().remove(discountScheme);
        hotel.setDiscountSchemes(hotel.getDiscountSchemes());
        em.remove(discountScheme);
        em.flush();
    }

    @Override
    public Collection<DiscountScheme> getDiscountSchemes(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        return hotel.getDiscountSchemes();
    }
}
