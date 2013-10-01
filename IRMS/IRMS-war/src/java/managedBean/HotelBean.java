/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelRoomBeanRemote;
import entity.Hotel;
import exception.ExistException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class HotelBean implements Serializable {

    @EJB
    private HotelRoomBeanRemote hotelBean;
    private List<Hotel> hotels;
    private Hotel newHotel = new Hotel();
    private Hotel selectedHotel;
    private List<String> hotelNames;
    //private Hotel editedHotel;
    /**
     * Creates a new instance of HotelBean
     */
    public HotelBean() {
    }

    @PostConstruct
    private void init() {
        this.hotels = hotelBean.getHotels();
        hotelNames=new ArrayList<String>();
        for(int i=0; i<hotels.size(); i++){
            hotelNames.add(hotels.get(i).getName());
        }
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
    /*
    public void checkSelect() {
        if(selectedHotel==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a hotel", ""));
            }
        else
            System.out.println(selectedHotel.getName());
    }
    
    public void editHotel() throws ExistException {
        try {
            if(selectedHotel==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a hotel", ""));
            }
            editedHotel.setName(selectedHotel.getName());
            this.hotelBean.editHotel(editedHotel.getName(), editedHotel.getAddress(),editedHotel.getTelNumber(),editedHotel.getDescription(),editedHotel.getCapacity(), editedHotel.getOverbookRate());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel "+editedHotel.getName()+ " edited successfully", ""));
        }catch (Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while editing the hotel: " + ex.getMessage(), ""));
        }
    }
    */ 
    public void deleteHotel(ActionEvent event) throws ExistException {
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

    public void onEdit(RowEditEvent event) throws ExistException {
        if(selectedHotel==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a hotel", ""));
            }
        this.hotelBean.editHotel(selectedHotel.getName(),selectedHotel.getAddress(), selectedHotel.getTelNumber(),selectedHotel.getDescription(), selectedHotel.getCapacity(), selectedHotel.getOverbookRate());
        
        FacesMessage msg = new FacesMessage("Hotel: " +((Hotel) event.getObject()).getName()+" Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing Hotel: " +((Hotel) event.getObject()).getName()+" ", "");

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
    
    /*
    public Hotel getEditedHotel() {
        return editedHotel;
    }

    public void setEditedHotel(Hotel editedHotel) {
        this.editedHotel = editedHotel;
    }
    */ 

    public List<String> getHotelNames() {
        return hotelNames;
    }

    public void setHotelNames(List<String> hotelNames) {
        this.hotelNames = hotelNames;
    }
}
