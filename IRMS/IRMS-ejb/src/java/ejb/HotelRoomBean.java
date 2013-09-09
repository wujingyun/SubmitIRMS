/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Hotel;
import entity.MiniBarItem;
import entity.Room;
import exception.ExistException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Yang Zhennan
 */
@Stateless
public class HotelRoomBean implements HotelRoomBeanRemote {
     @PersistenceContext()
     EntityManager em;
     Hotel hotel;
     Room room;
     MiniBarItem miniBarItem;
        
    @Override
    public void addHotel(String name, String address, String telNumber) throws ExistException {
       hotel=em.find(Hotel.class, name);
       if(hotel!=null)
           throw new ExistException("HOTEL ALREADY EXISTS.");
       hotel=new Hotel();
       hotel.create(name, address, telNumber);
       em.persist(hotel);
    }

    @Override
    public void editHotel(String name, String address, String telNumber, String description) throws ExistException {
        hotel=em.find(Hotel.class, name);
        if(hotel==null)
            throw new ExistException("HOTEL NOT EXIST.");
        hotel.setAddress(address);
        hotel.setTelNumber(telNumber);
        hotel.setDescription(description);
        em.persist(hotel);
    }

    @Override
    public void removeHotel(String name) throws ExistException {
        hotel=em.find(Hotel.class, name);
        if(hotel==null)
            throw new ExistException("HOTEL NOT EXIST.");
        em.remove(hotel);
    }

    @Override
    public void addRoom(String hotelName, Integer floorNumber, Integer roomNumber, String type, String description, double rate) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       room=hotel.findRoom(floorNumber, roomNumber);
       if(room!=null)
           throw new ExistException("ROOM ALREADY EXISTS.");
       room=new Room();
       room.create(floorNumber, roomNumber, type, description, rate);
       room.setHotel(hotel);
       hotel.getRooms().add(room);
       em.persist(room);
    }

    @Override
    public void editRoom(String hotelName, Integer floorNumber, Integer roomNumber, String type, String description, double rate) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       room=hotel.findRoom(floorNumber, roomNumber);
       if(room==null)
           throw new ExistException("ROOM NOT EXIST.");
       room.setFloorNumber(floorNumber);
       room.setRoomNumber(roomNumber);
       room.setType(type);
       room.setDescription(description);
       room.setRate(rate);
       em.persist(room);
    }

    @Override
    public void removeRoom(String hotelName, Integer floorNumber, Integer roomNumber) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       room=hotel.findRoom(floorNumber, roomNumber);
       if(room==null)
           throw new ExistException("ROOM NOT EXIST.");
       hotel.getRooms().remove(room);
       em.remove(room);
    }

    @Override
    public void addMiniBarItem(String hotelName, String name, String description, Integer quantity, double price) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       miniBarItem=hotel.findMiniBarItem(name);
       if(miniBarItem!=null)
           throw new ExistException("MINIBARITEM ALREADY EXISTS.");
       miniBarItem=new MiniBarItem();
       miniBarItem.setName(name);
       miniBarItem.setDescription(description);
       miniBarItem.setQuantity(quantity);
       miniBarItem.setPrice(price);
       miniBarItem.setHotel(hotel);
       hotel.getMiniBarItems().add(miniBarItem);
       em.persist(miniBarItem);
    }

    @Override
    public void editMiniBarItem(String hotelName, String name, String description, Integer quantity, double price) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       miniBarItem=hotel.findMiniBarItem(name);
       if(miniBarItem==null)
           throw new ExistException("MINIBARITEM NOT EXIST.");
       miniBarItem.setName(name);
       miniBarItem.setDescription(description);
       miniBarItem.setQuantity(quantity);
       miniBarItem.setPrice(price);
       em.persist(miniBarItem);
    }

    @Override
    public void removeMiniBarItem(String hotelName, String name) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       miniBarItem=hotel.findMiniBarItem(name);
       if(miniBarItem==null)
           throw new ExistException("MINIBARITEM NOT EXIST.");
       hotel.getMiniBarItems().remove(miniBarItem);
       em.remove(miniBarItem);
    }

    @Override
    public void updateRoomAvailability(String hotelName, Integer floorNumber, Integer roomNumber, String availabilityStatus) throws ExistException{
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       room=hotel.findRoom(floorNumber, roomNumber);
       if(room==null)
           throw new ExistException("ROOM NOT EXIST.");
       room.setAvailabilityStatus(availabilityStatus);
       em.persist(room);
    }

    @Override
    public void updateHousekeepingStatus(String hotelName, Integer floorNumber, Integer roomNumber, String housekeepingStatus) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       room=hotel.findRoom(floorNumber, roomNumber);
       if(room==null)
           throw new ExistException("ROOM NOT EXIST.");
       room.setHousekeepingStatus(housekeepingStatus);
       em.persist(room);
    }

    @Override
    public Collection<Hotel> getHotels() {
        Query q=em.createNamedQuery("SELECT h FROM Hotel h");
        Collection hotels=new ArrayList();
        for(Object o: q.getResultList()){
            Hotel h=(Hotel) o;
            hotels.add(h);
        }
        return hotels;
    }

    @Override
    public Collection<Room> getRooms(String hotelName) throws ExistException {
        hotel=em.find(Hotel.class, hotelName);
        if(hotel==null)
            throw new ExistException("HOTEL NOT EXIST.");
        return hotel.getRooms();
    }

    @Override
    public Collection<MiniBarItem> getMiniBarItems(String hotelName) throws ExistException {
        hotel=em.find(Hotel.class, hotelName);
        if(hotel==null)
            throw new ExistException("HOTEL NOT EXIST.");
        return hotel.getMiniBarItems();
    }

}