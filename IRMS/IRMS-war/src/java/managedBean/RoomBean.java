/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelRoomBeanRemote;
import entity.Room;
import exception.ExistException;
import java.io.Serializable;
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
public class RoomBean implements Serializable{
    
    @EJB
    HotelRoomBeanRemote hotelRoomBean;
    
    private String hotelName="Holiday Inn";
    private List<Room> rooms;
    private Room newRoom= new Room();
    private Room selectedRoom;
    /**
     * Creates a new instance of RoomBean
     */
    public RoomBean() throws ExistException {
    }
    
    
    @PostConstruct
    public void init() throws ExistException{
        this.rooms=hotelRoomBean.getRooms(hotelName);
    }
    
    public String reinit(){
        this.newRoom=new Room();
        return null;
    }
    
    public void createRoom() throws ExistException{
        hotelRoomBean.addRoom(hotelName,newRoom.getRoomNumber(), newRoom.getType(), newRoom.getDescription(), newRoom.getRate());
        rooms.add(newRoom);
    }
    
    public void deleteRoom() throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(hotelName);
            if(selectedRoom==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a room", ""));
            }
            this.hotelRoomBean.removeRoom(hotelName,selectedRoom.getRoomNumber());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel " + hotelName+ " " +selectedRoom.getRoomNumber() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the room: " + ex.getMessage(), ""));
        }
    }

    public void onEdit(RowEditEvent event) throws ExistException {
        if(selectedRoom==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a room", ""));
            }
        //System.out.println(hotelName + " + " + selectedRoom.getRoomNumber());
        this.hotelRoomBean.editRoom(hotelName, selectedRoom.getRoomNumber(),selectedRoom.getType(), selectedRoom.getDescription(), selectedRoom.getRate());
        
        FacesMessage msg = new FacesMessage("Hotel: " + hotelName+" "+((Room) event.getObject()).getRoomNumber()+" Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing Room: " +((Room) event.getObject()).getRoomNumber()+" ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void handleHotelChange() throws ExistException{
        if(hotelName!=null&& !hotelName.equals("")) {
            rooms = hotelRoomBean.getRooms(hotelName);
        } else {
            System.out.println("empty hotel name");
        }
    }

    public String getHotelName() {
        //System.out.println(hotelName);
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
        //System.out.println(hotelName);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Room getNewRoom() {
        return newRoom;
    }

    public void setNewRoom(Room newRoom) {
        this.newRoom = newRoom;
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

}
