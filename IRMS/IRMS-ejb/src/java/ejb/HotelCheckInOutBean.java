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
    public void addCallCharge(Long accommodationBillId, double callCharge) throws ExistException {
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        double charge=accommodationBill.getOverseasCallCharge()+callCharge;
        accommodationBill.setOverseasCallCharge(charge);
        em.flush();
    }

    @Override
    public void editCallCharge(Long accommodationBillId, double newCharge) throws ExistException {
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        accommodationBill.setOverseasCallCharge(newCharge);
        em.flush();
    }

    @Override
    public void addMiniBarItemCharge(Long accommodationBillId, String itemName, Integer quantity) throws ExistException {
        MiniBarConsumption consumption=new MiniBarConsumption(itemName, quantity);
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        accommodationBill.getMiniBarConsumptions().add(consumption);
        em.flush();
    }

    @Override
    public boolean removeMiniBarItemCharge(Long accommodationBillId, String itemName) throws ExistException {
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        for(Object o: accommodationBill.getMiniBarConsumptions()){
            MiniBarConsumption consumption= (MiniBarConsumption) o;
            if(consumption.getMiniBarItem().getName().equals(itemName)){
                accommodationBill.getMiniBarConsumptions().remove(consumption);
                return true;
            }
        }
        System.out.println("THE ITEM IS NOT IN THIS ACCOMMODATION BILL.");
        return false;
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
        accommodationBill.getRoomServiceOrders().add(roomServiceOrder);
        em.flush();
    }

    @Override
    public void removeRoomServiceOrder(Long accommodationBillId, Long roomServiceOrderId) throws ExistException {
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        roomServiceOrder=em.find(RoomServiceOrder.class, roomServiceOrderId);
        if(roomServiceOrder==null){
            throw new ExistException("ROOM SERVICE ORDER NOT EXIST");
        }
        accommodationBill.getRoomServiceOrders().remove(roomServiceOrder);
        em.flush();
    }

    @Override
    public void addIncidentalCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException {
        IncidentalCharge incidentalCharge=new IncidentalCharge(name, charge, description);
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        accommodationBill.getIncidentalCharges().add(incidentalCharge);
        em.flush();
    }

    @Override
    public boolean removeIncidentalCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException {
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        for(Object o: accommodationBill.getIncidentalCharges()){
            IncidentalCharge incidentalCharge=(IncidentalCharge) o;
            if(incidentalCharge.getChargeName().equals(name)&&incidentalCharge.getDescription().equals(description)&&incidentalCharge.getPrice()==charge){
                accommodationBill.getIncidentalCharges().remove(incidentalCharge);
                return true;
            }
        }
        System.out.println("THE CHARGE IS NOT FOUND IN THIS ACCOMMODATION BILL.");
        return false;
    }

    @Override
    public void addDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException {
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        discountScheme=em.find(DiscountScheme.class, discountScheme);
        if(discountScheme==null){
            throw new ExistException("DISCOUNT SCHEME ORDER NOT EXIST");
        }
        accommodationBill.getDiscountSchemes().add(discountScheme);
        em.flush();
    }

    @Override
    public void removeDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException {
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        discountScheme=em.find(DiscountScheme.class, discountScheme);
        if(discountScheme==null){
            throw new ExistException("DISCOUNT SCHEME ORDER NOT EXIST");
        }
        accommodationBill.getDiscountSchemes().remove(discountScheme);
        em.flush();
    }

    @Override
    public double tallyBill(Long accommodationBillId) throws ExistException {
        double total=0;
        
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        total+=accommodationBill.getRoomReservation().getTotal();
        total+=accommodationBill.getOverseasCallCharge();
        for(Object o: accommodationBill.getRoomServiceOrders()){
            RoomServiceOrder serviceOrder=(RoomServiceOrder) o;
            total+=serviceOrder.getTotal();
        }
        for(Object o: accommodationBill.getMiniBarConsumptions()){
            MiniBarConsumption consumption=(MiniBarConsumption) o;
            total+=consumption.getTotalCharge();
        }
        for(Object o: accommodationBill.getIncidentalCharges()){
            IncidentalCharge incidentalCharge=(IncidentalCharge) o;
            total+=incidentalCharge.getPrice();
        }
        for(Object o: accommodationBill.getDiscountSchemes()){
            DiscountScheme discount=(DiscountScheme) o;
            total*=(1-discount.getDiscountRate());
        }
        
        return total;
    }

    @Override
    public void checkIn(Long customerId, Long reservationId) throws ExistException {
        
    }

    @Override
    public void checkOut(Long accommodationBillId) throws ExistException {
    }

}
