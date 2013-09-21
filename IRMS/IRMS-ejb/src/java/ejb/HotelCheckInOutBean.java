/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.AccommodationBill;
import entity.Customer;
import entity.DiscountScheme;
import entity.MiniBarItem;
import entity.RoomReservation;
import entity.RoomServiceOrder;
import exception.ExistException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yang Zhennan
 */
@Stateless
public class HotelCheckInOutBean implements HotelCheckInOutBeanRemote {
    
    @PersistenceContext()
    EntityManager em;
    AccommodationBill accommodationBill;
    Customer customer;
    RoomReservation roomReservation;
    MiniBarItem miniBarItem;
    RoomServiceOrder roomServiceOrder;
    DiscountScheme discountScheme;

    @Override
    public void createAccommodationBill(Long reservationId) throws ExistException {
        accommodationBill=new AccommodationBill();
        accommodationBill.create();
        
    }

    @Override
    public void addCallCharge(Long accommodationBillId, double callRate, double callTime) throws ExistException {
    }

    @Override
    public void removeCallCharge(Long accommodationBillId) throws ExistException {
    }

    @Override
    public void addMiniBarItemCharge(Long accommodationBillId, String itemName, Integer quantity) throws ExistException {
    }

    @Override
    public void removeMiniBarItemCharge(Long accommodationBillId, String itemName) throws ExistException {
    }

    @Override
    public void addRoomServiceOrder(Long accommodationBillId, Long roomServiceOrderId) throws ExistException {
    }

    @Override
    public void removeRoomServiceOrder(Long accommodationBillId, Long roomRerviceOrderId) throws ExistException {
    }

    @Override
    public void addOtherCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException {
    }

    @Override
    public void removeOtherCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException {
    }

    @Override
    public void addDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException {
    }

    @Override
    public void removeDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException {
    }

    @Override
    public double tallyBill(Long accommodationBillId) throws ExistException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void checkIn(Long customerId, Long reservationId) throws ExistException {
    }

    @Override
    public void checkOut(Long accommodationBillId) throws ExistException {
    }

}
