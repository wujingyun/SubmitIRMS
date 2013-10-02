/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Hotel;
import entity.MiniBarItem;
import entity.Room;
import exception.ExistException;
import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface HotelRoomBeanRemote {
    public void createHotel(Hotel hotel);
    public void addHotel(String name, String address, String telNumber,  String description, Integer capacity, double overbookRate)throws ExistException;
    public void editHotel(String Name, String address, String telNumber,  String description, Integer capacity, double overbookRate)throws ExistException;
    public void removeHotel(String name) throws ExistException;
    //public void createRoom(String hotelName, Room room) throws ExistException;
    public void addRoom(String hotelName, Integer roomNumber, String type, String description, double rate)throws ExistException;
    public void editRoom(String hotelName, Integer roomNumber, String type, String description, double rate)throws ExistException;
    public void removeRoom(String hotelName, Integer roomNumber) throws ExistException;
    public void addMiniBarItem(String hotelName, String name, String description, Integer quantity, double price)throws ExistException;
    public void editMiniBarItem(String hotelName, String name, String description, Integer quantity, double price)throws ExistException;
    public void removeMiniBarItem(String hotelName, String name)throws ExistException;
    public void updateRoomAvailability(String hotelName, Integer roomNumber, String availabilityStatus)throws ExistException;
    public void updateHousekeepingStatus(String hotelName, Integer roomNumber, String housekeepingStatus)throws ExistException;
    public List<Hotel> getHotels();
    public List<Room> getRooms(String hotelName)throws ExistException;
    public List<MiniBarItem> getMiniBarItems(String hotelName)throws ExistException;

    public List<Hotel> getHotelByDescription(String description);

   
}
