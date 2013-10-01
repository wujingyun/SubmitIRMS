/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.AccommodationBill;
import entity.Hotel;
import entity.RoomService;
import entity.RoomServiceOrder;
import exception.ExistException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yang Zhennan
 */
@Stateless
public class RoomServiceBean implements RoomServiceBeanRemote {

    @PersistenceContext()
    EntityManager em;
    Hotel hotel;
    RoomService roomService;
    RoomServiceOrder serviceOrder;
    AccommodationBill accommodationBill;

    public RoomServiceBean() {
    }

    @Override
    public void addRoomService(String hotelName, String name, String description, double price) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        roomService = hotel.findRoomService(name);
        if (roomService != null) {
            throw new ExistException("ROOM SERIVCE ALREADY EXISTS.");
        }
        roomService = new RoomService();
        roomService.create(name, description, price);
        roomService.setHotel(hotel);
        hotel.getRoomServices().add(roomService);
        hotel.setRoomServices(hotel.getRoomServices());
        em.persist(roomService);
    }

    @Override
    public void editRoomService(String hotelName, String name, String description, double price) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        roomService = hotel.findRoomService(name);
        if (roomService == null) {
            throw new ExistException("ROOM SERVICE NOT EXIST.");
        }
        roomService.setName(name);
        roomService.setDescription(description);
        roomService.setPrice(price);
        em.flush();
    }

    @Override
    public void removeRoomService(String hotelName, String name) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        roomService = hotel.findRoomService(name);
        if (roomService == null) {
            throw new ExistException("ROOM SERVICE NOT EXIST.");
        }
        hotel.getRoomServices().remove(roomService);
        hotel.setRoomServices(hotel.getRoomServices());
        em.remove(roomService);
        em.flush();
    }

    @Override
    public List<RoomService> getRoomServices(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        hotel.getRoomServices().size();
        return hotel.getRoomServices();
    }
    
    @Override
    public void createRoomServiceOrder(Long accommodationBillId, List<RoomService> selectedServices)throws ExistException{
        accommodationBill=em.find(AccommodationBill.class, accommodationBillId);
        if(accommodationBill==null){
            throw new ExistException("ACCOMMODATION BILL NOT EXIST.");
        }
        serviceOrder=new RoomServiceOrder();
        serviceOrder.create();
        accommodationBill.getRoomServiceOrders().add(serviceOrder);
        serviceOrder.setRoomServices(selectedServices);
        em.persist(serviceOrder);
    }

    @Override
    public void addItemToOrder(Long serviceOrderId, String hotelName, String serviceName)  throws ExistException {
        serviceOrder=em.find(RoomServiceOrder.class, serviceOrderId);
        if(serviceOrder==null){
            throw new ExistException("ROOM SERVICE ORDER NOT EXIST.");
        }
        hotel=em.find(Hotel.class, hotelName);
        if(hotel==null){
            throw new ExistException("HOTEL NOT EXIST.");
        }
        roomService=hotel.findRoomService(serviceName);
        if(roomService==null){
            throw new ExistException("ROOM SERVICE NOT FOUND IN THIS HOTEL.");
        }
        serviceOrder.getRoomServices().add(roomService);
        em.flush();
    }

    @Override
    public void cancelRoomServiceOrder(Long serviceOrderId)  throws ExistException {
        serviceOrder=em.find(RoomServiceOrder.class, serviceOrderId);
        if(serviceOrder==null){
            throw new ExistException("ROOM SERVICE ORDER NOT EXIST.");
        }
        serviceOrder.getAccommodationBill().getRoomServiceOrders().remove(serviceOrder);
        em.flush();
    }
    
}
