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
        roomReservation=em.find(RoomReservation.class,reservationId);
        if(roomReservation==null){
            throw new ExistException("ROOM RESERVATION NOT EXIST.");
        }
        accommodationBill.setRoomReservation(roomReservation);
        double total=0+roomReservation.getTotal();
        accommodationBill.setTotal(total);
        em.persist(accommodationBill);
    }

    @Override
    public void removeAccommodationBill(Long accommodationBillId) throws ExistException {
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        em.remove(accommodationBill);
        em.flush();
    }

    @Override
    public void addCallCharge(Long accommodationBillId, double callRate, double callTime) throws ExistException {
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        //accommodationBill.getOverseasCall()
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
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        roomServiceOrder=em.find(RoomServiceOrder.class, roomServiceOrderId);
        if(roomServiceOrder==null){
            throw new ExistException("ROOM SERVICE ORDER NOT EXIST");
        }
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
