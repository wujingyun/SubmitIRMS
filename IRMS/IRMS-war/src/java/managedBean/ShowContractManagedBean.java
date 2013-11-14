package managedBean;

import ejb.ShowContractBeanRemote;
import entity.ShowContract;
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
public class ShowContractManagedBean implements Serializable{
    
    @EJB
    private ShowContractBeanRemote showContractBean;
    
    private List<ShowContract> showContracts;
    private ShowContract newShowContract = new ShowContract();
    private ShowContract selectedContract;
    private List<Long> contractIds;
    
    /**
     * Creates a new instance of ShowContractBean
     */
    public ShowContractManagedBean() throws ExistException {
    }
        
    @PostConstruct
    public void init() throws ExistException{
        this.showContracts = showContractBean.getContracts();
        //System.out.println("Managed-Contract-1==============================");
        contractIds = new ArrayList<Long>();
        //System.out.println("Managed-Contract-2==============================");
        for(int i = 0; i<showContracts.size(); i++){
            contractIds.add(showContracts.get(i).getContractId());
            //System.out.println("Managed-Contract-3==============================");
        }
    }
    
    public String reinit(){
        this.newShowContract = new ShowContract();
        //System.out.println("Managed-Contract-4==============================");
        return null;
    }
    
    public void checkAvailability() throws ExistException{
        
    }
    
    public void makeReservation() throws ExistException{
        
    }
    
    public void createContract(ActionEvent event) throws ExistException{
        try{
            ShowContract newContract = showContractBean.signContract(newShowContract.getShowName(),newShowContract.getShowDate(), newShowContract.getShowVenue(), newShowContract.getStaffId(), newShowContract.getSignDate());
            //System.out.println("Managed-Contract-5==============================");
            showContracts.add(newContract);
            //System.out.println("Managed-Contract-6==============================");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contract: " + selectedContract.getContractId() + " " + " added successfully", ""));
        }
        catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR:" + ex.getMessage(),"Clash exists."));
        }
    }
    
    public void deleteContract(ActionEvent event) throws ExistException {
        try {
            if(selectedContract==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a contract", ""));
            }
            this.showContractBean.terminateContract(selectedContract.getContractId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contract: " + selectedContract.getContractId() + " " + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the contract: " + ex.getMessage(), ""));
        }
    }

    /*
    public void onEdit(RowEditEvent event) throws ExistException {
    if(selectedVenue==null){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a venue", ""));
    }
    this.showTheatreBean.edit(selectedVenue.getVenueName(), selectedVenue.getVenueAddr(),selectedVenue.getPrevailingRates());
    FacesMessage msg = new FacesMessage("Venue: " + selectedVenue.getVenueName() + " " + " Edited", "");
    
    FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     */
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
    FacesMessage msg = new FacesMessage("Cancelled editing Contract");
    FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<ShowContract> getShowContracts() {
        return showContracts;
    }

    public void setShowContracts(List<ShowContract> showContracts) {
        this.showContracts = showContracts;
    }

    public ShowContract getSelectedContract() {
        return selectedContract;
    }

    public void setSelectedContract(ShowContract selectedContract) {
        this.selectedContract = selectedContract;
    }

    public List<Long> getContractIds() {
        return contractIds;
    }

    public void setContractIds(List<Long> contractIds) {
        this.contractIds = contractIds;
    }

    public ShowContractBeanRemote getShowContractBean() {
        return showContractBean;
    }

    public void setShowContractBean(ShowContractBeanRemote showContractBean) {
        this.showContractBean = showContractBean;
    }

    public ShowContract getNewShowContract() {
        return newShowContract;
    }

    public void setNewShowContract(ShowContract newShowContract) {
        this.newShowContract = newShowContract;
    }

}
