/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.ComplaintRegisterBeanRemote;
import entity.ComplaintEntry;
import entity.LogEntry;
import exception.ExistException;
import java.io.Serializable;
import java.util.Calendar;
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
public class ComplaintBean implements Serializable{

    @EJB
    ComplaintRegisterBeanRemote complaintBean;
    private String hotelName = "Holiday Inn";
    private List<ComplaintEntry> entries;
    private ComplaintEntry newEntry = new ComplaintEntry();
    private ComplaintEntry selectedEntry;

    /**
     * Creates a new instance of ComplaintBean
     */
    public ComplaintBean() {
    }

    @PostConstruct
    public void init() throws ExistException {
        //System.out.println(hotelName);
        this.entries = complaintBean.getComplaintEntries(hotelName);
    }

    public String reinit() {
        this.newEntry = new ComplaintEntry();
        return null;
    }

    public void createEntry() throws ExistException {
        //newEntry.setId(UUID.randomUUID().getMostSignificantBits());
        newEntry.setDateTime(Calendar.getInstance());
        newEntry.setId(complaintBean.addComplaintEntry(hotelName, newEntry.getCustomerName(), newEntry.getRoomNumber(), newEntry.getContact(), newEntry.getDescription(), newEntry.getStatus(), newEntry.getDateTime()));
        entries.add(newEntry);
        //newEntry=new LogEntry();
    }

    public void deleteEntry() throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(selectedEntry.getId());
            if (selectedEntry == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a complaint entry", ""));
            }
            this.complaintBean.removeComplaintEntry(selectedEntry.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel:" + hotelName + " Complaint ID: " + selectedEntry.getId() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the complaint entry: " + ex.getMessage(), ""));
        }
    }

    public void onEdit(RowEditEvent event) throws ExistException {
        if (selectedEntry == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a log entry", ""));
        }
        //System.out.println(hotelName + " + " + selectedItem.getName());
        this.complaintBean.editComplaintEntry(selectedEntry.getId(), selectedEntry.getCustomerName(), selectedEntry.getRoomNumber(), selectedEntry.getContact(), selectedEntry.getDescription(), selectedEntry.getStatus());

        FacesMessage msg = new FacesMessage("Hotel: " + hotelName + " Complaint ID: " + ((ComplaintEntry) event.getObject()).getId() + " Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing complaint entry: " + ((ComplaintEntry) event.getObject()).getId() + " ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleHotelChange() throws ExistException {
        if (hotelName != null && !hotelName.equals("")) {
            entries = complaintBean.getComplaintEntries(hotelName);
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

    public List<ComplaintEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ComplaintEntry> entries) {
        this.entries = entries;
    }

    public ComplaintEntry getNewEntry() {
        return newEntry;
    }

    public void setNewEntry(ComplaintEntry newEntry) {
        this.newEntry = newEntry;
    }

    public ComplaintEntry getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(ComplaintEntry selectedEntry) {
        this.selectedEntry = selectedEntry;
    }
    
}
