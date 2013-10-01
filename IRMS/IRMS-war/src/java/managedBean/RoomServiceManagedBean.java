/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.RoomServiceBeanRemote;
import entity.Room;
import entity.RoomService;
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
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@ViewScoped
public class RoomServiceManagedBean implements Serializable{
        
    @EJB
    RoomServiceBeanRemote serviceBean;
    
    private String hotelName="Holiday Inn";
    private List<RoomService> services;
    private RoomService newService= new RoomService();
    private RoomService selectedService;
    private List<RoomService> selectedServices;
    private Long accBillId;
    private double total=0;

    /**
     * Creates a new instance of RoomServiceManagedBean
     */
    public RoomServiceManagedBean() {
    }
    
    @PostConstruct
    public void init() throws ExistException{
        this.services=serviceBean.getRoomServices(hotelName);
    }
    
    public String reinit(){
        this.newService=new RoomService();
        return null;
    }
    
    public void createService(ActionEvent event) throws ExistException{
        serviceBean.addRoomService(hotelName,newService.getName(), newService.getDescription(), newService.getPrice());
        services.add(newService);
    }
    
    public void createServiceOrder(ActionEvent event) throws ExistException{
        serviceBean.createRoomServiceOrder(accBillId, selectedServices);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Room Service Order has been created!",""));
        this.setTotal(0);
    }
    
    public void calculateTotal(){
        
        for(int i=0; i<selectedServices.size(); i++){
            total=total+selectedServices.get(i).getPrice()*selectedServices.get(i).getQuantity();
        }
    }
    
    public void deleteService(ActionEvent event) throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(hotelName);
            if(selectedService==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a room service", ""));
            }
            this.serviceBean.removeRoomService(hotelName,selectedService.getName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel " + hotelName+ " Room Service: " +selectedService.getName() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the room service: " + ex.getMessage(), ""));
        }
    }

    public void onEdit(RowEditEvent event) throws ExistException {
        if(selectedService==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a room", ""));
            }
        //System.out.println(hotelName + " + " + selectedRoom.getRoomNumber());
        this.serviceBean.editRoomService(hotelName, selectedService.getName(),selectedService.getDescription(), selectedService.getPrice());
        
        FacesMessage msg = new FacesMessage("Hotel: " + hotelName+" Room Service: "+((RoomService) event.getObject()).getName()+" Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing Room: " +((Room) event.getObject()).getRoomNumber()+" ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void handleHotelChange() throws ExistException{
        if(hotelName!=null&& !hotelName.equals("")) {
            services = serviceBean.getRoomServices(hotelName);
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

    public List<RoomService> getServices() {
        return services;
    }

    public void setServices(List<RoomService> services) {
        this.services = services;
    }

    public RoomService getNewService() {
        return newService;
    }

    public void setNewService(RoomService newService) {
        this.newService = newService;
    }

    public RoomService getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(RoomService selectedService) {
        this.selectedService = selectedService;
    }

    public List<RoomService> getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(List<RoomService> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public Long getAccBillId() {
        return accBillId;
    }

    public void setAccBillId(Long accBillId) {
        this.accBillId = accBillId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
