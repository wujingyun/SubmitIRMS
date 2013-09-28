/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelDiscountBeanRemote;
import entity.DiscountScheme;
import exception.ExistException;
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
public class DiscountSchemeBean {
    @EJB
    HotelDiscountBeanRemote hotelDiscountBean;
    private String hotelName = "Holiday Inn";
    private List<DiscountScheme> schemes;
    private DiscountScheme newScheme = new DiscountScheme();
    private DiscountScheme selectedScheme;

    /**
     * Creates a new instance of DiscountSchemeBean
     */
    public DiscountSchemeBean() {
    }
    
    @PostConstruct
    public void init() throws ExistException {
        this.schemes = hotelDiscountBean.getDiscountSchemes(hotelName);
    }

    public String reinit() {
        this.newScheme = new DiscountScheme();
        return null;
    }

    public void createScheme() throws ExistException {
        newScheme.setDateCreated(Calendar.getInstance());
        //ewScheme.setId(UUID.randomUUID().getMostSignificantBits());
        hotelDiscountBean.addDiscountScheme(hotelName, newScheme.getName(), newScheme.getEligibility(), newScheme.getDescription(), newScheme.getDiscountRate(), newScheme.getDateCreated());
        schemes.add(newScheme);
    }

    public void deleteScheme() throws ExistException {
        try {
            //String hotelName=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedHotel");
            //System.out.println(hotelName);
            if (selectedScheme == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a scheme", ""));
            }
            this.hotelDiscountBean.removeDiscountScheme(hotelName, selectedScheme.getName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hotel " + hotelName + " " + selectedScheme.getName() + " deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the scheme: " + ex.getMessage(), ""));
        }
    }

    public void onEdit(RowEditEvent event) throws ExistException {
        if (selectedScheme == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a scheme", ""));
        }
        //System.out.println(hotelName + " + " + selectedItem.getName());
        this.hotelDiscountBean.editDiscountScheme(hotelName, selectedScheme.getName(), selectedScheme.getEligibility(), selectedScheme.getDescription(),selectedScheme.getDiscountRate());

        FacesMessage msg = new FacesMessage("Hotel: " + hotelName + " " + ((DiscountScheme) event.getObject()).getName() + " Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing scheme: " + ((DiscountScheme) event.getObject()).getName() + " ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleHotelChange() throws ExistException {
        if (hotelName != null && !hotelName.equals("")) {
            schemes = hotelDiscountBean.getDiscountSchemes(hotelName);
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

    public List<DiscountScheme> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<DiscountScheme> schemes) {
        this.schemes = schemes;
    }

    public DiscountScheme getNewScheme() {
        return newScheme;
    }

    public void setNewScheme(DiscountScheme newScheme) {
        this.newScheme = newScheme;
    }

    public DiscountScheme getSelectedScheme() {
        return selectedScheme;
    }

    public void setSelectedScheme(DiscountScheme selectedScheme) {
        this.selectedScheme = selectedScheme;
    }
    
}
