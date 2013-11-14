package managedBean;

import ejb.ShowExecutionBeanRemote;
import entity.EntShow;
import exception.ExistException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;

/**
 * @author Jiao Shen
 */

@ManagedBean
@ViewScoped
public class ShowExecutionManagedBean implements Serializable{
    
    @EJB
    private ShowExecutionBeanRemote showExecutionBean;
    
    private List<EntShow> shows;
    private EntShow newShow = new EntShow();
    private EntShow selectedShow = new EntShow();
    private List<Long> showIds;
    
    /**
     * Creates a new instance of ShowExcutionBean
     */
    public ShowExecutionManagedBean() throws ExistException {
    }
        
    @PostConstruct
    public void init() throws ExistException{
        this.shows = showExecutionBean.getShows();
        //System.out.println("Managed-Execution-1==============================");
        showIds = new ArrayList<Long>();
        //System.out.println("Managed-Execution-2==============================");
        for(int i = 0; i<shows.size(); i++){
            showIds.add(shows.get(i).getShowId());
            //System.out.println("Managed-Execution-3==============================");
        }
    }
    
    public String reinit(){
        this.newShow = new EntShow();
        //System.out.println("Managed-Execution-4==============================");
        return null;
    }
    
    public void createShow(ActionEvent event) throws ExistException{
        try{
            EntShow newShow2 = showExecutionBean.addShow(newShow.getShowName(), newShow.getShowVenue(), newShow.getShowDate(), newShow.getStartSalesDate(), newShow.getDuration(), newShow.getDescription());
            //System.out.println("Managed-Execution-5==============================");
            //shows.add(newShow2);
            this.shows = showExecutionBean.getShows();
            //System.out.println("Managed-Execution-6==============================");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Show: " + selectedShow.getShowId() + " " + " added successfully", ""));
        }
        catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR: " + ex.getMessage(), "Clash exists."));
        }
    }
    
    public void deleteShow(ActionEvent event) throws ExistException {
        try {
            if(selectedShow==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a show", ""));
            }
            this.showExecutionBean.deleteShow(selectedShow.getShowId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Show: " + selectedShow.getShowId() + " " + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the show: " + ex.getMessage(), ""));
        }
    }

    public void onEdit(RowEditEvent event) throws ExistException {
        try{
            if(selectedShow==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a show", ""));
            }
            //System.out.println("BEFORE: onEdit line");
            this.showExecutionBean.editShow(selectedShow.getShowId(), selectedShow.getShowName(), selectedShow.getShowVenue(), selectedShow.getShowDate(), selectedShow.getStartSalesDate(), selectedShow.getDuration(), selectedShow.getDescription());        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Show: " + selectedShow.getShowId() + " " + " edited successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while editing the show: " + ex.getMessage(), ""));
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
    FacesMessage msg = new FacesMessage("Cancelled editing show");
    FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void selectShow(ActionEvent event) throws IOException {
    
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedShowId", selectedShow.getShowId());
   System.out.println("selected Show id"+selectedShow.getShowId());
    FacesContext.getCurrentInstance().getExternalContext().redirect("/IRMS-war/irmsShowSelectSeat.xhtml");
    }
    
   public List<EntShow> getShows() {
        return shows;
    }

    public void setShows(List<EntShow> shows) {
        this.shows = shows;
    }

    public EntShow getSelectedShow() {
        return selectedShow;
    }

    public void setSelectedShow(EntShow selectedShow) {
        this.selectedShow = selectedShow;
    }

    public List<Long> getShowIds() {
        return showIds;
    }

    public void setShowIds(List<Long> showIds) {
        this.showIds = showIds;
    }

    public ShowExecutionBeanRemote getShowExecutionBean() {
        return showExecutionBean;
    }

    public void setShowExecutionBean(ShowExecutionBeanRemote showExecutionBean) {
        this.showExecutionBean = showExecutionBean;
    }

    public EntShow getNewShow() {
        return newShow;
    }

    public void setNewShow(EntShow newShow) {
        this.newShow = newShow;
    }
    
}
