/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelRoomBeanRemote;
import entity.Hotel;
import exception.ExistException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@ViewScoped
public class TestBean {
    
    @EJB
    private HotelRoomBeanRemote hotelBean;
    private List<Hotel> hotels;
    private Hotel selectedHotel;

    /**
     * Creates a new instance of TestBean
     */
    public TestBean() {
        selectedHotel=new Hotel();
    }
    
    @PostConstruct
    public void init() throws ExistException {
        hotels = new ArrayList<Hotel>();
        hotels=(ArrayList)hotelBean.getHotels();
    }
    
    public List<Hotel> getHotels() {
        return hotels;
    }
    
    public void deleteHotel(){
        hotels.remove(selectedHotel);
    }

    public Hotel getSelectedHotel() {
        return selectedHotel;
    }

    public void setSelectedHotel(Hotel selectedHotel) {
        this.selectedHotel = selectedHotel;
    }
    
    public void onEdit(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Hotel Edited", ((Hotel) event.getObject()).getName());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    public void onCancel(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Hotel Cancelled", ((Hotel) event.getObject()).getName());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
}
