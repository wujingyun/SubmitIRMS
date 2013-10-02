
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.ComplaintRegister;
import entity.Hotel;
import entity.Logbook;
import entity.MiniBarItem;
import entity.Room;
import exception.ExistException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    Logbook logbook;
    ComplaintRegister complaintRegister;

    public HotelRoomBean() {
    }
    
    @Override
    public void createHotel(Hotel hotel){
        logbook=new Logbook();
        complaintRegister=new ComplaintRegister();
        logbook.setHotel(hotel);
        logbook.setDescription(hotel.getName()+" Logbook");
        logbook.setDateCreated(Calendar.getInstance());
        em.persist(logbook);
        complaintRegister.setHotel(hotel);
        complaintRegister.setDateCreated(Calendar.getInstance());
        em.persist(complaintRegister);
        hotel.setLogbook(logbook);
        hotel.setComplaintRegister(complaintRegister);
        em.persist(hotel);
    }

    
     @Override  
   public  List<Hotel> getHotelByDescription(String description) {
        Query q1 = em.createQuery("SELECT ua FROM Hotel ua where ua.description LIKE:custName OR ua.name LIKE:custName2");
         q1.setParameter("custName", description);
          q1.setParameter("custName2", description);
           List <Hotel> hotelList = new ArrayList <Hotel> ();
           
          for (Object o : q1.getResultList()) {
             hotel = (Hotel) o;
             hotelList.add(hotel);
             System.out.println( "hotel==========================================================");
        }
        
        return hotelList;
    }
    
     
     
    @Override
    public void addHotel(String name, String address, String telNumber, String description, Integer capacity, double overbookRate) throws ExistException {
        hotel = em.find(Hotel.class, name);
        if (hotel != null) {
            throw new ExistException("HOTEL ALREADY EXISTS.");
        }
        hotel = new Hotel();
        hotel.create(name, address, telNumber, description, capacity, overbookRate);
        em.persist(hotel);
    }

    @Override
    public void editHotel(String name, String address, String telNumber, String description, Integer capacity, double overbookRate) throws ExistException {
        hotel = em.find(Hotel.class, name);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        hotel.setAddress(address);
        hotel.setTelNumber(telNumber);
        hotel.setDescription(description);
        hotel.setCapacity(capacity);
        hotel.setOverbookRate(overbookRate);
        em.flush();
    }
    
     @Override
    public void editHotelweb(String displayName, String name, String address, String telNumber, String description, Integer capacity, double overbookRate) throws ExistException {
        hotel = em.find(Hotel.class, name);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
         hotel.setDisplayName(displayName);
        hotel.setAddress(address);
        hotel.setTelNumber(telNumber);
        hotel.setDescription(description);
        hotel.setCapacity(capacity);
        hotel.setOverbookRate(overbookRate);
        em.flush();
    }

    @Override
    public void removeHotel(String name) throws ExistException {
        hotel = em.find(Hotel.class, name);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        em.remove(hotel.getLogbook());
        em.remove(hotel.getComplaintRegister());
        em.remove(hotel);
        em.flush();
    }
    /*
    @Override
    public void createRoom(String hotelName, Room newRoom) throws ExistException{
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        newRoom = hotel.findRoom(newRoom.getRoomNumber());
        if (newRoom != null) {
            throw new ExistException("ROOM ALREADY EXISTS.");
        }
        newRoom.setHotel(hotel);
        hotel.getRooms().add(newRoom);
        hotel.setRooms(hotel.getRooms());
        em.persist(newRoom);
    }
    */ 
    @Override
    public void addRoom(String hotelName, Integer roomNumber, String type, String description, double rate) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        room = hotel.findRoom(roomNumber);
        if (room != null) {
            throw new ExistException("ROOM ALREADY EXISTS.");
        }
        room = new Room();
        room.create(roomNumber, type, description, rate);
        room.setAvailabilityStatus("Available");
        room.setHousekeepingStatus("Clean");
        room.setHotel(hotel);
        hotel.getRooms().add(room);
        hotel.setRooms(hotel.getRooms());
        em.persist(room);
    }

    @Override
    public void editRoom(String hotelName, Integer roomNumber, String type, String description, double rate) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        room = hotel.findRoom(roomNumber);
        if (room == null) {
            throw new ExistException("ROOM NOT EXIST.");
        }
        room.setRoomNumber(roomNumber);
        room.setType(type);
        room.setDescription(description);
        room.setRate(rate);
        em.flush();
    }

    @Override
    public void removeRoom(String hotelName, Integer roomNumber) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        room = hotel.findRoom(roomNumber);
        if (room == null) {
            throw new ExistException("ROOM NOT EXIST.");
        }
        hotel.getRooms().remove(room);
        hotel.setRooms(hotel.getRooms());
        em.remove(room);
        em.flush();
    }

    @Override
    public void addMiniBarItem(String hotelName, String name, String description, Integer quantity, double price) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        miniBarItem = hotel.findMiniBarItem(name);
        if (miniBarItem != null) {
            throw new ExistException("MINIBARITEM ALREADY EXISTS.");
        }
        miniBarItem = new MiniBarItem();
        miniBarItem.setName(name);
        miniBarItem.setDescription(description);
        miniBarItem.setQuantity(quantity);
        miniBarItem.setPrice(price);
        miniBarItem.setHotel(hotel);
        hotel.getMiniBarItems().add(miniBarItem);
        hotel.setMiniBarItems(hotel.getMiniBarItems());
        em.persist(miniBarItem);
    }

    @Override
    public void editMiniBarItem(String hotelName, String name, String description, Integer quantity, double price) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        miniBarItem = hotel.findMiniBarItem(name);
        if (miniBarItem == null) {
            throw new ExistException("MINIBARITEM NOT EXIST.");
        }
        miniBarItem.setName(name);
        miniBarItem.setDescription(description);
        miniBarItem.setQuantity(quantity);
        miniBarItem.setPrice(price);
        em.flush();
    }

    @Override
    public void removeMiniBarItem(String hotelName, String name) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        miniBarItem = hotel.findMiniBarItem(name);
        if (miniBarItem == null) {
            throw new ExistException("MINIBARITEM NOT EXIST.");
        }
        hotel.getMiniBarItems().remove(miniBarItem);
        hotel.setMiniBarItems(hotel.getMiniBarItems());
        em.remove(miniBarItem);
        em.flush();
    }

    @Override
    public void updateRoomAvailability(String hotelName, Integer roomNumber, String availabilityStatus) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        room = hotel.findRoom(roomNumber);
        if (room == null) {
            throw new ExistException("ROOM NOT EXIST.");
        }
        room.setAvailabilityStatus(availabilityStatus);
        em.flush();
    }

    @Override
    public void updateHousekeepingStatus(String hotelName, Integer roomNumber, String housekeepingStatus) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        room = hotel.findRoom(roomNumber);
        if (room == null) {
            throw new ExistException("ROOM NOT EXIST.");
        }
        room.setHousekeepingStatus(housekeepingStatus);
        em.flush();
    }

    @Override
    public List<Hotel> getHotels() {
        Query q = em.createQuery("SELECT h FROM Hotel h");
        /*
        Collection<Hotel> hotels = new ArrayList();
        for (Object o : q.getResultList()) {
            Hotel h = (Hotel) o;
            hotels.add(h);
        }
        
        return hotels;
        */
        return q.getResultList();
    }

    @Override
    public List<Room> getRooms(String hotelName) throws ExistException {
        //System.out.println(hotelName);
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        /*
        System.out.println("test");
        if(hotel.getRooms()==null)
            System.out.println("null arraylist");
            */ 
        hotel.getRooms().size();
        return (List)hotel.getRooms();
    }

    @Override
    public List<MiniBarItem> getMiniBarItems(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        hotel.getMiniBarItems().size();
        return (List)hotel.getMiniBarItems();
    }

    @Override
    public List<String> getMiniBarItemsNames(String hotelName) throws ExistException {
        List<String> names=new ArrayList<String>();
        Integer size=this.getMiniBarItems(hotelName).size();
        for(int i=0; i<size; i++){
            String st=this.getMiniBarItems(hotelName).get(i).getName();
            names.add(st);
        }
        return names;
    }

    @Override
    public double findMiniBarItemPrice(String hotelName, String name) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        return hotel.findMiniBarItem(name).getPrice();
    }
    
}
