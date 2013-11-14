package managedBean;

import ejb.ShowTicketBeanRemote;
import entity.EntShow;
import entity.TicketCat;
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
public class ShowTicketManagedBean implements Serializable{
    
    @EJB
    private ShowTicketBeanRemote showTicketBean;
    
    private List<TicketCat> ticketCats;
    private TicketCat newTicketCat = new TicketCat();
    private TicketCat selectedTicketCat;
    private List<String> catNames;
    private EntShow entshow = new EntShow();
    private Long selectedShowId;

    
    /**
     * Creates a new instance of ShowTicketBean
     */
    public ShowTicketManagedBean() throws ExistException {
    }
        
    @PostConstruct
    public void init() throws ExistException{
        this.ticketCats = showTicketBean.getTicketCats();
        //System.out.println("Managed-Ticket-1==============================");
        catNames = new ArrayList<String>();
        //System.out.println("Managed-Ticket-2==============================");
        for(int i = 0; i<catNames.size(); i++){
            catNames.add(ticketCats.get(i).getCatName());
            //System.out.println("Managed-Ticket-3==============================");
        }
    }
    
    public String reinit(){
        this.newTicketCat = new TicketCat();
        //System.out.println("Managed-Ticket-4==============================");
        return null;
    }
    
    public void createCategory(ActionEvent event) throws ExistException{
            System.out.println("selectedShowId is " + selectedShowId);
            TicketCat newCat = showTicketBean.addCategory(selectedShowId, newTicketCat.getCatName(), newTicketCat.getCatPrice(), newTicketCat.getTotalNum(), newTicketCat.getAvailNum());
            //System.out.println("Managed-Ticket-5==============================");
            ticketCats.add(newCat);
            //System.out.println("Managed-Ticket-6==============================");
            FacesMessage msg = new FacesMessage("Show ID: " + selectedShowId + "--> Category: " + selectedTicketCat.getCatName() + " " + " addedd successfully", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void deleteCategory(ActionEvent event) throws ExistException {
        try {
            if(selectedTicketCat==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a category", ""));
            }
            this.showTicketBean.deleteCategory(selectedTicketCat.getCatId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Show ID: " + selectedShowId + "--> Category: " + selectedTicketCat.getCatName() + " " + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the category: " + ex.getMessage(), ""));
        }
    }
    
    public void onEdit(RowEditEvent event) throws ExistException {
        try{
            if(selectedTicketCat==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a category", ""));
            }
            this.showTicketBean.editCategory(selectedTicketCat.getCatId(), selectedTicketCat.getCatName(), selectedTicketCat.getCatPrice(), selectedTicketCat.getTotalNum(),selectedTicketCat.getAvailNum());        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Show ID: " + selectedShowId + "--> Category: " + selectedTicketCat.getCatName() + " " + " edited successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while editing the category: " + ex.getMessage(), ""));
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
    FacesMessage msg = new FacesMessage("Cancelled editing category");
    FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<TicketCat> getTicketCats() {
        return ticketCats;
    }

    public void setTicketCats(List<TicketCat> ticketCats) {
        this.ticketCats = ticketCats;
    }

    public TicketCat getSelectedTicketCat() {
        return selectedTicketCat;
    }

    public void setSelectedTicketCat(TicketCat selectedTicketCat) {
        this.selectedTicketCat = selectedTicketCat;
    }

    public List<String> getCatNames() {
        return catNames;
    }

    public void setCatNames(List<String> catNames) {
        this.catNames = catNames;
    }

    public void setSelectedShowId(Long selectedShowId) {
        this.selectedShowId = selectedShowId;
    }

    public Long getSelectedShowId() {
        return selectedShowId;
    }

    public ShowTicketBeanRemote getShowTicketBean() {
        return showTicketBean;
    }

    public void setShowTicketBean(ShowTicketBeanRemote showTicketBean) {
        this.showTicketBean = showTicketBean;
    }

    public TicketCat getNewTicketCat() {
        return newTicketCat;
    }

    public void setNewTicketCat(TicketCat newTicketCat) {
        this.newTicketCat = newTicketCat;
    }
    
}
