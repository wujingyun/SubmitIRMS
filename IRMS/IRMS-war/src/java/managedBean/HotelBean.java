/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelRoomBeanRemote;
import entity.Hotel;
import exception.ExistException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@ViewScoped
public class HotelBean implements Serializable {

    @EJB
    private HotelRoomBeanRemote hotelBean;
    private List<Hotel> hotels;
    private Hotel newHotel = new Hotel();
    private Hotel selectedHotel;
    /**
     * Creates a new instance of HotelBean
     */
    public HotelBean() {
    }

    @PostConstruct
    private void init() {
        this.hotels = hotelBean.getHotels();
    }

    public String reinit() {
        this.newHotel = new Hotel();
        return null;
    }

    public void createHotel(ActionEvent event) {
        hotelBean.createHotel(newHotel);
        hotels.add(newHotel);
        // newHotel = new Hotel();
    }

    public void deleteHotel() throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(hotelName);
            if(selectedHotel==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a hotel", ""));
            }
            this.hotelBean.removeHotel(selectedHotel.getName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel " + selectedHotel.getName() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the hotel: " + ex.getMessage(), ""));
        }
    }

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Hotel Edited", ((Hotel) event.getObject()).getName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Hotel Cancelled", ((Hotel) event.getObject()).getName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public Hotel getNewHotel() {
        return newHotel;
    }

    public void setNewHotel(Hotel newHotel) {
        this.newHotel = newHotel;
    }

    public Hotel getSelectedHotel() {
        return selectedHotel;
    }

    public void setSelectedHotel(Hotel selectedHotel) {
        this.selectedHotel = selectedHotel;
    }
    
}
