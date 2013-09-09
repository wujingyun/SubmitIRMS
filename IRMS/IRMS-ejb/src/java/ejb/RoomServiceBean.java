/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Hotel;
import entity.RoomService;
import exception.ExistException;
import java.util.Collection;
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

    @Override
    public void addRoomService(String hotelName, String name, String description, double price) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       roomService=hotel.findRoomService(name);
       if(roomService!=null)
           throw new ExistException("ROOM SERIVCE ALREADY EXISTS.");
       roomService=new RoomService();
       roomService.create(name, description, price);
       roomService.setHotel(hotel);
       hotel.getRoomServices().add(roomService);
       hotel.setRoomServices(hotel.getRoomServices());
       em.persist(roomService);
    }

    @Override
    public void editRoomService(String hotelName, String name, String description, double price) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       roomService=hotel.findRoomService(name);
       if(roomService==null)
           throw new ExistException("ROOM SERVICE NOT EXIST.");
       roomService.setName(name);
       roomService.setDescription(description);
       roomService.setPrice(price);
       em.persist(roomService);
    }

    @Override
    public void removeRoomService(String hotelName, String name) throws ExistException {
       hotel=em.find(Hotel.class, hotelName);
       if(hotel==null)
           throw new ExistException("HOTEL NOT EXIST.");
       roomService=hotel.findRoomService(name);
       if(roomService==null)
           throw new ExistException("ROOM SERVICE NOT EXIST.");
       hotel.getRoomServices().remove(roomService);
       hotel.setRoomServices(hotel.getRoomServices());
       em.remove(roomService);
    }

    @Override
    public Collection<RoomService> getRoomServices(String hotelName) throws ExistException {
        hotel=em.find(Hotel.class, hotelName);
        if(hotel==null)
            throw new ExistException("HOTEL NOT EXIST.");
        return hotel.getRoomServices();
    }

}
