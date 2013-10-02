/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.AccommodationBill;
import entity.Customer;
import entity.DiscountScheme;
import entity.Hotel;
import entity.MiniBarItem;
import entity.Room;
import entity.RoomReservation;
import entity.RoomServiceOrder;
import exception.ExistException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import structure.IncidentalCharge;
import structure.MiniBarConsumption;

/**
 *
 * @author Yang Zhennan
 */
@Stateless
public class HotelCheckInOutBean implements HotelCheckInOutBeanRemote {

    @PersistenceContext()
    EntityManager em;
    Hotel hotel;
    AccommodationBill accommodationBill;
    Customer customer;
    RoomReservation roomReservation;
    MiniBarItem miniBarItem;
    RoomServiceOrder roomServiceOrder;
    DiscountScheme discountScheme;

    @Override
    public List<AccommodationBill> getAccommodationBills(String hotelName) throws ExistException {

        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        Query q = em.createQuery("SELECT a FROM AccommodationBill a WHERE a.hotelName=:hotelName");
        q.setParameter("hotelName", hotelName);

        return q.getResultList();
    }

    @Override
    public AccommodationBill createAccommodationBill(Long reservationId, String hotelName) throws ExistException {
        accommodationBill = new AccommodationBill();
        accommodationBill.create();
        roomReservation = em.find(RoomReservation.class, reservationId);
        if (roomReservation == null) {
            throw new ExistException("ROOM RESERVATION NOT EXIST.");
        }
        accommodationBill.setDateTime(Calendar.getInstance().getTime());
        accommodationBill.setPaymentStatus("Pending");
        accommodationBill.setRoomReservation(roomReservation);
        accommodationBill.setRoomReservationId(reservationId);
        accommodationBill.setHotelName(hotelName);
        Integer size = roomReservation.getRooms().size();
        System.out.println("reservation size=" + size);
        for (int i = 0; i < size; i++) {
            roomReservation.getRooms().get(i).setAvailabilityStatus("Occupied");
        }
        em.persist(accommodationBill);
        em.flush();
        return accommodationBill;
    }

