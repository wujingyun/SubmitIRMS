/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;


import ejb.AttractionSessionBeanRemote;
import entity.Attraction;
import entity.AttractionPass;
import entity.AttractionTicket;
import exception.ExistException;
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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@ViewScoped
public class AttractionManagedBean implements Serializable {
    
    @EJB
    AttractionSessionBeanRemote asbr;
    
    private Attraction att;
    private List<Attraction> attList;
    private String name;
    private int maxQuota;
    private String operatingHours;
    private String location;
    private String descriptions;
    private Attraction selectedAttraction;
   
    private List<AttractionPass> ap;
    private List<AttractionTicket> at;
    private String attractionName;
    
    private AttractionPass selectedPass;
    private AttractionPass newPass;
    
    private AttractionTicket selectedTicket;
    private AttractionTicket newTicket;
    
    @PostConstruct
    private void init(){
        this.attList = asbr.getAttractions();
      
    }
    
    public AttractionManagedBean() {
        att=  new Attraction();
        newPass = new AttractionPass();
        newTicket = new AttractionTicket();
    }
    public String reinit() {
        this.att = new Attraction();
        return null;
    }
    
    public void attrationCreate(ActionEvent event) throws ExistException{
        try {
          System.err.println("Attraction Manged Bean:...OP hr"+ this.getOperatingHours());
        asbr.createAttraction(att.getName(),att.getMaxQuota(),att.getOperatingHours(),att.getLocation(),att.getDescriptions());
        attList.add(att);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attraction Added successfully", ""));
        }catch(Exception ex){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while adding the attraction: " + ex.getMessage(), ""));
        }
    }
    
    public void onEdit(RowEditEvent event) throws ExistException {
        if(selectedAttraction==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select an attraction ", ""));
            }
           asbr.editAttraction(selectedAttraction.getName(),selectedAttraction.getMaxQuota(),selectedAttraction.getOperatingHours(), selectedAttraction.getLocation(), selectedAttraction.getDescriptions());
        
        FacesMessage msg = new FacesMessage("Attraction: " +((Attraction) event.getObject()).getName()+" Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
     public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing Attraction: " +((Attraction) event.getObject()).getName()+" ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
      public void deleteAttraction(ActionEvent event) throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(hotelName);
            if(selectedAttraction==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select an attraction", ""));
            }
            asbr.deleteAttraction(selectedAttraction.getName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attraction " + selectedAttraction.getName() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the attraction: " + ex.getMessage(), ""));
        }
    }
      
      public void handleAttractionChange() throws ExistException {
        if (attractionName != null && !attractionName.equals("")) {
            ap = asbr.getPass(attractionName); 
            at = asbr.getTicket(attractionName);
        } else {
            System.out.println("empty attraction name");
        }
    }
      public String reinitPass(){
           this.newPass = new AttractionPass();
         return null;
      }
      
      public String reinitTicket(){
          this.newTicket = new AttractionTicket();
          return null;
      }
      
       public void passCreate(ActionEvent event) throws ExistException{
        try {
          System.err.println("Attraction Manged Bean:...passCreate"+attractionName);
        asbr.createPass(attractionName, newPass.getPassName(),newPass.getPrice(), newPass.getPassType(), newPass.getRemarks());
        ap.add(newPass);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pass Added successfully", ""));
        }catch(Exception ex){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while adding the Pass: " + ex.getMessage(), ""));
        }
    }
        public void ticketCreate(ActionEvent event) throws ExistException{
        try {
          System.err.println("Attraction Manged Bean:...ticketCreate"+attractionName);
        asbr.createTicket(attractionName, newTicket.getTicketName(), newTicket.getTicketPrice(),newTicket.getTicketType(), newTicket.getRemarks());
        at.add(newTicket);
      
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ticket Added successfully", ""));
        }catch(Exception ex){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while adding the Ticket: " + ex.getMessage(), ""));
        }
    }
     
     public void onPassEdit(RowEditEvent event) throws ExistException {
        if(selectedPass ==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a pass ", ""));
            }
         
           asbr.editPass(selectedPass.getPassName(), selectedPass.getPrice(), selectedPass.getPassType(), selectedPass.getRemarks());
        FacesMessage msg = new FacesMessage("Pass: " +((AttractionPass) event.getObject()).getPassName()+" Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     public void onTicketEdit(RowEditEvent event) throws ExistException {
        if(selectedTicket ==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a ticket ", ""));
            }
           asbr.editTicket(selectedTicket.getTicketName(), selectedTicket.getTicketPrice(), selectedTicket.getTicketType(), selectedTicket.getRemarks());
     //      asbr.editPass(selectedPass.getPassName(), selectedPass.getPrice(), selectedPass.getPassType(), selectedPass.getRemarks());
        FacesMessage msg = new FacesMessage("Ticket: " +((AttractionTicket) event.getObject()).getTicketName()+" Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
      public void onPassCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing Pass: " +((AttractionPass) event.getObject()).getPassName()+" ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
       public void onTicketCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing Ticket: " +((AttractionTicket) event.getObject()).getTicketName()+" ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
      
    public void deletePass(ActionEvent event) throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(hotelName);
            if(selectedPass==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a pass", ""));
            }
            asbr.deletePass(attractionName,selectedPass.getPassName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pass " + selectedPass.getPassName() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the Pass: " + ex.getMessage(), ""));
        }
    }
    
    public void deleteTicket(ActionEvent event) throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(hotelName);
            if(selectedTicket==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a ticket", ""));
            }
            asbr.deleteTicket(attractionName, selectedTicket.getTicketName());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ticket " + selectedTicket.getTicketName() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the ticket: " + ex.getMessage(), ""));
        }
    }
    public Attraction getAtt() {
        return att;
    }

    public void setAtt(Attraction att) {
        this.att = att;
    }

    public List<Attraction> getAttList() {
        return attList;
    }

    public void setAttList(List<Attraction> attList) {
        this.attList = attList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxQuota() {
        return maxQuota;
    }

    public void setMaxQuota(int maxQuota) {
        this.maxQuota = maxQuota;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Attraction getSelectedAttraction() {
        return selectedAttraction;
    }

    public void setSelectedAttraction(Attraction selectedAttraction) {
        this.selectedAttraction = selectedAttraction;
    }

    public List<AttractionPass> getAp() {
        return ap;
    }

    public void setAp(List<AttractionPass> ap) {
        this.ap = ap;
    }

    public List<AttractionTicket> getAt() {
        return at;
    }

    public void setAt(List<AttractionTicket> at) {
        this.at = at;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public AttractionPass getSelectedPass() {
        return selectedPass;
    }

    public void setSelectedPass(AttractionPass selectedPass) {
        this.selectedPass = selectedPass;
    }

    public AttractionPass getNewPass() {
        return newPass;
    }

    public void setNewPass(AttractionPass newPass) {
        this.newPass = newPass;
    }

    public AttractionTicket getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(AttractionTicket selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public AttractionTicket getNewTicket() {
        return newTicket;
    }

    public void setNewTicket(AttractionTicket newTicket) {
        this.newTicket = newTicket;
    }

    
}
