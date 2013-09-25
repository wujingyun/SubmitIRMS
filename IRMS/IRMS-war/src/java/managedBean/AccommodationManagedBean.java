/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelRoomBeanRemote;
import entity.Hotel;
import entity.Room;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@RequestScoped
public class AccommodationManagedBean implements Serializable {

    @EJB
    private HotelRoomBeanRemote hotelBean;
    private Collection<Hotel> hotels;
    @ManagedProperty(value="#{param.hotelName}")
    private String hotelName;

    public AccommodationManagedBean(){
    }

    @PostConstruct
    public void init() {
        hotels = (ArrayList) hotelBean.getHotels();
    }
    
    public Collection<Room> listRooms() {
        try {
             return hotelBean.getRooms(hotelName);
        } catch (Exception e) {
            System.out.println("An error has occurered." + e.getMessage());
            return null;
        }
    }
     
    public void listMiniBarItems(String hotelName) {
        try {
            hotelBean.getMiniBarItems(hotelName);
            System.out.println(hotelName);
        } catch (Exception e) {
            System.out.println("An error has occurered." + e.getMessage());
        }
    }

    public Collection<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(Collection<Hotel> hotels) {
        this.hotels = hotels;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
    /*
    public Collection<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Collection<Room> rooms) {
        this.rooms = rooms;
    }
    */ 
}