    @Override
    public void removeAccommodationBill(Long accommodationBillId) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        em.remove(accommodationBill);
        em.flush();
    }

    @Override
    public void addCallCharge(Long accommodationBillId, double callCharge) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        //System.out.println("AccommodationBill ID: "+accommodationBill.getId());
        //System.out.println("Call Charge: "+callCharge);
        double charge = accommodationBill.getOverseasCallCharge() + callCharge;
        //System.out.println("AccommodationBill charge: "+accommodationBill.getOverseasCallCharge());
        accommodationBill.setOverseasCallCharge(charge);
        //System.out.println("AccommodationBill charge: "+accommodationBill.getOverseasCallCharge());
        em.flush();
        //System.out.println("AccommodationBill charge: "+accommodationBill.getOverseasCallCharge());
    }

    @Override
    public void editCallCharge(Long accommodationBillId, double newCharge) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        accommodationBill.setOverseasCallCharge(newCharge);
        em.flush();
    }

    @Override
    public void addMiniBarItemCharge(Long accommodationBillId, String itemName, Integer quantity) throws ExistException {

        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        MiniBarConsumption consumption = new MiniBarConsumption();
        miniBarItem = em.find(MiniBarItem.class, itemName);
        if (miniBarItem == null) {
            throw new ExistException("MINI BAR ITEM NOT EXIST");
        }
        consumption.setMiniBarItem(miniBarItem);
        consumption.setQuantity(quantity);
        //System.out.println("Consumption quantity="+consumption.getQuantity());
        accommodationBill.getMiniBarConsumptions().add(consumption);
        em.flush();
    }

    @Override
    public boolean removeMiniBarItemCharge(Long accommodationBillId, String itemName) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        for (Object o : accommodationBill.getMiniBarConsumptions()) {
            MiniBarConsumption consumption = (MiniBarConsumption) o;
            if (consumption.getMiniBarItem().getName().equals(itemName)) {
                accommodationBill.getMiniBarConsumptions().remove(consumption);
                return true;
            }
        }
        System.out.println("THE ITEM IS NOT IN THIS ACCOMMODATION BILL.");
        return false;
    }

    @Override
    public void addRoomServiceOrder(Long accommodationBillId, Long roomServiceOrderId) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        roomServiceOrder = em.find(RoomServiceOrder.class, roomServiceOrderId);
        if (roomServiceOrder == null) {
            throw new ExistException("ROOM SERVICE ORDER NOT EXIST");
        }
        accommodationBill.getRoomServiceOrders().add(roomServiceOrder);
        em.flush();
    }

    @Override
    public void removeRoomServiceOrder(Long accommodationBillId, Long roomServiceOrderId) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        roomServiceOrder = em.find(RoomServiceOrder.class, roomServiceOrderId);
        if (roomServiceOrder == null) {
            throw new ExistException("ROOM SERVICE ORDER NOT EXIST");
        }
        accommodationBill.getRoomServiceOrders().remove(roomServiceOrder);
        em.flush();
    }

    @Override
    public void addIncidentalCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException {
        IncidentalCharge incidentalCharge = new IncidentalCharge();
        incidentalCharge.setChargeName(name);
        incidentalCharge.setDescription(description);
        incidentalCharge.setPrice(charge);
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        accommodationBill.getIncidentalCharges().add(incidentalCharge);
        em.flush();
    }

    @Override
    public boolean removeIncidentalCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        for (Object o : accommodationBill.getIncidentalCharges()) {
            IncidentalCharge incidentalCharge = (IncidentalCharge) o;
            if (incidentalCharge.getChargeName().equals(name) && incidentalCharge.getDescription().equals(description) && incidentalCharge.getPrice() == charge) {
                accommodationBill.getIncidentalCharges().remove(incidentalCharge);
                return true;
            }
        }
        System.out.println("THE CHARGE IS NOT FOUND IN THIS ACCOMMODATION BILL.");
        return false;
    }

    @Override
    public double addDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        discountScheme = em.find(DiscountScheme.class, discountScheme);
        if (discountScheme == null) {
            throw new ExistException("DISCOUNT SCHEME ORDER NOT EXIST");
        }
        accommodationBill.getDiscountSchemes().add(discountScheme);
        em.flush();
        return discountScheme.getDiscountRate();
    }

    @Override
    public void removeDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        discountScheme = em.find(DiscountScheme.class, discountScheme);
        if (discountScheme == null) {
            throw new ExistException("DISCOUNT SCHEME ORDER NOT EXIST");
        }
        accommodationBill.getDiscountSchemes().remove(discountScheme);
        em.flush();
    }

    @Override
    public double tallyBill(Long accommodationBillId) throws ExistException {
        double total = 0;

        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        total += accommodationBill.getRoomReservation().getTotal();
        total += accommodationBill.getOverseasCallCharge();
        if (accommodationBill.getRoomServiceOrders() != null) {
            for (Object o : accommodationBill.getRoomServiceOrders()) {
                RoomServiceOrder serviceOrder = (RoomServiceOrder) o;
                total += serviceOrder.getTotal();
            }
        }
        if (accommodationBill.getMiniBarConsumptions() != null) {
            for (Object o : accommodationBill.getMiniBarConsumptions()) {
                MiniBarConsumption consumption = (MiniBarConsumption) o;
                total += consumption.getTotalCharge();
            }
        }
        if (accommodationBill.getIncidentalCharges() != null) {
            for (Object o : accommodationBill.getIncidentalCharges()) {
                IncidentalCharge incidentalCharge = (IncidentalCharge) o;
                total += incidentalCharge.getPrice();
            }
        }
        if (accommodationBill.getDiscountSchemes() != null) {
            for (Object o : accommodationBill.getDiscountSchemes()) {
                DiscountScheme discount = (DiscountScheme) o;
                total *= (1 - discount.getDiscountRate());
            }
        }
        return total;
    }

    @Override
    public double getRoomServiceTotal(Long accommodationBillId) throws ExistException {
        double total = 0;

        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        if (accommodationBill.getRoomServiceOrders() != null) {
            for (Object o : accommodationBill.getRoomServiceOrders()) {
                RoomServiceOrder serviceOrder = (RoomServiceOrder) o;
                total += serviceOrder.getTotal();
            }
        }
        return total;
    }

    @Override
    public double getIncidentalTotal(Long accommodationBillId) throws ExistException {
        double total = 0;

        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        if (accommodationBill.getIncidentalCharges() != null) {
            for (Object o : accommodationBill.getIncidentalCharges()) {
                IncidentalCharge incidentalCharge = (IncidentalCharge) o;
                total += incidentalCharge.getPrice();
            }
        }
        return total;
    }

    @Override
    public double getMiniBarTotal(Long accommodationBillId) throws ExistException {
        double total = 0;

        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        if (accommodationBill.getMiniBarConsumptions() != null) {
            for (Object o : accommodationBill.getMiniBarConsumptions()) {
                MiniBarConsumption consumption = (MiniBarConsumption) o;
                total += consumption.getTotalCharge();
            }
        }
        return total;
    }
    
    

    @Override
    public void updatePaymentStatus(Long accommodationBillId, String paymentStatus) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        accommodationBill.setPaymentStatus(paymentStatus);
        em.flush();
    }

    @Override
    public void updateRoomAvailabilityStatus(Long accommodationBillId) throws ExistException {
        accommodationBill = em.find(AccommodationBill.class, accommodationBillId);
        if (accommodationBill == null) {
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        for(Object o:accommodationBill.getRoomReservation().getRooms()){
            Room r=(Room) o;
            r.setAvailabilityStatus("Available");
        }
        em.flush();
    }

    @Override
    public List<String> getDiscountSchemes(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        hotel.getDiscountSchemes().size();
        List<String> l=new ArrayList<String>();
        for(int i=0; i<hotel.getDiscountSchemes().size(); i++){
            l.add(hotel.getDiscountSchemes().get(i).getName());
        }
        return (List)l;
    }
    
    
    @Override
    public void checkIn(Long customerId, Long reservationId) throws ExistException {
    }

    @Override
    public void checkOut(Long accommodationBillId) throws ExistException {
    }
}
