/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelRoomBeanRemote;
import entity.MiniBarItem;
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

public class MiniBarItemBean implements Serializable {

    @EJB
    HotelRoomBeanRemote hotelRoomBean;
    private String hotelName = "Holiday Inn";
    private List<MiniBarItem> items;
    private MiniBarItem newItem = new MiniBarItem();
    private MiniBarItem selectedItem;

    /**
     * Creates a new instance of MiniBarItemBean
     */
    public MiniBarItemBean() {
    }

    @PostConstruct
    public void init() throws ExistException {
        this.items = hotelRoomBean.getMiniBarItems(hotelName);
    }

    public String reinit() {
        this.newItem = new MiniBarItem();
        return null;
    }

    public void createItem() throws ExistException {
        hotelRoomBean.addMiniBarItem(hotelName, newItem.getName(), newItem.getDescription(), newItem.getQuantity(), newItem.getPrice());
        items.add(newItem);
    }

    public void deleteItem() throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(hotelName);
            if (selectedItem == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select an item", ""));
            }
            this.hotelRoomBean.removeMiniBarItem(hotelName, selectedItem.getName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel " + hotelName + " " + selectedItem.getName() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the room: " + ex.getMessage(), ""));
        }
    }

    public void onEdit(RowEditEvent event) throws ExistException {
        if (selectedItem == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select an item", ""));
        }
        //System.out.println(hotelName + " + " + selectedItem.getName());
        this.hotelRoomBean.editMiniBarItem(hotelName, selectedItem.getName(), selectedItem.getDescription(), selectedItem.getQuantity(), selectedItem.getPrice());

        FacesMessage msg = new FacesMessage("Hotel: " + hotelName + " " + ((MiniBarItem) event.getObject()).getName() + " Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing item: " + ((MiniBarItem) event.getObject()).getName() + " ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleHotelChange() throws ExistException {
        if (hotelName != null && !hotelName.equals("")) {
            items = hotelRoomBean.getMiniBarItems(hotelName);
        } else {
            System.out.println("empty hotel name");
        }
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<MiniBarItem> getItems() {
        return items;
    }

    public void setItems(List<MiniBarItem> items) {
        this.items = items;
    }

    public MiniBarItem getNewItem() {
        return newItem;
    }

    public void setNewItem(MiniBarItem newItem) {
        this.newItem = newItem;
    }

    public MiniBarItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MiniBarItem selectedItem) {
        this.selectedItem = selectedItem;
    }
    
    
}
