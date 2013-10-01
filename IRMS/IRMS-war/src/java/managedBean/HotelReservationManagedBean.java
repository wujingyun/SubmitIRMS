/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelReservationBeanRemote;
import entity.InternalRoomRequest;
import entity.RoomReservation;
import exception.ExistException;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;

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
    private List<RoomReservation> reservations;
    private RoomReservation newReservation = new RoomReservation();
    private RoomReservation selectedReservation;
    private List<InternalRoomRequest> requests;

    /**
     * Creates a new instance of HotelReservationManagedBean
     */
    public HotelReservationManagedBean() {
    }

    @PostConstruct
    public void init() throws ExistException {
        //System.out.println(hotelName);
        this.reservations = reservationBean.getHotelReservations(hotelName);
        this.requests=reservationBean.getInternalRoomRequests(hotelName);
        System.out.println(requests.size());
        //System.out.println(requests.get(0).getId());
        //System.out.println(requests.get(0).getHeadcount());
    }

    public String reinit() {
        this.newReservation = new RoomReservation();
        return null;
    }

    public void createReservation(ActionEvent event) throws ExistException {
        //System.out.println(hotelName + newReservation.getCustomerId()+ newReservation.getRoomType());
        newReservation.setDateReserved(Calendar.getInstance());
        newReservation = reservationBean.makeReservation(newReservation.getCustomerId(), hotelName, newReservation.getRoomType(), newReservation.getQuantity(), newReservation.getStartDate(), newReservation.getEndDate(), newReservation.getRemark(), newReservation.getDateReserved());
        System.out.println(hotelName + newReservation.getCustomerId() + newReservation.getRoomType());
        reservations.add(newReservation);
    }

    public void onEdit(RowEditEvent event) throws ExistException {
        if (selectedReservation == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a log entry", ""));
        }
        //System.out.println(hotelName + " + " + selectedItem.getName());
        this.reservationBean.editReservation(selectedReservation.getId(), selectedReservation.getHotelName(), selectedReservation.getRoomType(), selectedReservation.getQuantity(), selectedReservation.getStartDate(), selectedReservation.getEndDate(), selectedReservation.getRemark(), selectedReservation.getStatus(), selectedReservation.getTotal(), selectedReservation.getPaymentStatus());

        FacesMessage msg = new FacesMessage("Hotel: " + hotelName + " Reservation " + ((RoomReservation) event.getObject()).getId() + " Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing reservation: " + ((RoomReservation) event.getObject()).getId() + " ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleHotelChange() throws ExistException {
        if (hotelName != null && !hotelName.equals("")) {
            reservations = reservationBean.getHotelReservations(hotelName);
            requests=reservationBean.getInternalRoomRequests(hotelName);
        } else {
            System.out.println("empty hotel name");
        }
    }

    public void cancelReservation() throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(selectedEntry.getId());
            if (selectedReservation == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a reservation", ""));
            }
            this.reservationBean.cancelReservation(selectedReservation.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel " + hotelName + " Reservation " + selectedReservation.getId() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the reservation: " + ex.getMessage(), ""));
        }
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<RoomReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<RoomReservation> reservations) {
        this.reservations = reservations;
    }

    public RoomReservation getNewReservation() {
        return newReservation;
    }

    public void setNewReservation(RoomReservation newReservation) {
        this.newReservation = newReservation;
    }

    public RoomReservation getSelectedReservation() {
        return selectedReservation;
    }

    public void setSelectedReservation(RoomReservation selectedReservation) {
        this.selectedReservation = selectedReservation;
    }

    public List<InternalRoomRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<InternalRoomRequest> requests) {
        this.requests = requests;
    }
    
}
