package managedBean;

import ejb.ShowTheatreBeanRemote;
import entity.Venue;
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
 * @author Jiao Shen
 */

@ManagedBean
@ViewScoped
public class ShowTheatreManagedBean implements Serializable{
    
    @EJB
    private ShowTheatreBeanRemote showTheatreBean;
    
    private List<Venue> venues;
    private Venue newVenue = new Venue();
    private Venue selectedVenue;
    private List<String> venueNames;
    
    /**
     * Creates a new instance of ShowTheatreBean
     */
    public ShowTheatreManagedBean() throws ExistException {
    }
        
    @PostConstruct
    public void init() throws ExistException{
        this.venues = showTheatreBean.getVenues();
        System.out.println("Managed-Theatre-1==============================");
        venueNames = new ArrayList<String>();
        System.out.println("Managed-Theatre-2==============================");
        for(int i = 0; i<venues.size(); i++){
            venueNames.add(venues.get(i).getVenueName());
            System.out.println("Managed-Theatre-3==============================");
        }
    }
    
    public String reinit(){
        this.newVenue = new Venue();
        System.out.println("Managed-Theatre-4==============================");
        return null;
    }
    
    public void createTheatre(ActionEvent event) throws ExistException{
        try{
            showTheatreBean.add(newVenue.getVenueName(),newVenue.getVenueAddr(), newVenue.getPrevailingRates());
            System.out.println("Managed-Theatre-5==============================");
            venues.add(newVenue);
            System.out.println("Managed-Theatre-6==============================");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Venue: " + selectedVenue.getVenueName() + " " + " addedd successfully", ""));
        }
        catch (ExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR: ","Venue exists. Please use another name."));
        }
    }
    
    public void deleteTheatre(ActionEvent event) throws ExistException {
        try {
            if(selectedVenue==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a venue", ""));
            }
            this.showTheatreBean.remove(selectedVenue.getVenueName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Venue: " + selectedVenue.getVenueName() + " " + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the venue: " + ex.getMessage(), ""));
        }
    }

    public void onEdit(RowEditEvent event) throws ExistException {
        try {
            if(selectedVenue==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a venue", ""));
            }
            this.showTheatreBean.edit(selectedVenue.getVenueName(), selectedVenue.getVenueAddr(),selectedVenue.getPrevailingRates());        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Venue: " + selectedVenue.getVenueName() + " " + " edited successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while editing the venue: " + ex.getMessage(), ""));
        }
    }
    
    /*
    public void onEditStatus(RowEditEvent event) throws ExistException {
        if(selectedVenue==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a venue", ""));
            }
        this.showTheatreBean.updateStatus(selectedVenue.getVenueName(),selectedVenue.getVenueStatus());
        FacesMessage msg = new FacesMessage("Venue: " + selectedVenue.getVenueName() + " " + " Status Updated", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    */
 
    public void onCancel(RowEditEvent event) {
    FacesMessage msg = new FacesMessage("Cancelled editing venue");
    FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    public Venue getSelectedVenue() {
        return selectedVenue;
    }

    public void setSelectedVenue(Venue selectedVenue) {
        this.selectedVenue = selectedVenue;
    }

    public List<String> getVenueNames() {
        return venueNames;
    }

    public void setVenueNames(List<String> venueNames) {
        this.venueNames = venueNames;
    }

    public Venue getNewVenue() {
        return newVenue;
    }

    public void setNewVenue(Venue newVenue) {
        this.newVenue = newVenue;
    }

    public ShowTheatreBeanRemote getShowTheatreBean() {
        return showTheatreBean;
    }

    public void setShowTheatreBean(ShowTheatreBeanRemote showTheatreBean) {
        this.showTheatreBean = showTheatreBean;
    }
    
}
