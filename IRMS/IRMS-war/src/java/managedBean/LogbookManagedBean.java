/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.LogbookBeanRemote;
import entity.LogEntry;
import exception.ExistException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
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
public class LogbookManagedBean implements Serializable{


        @EJB
        LogbookBeanRemote logbookBean;
        private String hotelName = "Holiday Inn";
        private List<LogEntry> entries;
        private LogEntry newEntry = new LogEntry();
        private LogEntry selectedEntry;

        /**
         * Creates a new instance of LogbookManagedBean
         */
        public LogbookManagedBean() throws ExistException {
        }
        
        @PostConstruct
        public void init() throws ExistException {
            //System.out.println(hotelName);
            this.entries = logbookBean.getLogEntries(hotelName);
        }
        
        public String reinit() {
            this.newEntry = new LogEntry();
            return null;
        }

        public void createEntry() throws ExistException {
            //newEntry.setId(UUID.randomUUID().getMostSignificantBits());
            newEntry.setDateTime(Calendar.getInstance());
            newEntry.setId(logbookBean.addLogEntry(hotelName,newEntry.getDescription(),newEntry.getContactPerson(),newEntry.getContactNumber(),newEntry.getCategory(),newEntry.getStatus(),newEntry.getDateTime()));
            entries.add(newEntry);
            //newEntry=new LogEntry();
        }

        public void deleteEntry() throws ExistException {
            try {
                //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
                //System.out.println(selectedEntry.getId());
                if (selectedEntry == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a log entry", ""));
                }
                this.logbookBean.removeLogEntry(selectedEntry.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel " + hotelName + " " + selectedEntry.getId() + " deleted successfully", ""));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the log entry: " + ex.getMessage(), ""));
            }
        }

        public void onEdit(RowEditEvent event) throws ExistException {
            if (selectedEntry == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a log entry", ""));
            }
            //System.out.println(hotelName + " + " + selectedItem.getName());
            this.logbookBean.editLogEntry(hotelName, selectedEntry.getId(), selectedEntry.getDescription(), selectedEntry.getContactPerson(), selectedEntry.getContactNumber(), selectedEntry.getCategory(), selectedEntry.getStatus());

            FacesMessage msg = new FacesMessage("Hotel: " + hotelName + " " + ((LogEntry) event.getObject()).getId() + " Edited", "");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        public void onCancel(RowEditEvent event) {
            FacesMessage msg = new FacesMessage("Cancelled editing log entry: " + ((LogEntry) event.getObject()).getId() + " ", "");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        public void handleHotelChange() throws ExistException {
            if (hotelName != null && !hotelName.equals("")) {
                entries = logbookBean.getLogEntries(hotelName);
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

    public List<LogEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LogEntry> entries) {
        this.entries = entries;
    }

    public LogEntry getNewEntry() {
        return newEntry;
    }

    public void setNewEntry(LogEntry newEntry) {
        this.newEntry = newEntry;
    }

    public LogEntry getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(LogEntry selectedEntry) {
        this.selectedEntry = selectedEntry;
    }
        
}
