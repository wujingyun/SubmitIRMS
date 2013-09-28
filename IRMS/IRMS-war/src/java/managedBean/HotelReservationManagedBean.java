/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelReservationBeanRemote;
import entity.RoomReservation;
import exception.ExistException;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@ViewScoped
public class HotelReservationManagedBean {

    @EJB
    HotelReservationBeanRemote reservationBean;
    private String hotelName = "Holiday Inn";
    private List<RoomReservation> entries;
    private RoomReservation newEntry = new RoomReservation();
    private RoomReservation selectedEntry;

    /**
     * Creates a new instance of HotelReservationManagedBean
     */
    public HotelReservationManagedBean() {
    }

    @PostConstruct
    public void init() throws ExistException {
        //System.out.println(hotelName);
        this.entries = reservationBean.getHotelReservations(hotelName);
    }

    public String reinit() {
        this.newEntry = new RoomReservation();
        return null;
    }

    public void createEntry(ActionEvent event) throws ExistException {
        //newEntry.setId(UUID.randomUUID().getMostSignificantBits());
        System.out.println(hotelName + newEntry.getCustomerId()+ newEntry.getRoomType());
        newEntry.setDateReserved(Calendar.getInstance());
        reservationBean.makeReservation(newEntry.getCustomerId(),hotelName, newEntry.getRoomType(), newEntry.getQuantity(), newEntry.getStartDate(), newEntry.getEndDate(), newEntry.getRemark());
        entries.add(newEntry);
        //newEntry=new LogEntry();
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<RoomReservation> getEntries() {
        return entries;
    }

    public void setEntries(List<RoomReservation> entries) {
        this.entries = entries;
    }

    public RoomReservation getNewEntry() {
        return newEntry;
    }

    public void setNewEntry(RoomReservation newEntry) {
        this.newEntry = newEntry;
    }

    public RoomReservation getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(RoomReservation selectedEntry) {
        this.selectedEntry = selectedEntry;
    }
    
}
