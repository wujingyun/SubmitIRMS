/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelRoomBeanRemote;
import entity.Hotel;
import entity.Room;
import exception.ExistException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@ViewScoped
public class HotelRoomManagedBean implements Serializable {

    @EJB
    private HotelRoomBeanRemote hotelBean;
    private Collection<Hotel> hotels;
    //@ManagedProperty(value="#{param.hotelName}")
    private String hotelName;
    private List<Room> rooms;
    private Room selectedRoom;
    private List<Room> filteredRooms;

    /**
     * Creates a new instance of HotelRoomManagedBean
     */
    public HotelRoomManagedBean() {
        selectedRoom=new Room();
    }

    @PostConstruct
    public void init() throws ExistException {
        hotels = hotelBean.getHotels();
    }

    public void listRooms() {
        try {
            this.rooms = (List) hotelBean.getRooms(hotelName);
        } catch (Exception e) {
            System.out.println("An error has occurered." + e.getMessage());
        }
    }
    
    public void onEdit(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Hotel Edited", ((Hotel) event.getObject()).getName());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    public void onCancel(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Hotel Cancelled", ((Hotel) event.getObject()).getName());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    /*
     public Collection<Room> listRooms() {
     try {
     //System.out.println(hotelName);
     return hotelBean.getRooms(hotelName);
     } catch (Exception e) {
     System.out.println("An error has occurered." + e.getMessage());
     return null;
     }
     }
     */

    public Collection<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(Collection<Hotel> hotels) {
        this.hotels = hotels;
    }

    public String getHotelName() {
        return hotelName;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getFilteredRooms() {
        return filteredRooms;
    }

    public void setFilteredRooms(List<Room> filteredRooms) {
        this.filteredRooms = filteredRooms;
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }
}