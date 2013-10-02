/*
 */
package managedBean;

import entity.Hotel;
import ejb.HotelRoomBeanRemote;
import exception.ExistException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;

/**
 *
 * @author Ser3na
 */
@ManagedBean
@ViewScoped
public class SearchManagedBean {

    @EJB
    private HotelRoomBeanRemote hrb;
    private String searchName;
    private List<Hotel> hotelLists = new ArrayList<Hotel>();
    @EJB
    HotelRoomBeanRemote hotelrb;
private List<Hotel> hotelhotels;
    /**
     * Creates a new instance of SearchManagedBean
     */
    public SearchManagedBean() {
    }

    public List<String> complete(String query) throws ExistException {
        List<String> results = new ArrayList<String>();

        List<Hotel> hotelList = hotelrb.getHotels();
         System.out.println(hotelList.size());
         System.out.println("get all hotel inho");
        for (Object o : hotelList) {
            Hotel emp = (Hotel) o;
           // System.out.println(emp.getName()+"...................");
            if (emp.getName().startsWith(query)) {
                results.add(emp.getName());
               // System.out.println(emp.getName());
            }
        }
        return results;
    }

    public void searchByName(ActionEvent event) throws IOException, ExistException {
        setHotelLists(hrb.getHotelByDescription(searchName));
        System.out.println(searchName);
     System.out.println(hotelLists.size());
     if (hotelLists.size()==0)
     {FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No record Find",""));
                }
     else { FacesContext.getCurrentInstance().getExternalContext().getFlash().put("Hotels", hotelLists);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/IRMS-war/webSearchResult.xhtml");}
    }

    public void initView(PhaseEvent event) {
        hotelhotels = (List<Hotel>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("Hotels");
    }

    public void setHrb(HotelRoomBeanRemote hrb) {
        this.hrb = hrb;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void setHotelLists(List<Hotel> hotelLists) {
        this.hotelLists = hotelLists;
    }

    public void setHotelrb(HotelRoomBeanRemote hotelrb) {
        this.hotelrb = hotelrb;
    }

    public HotelRoomBeanRemote getHrb() {
        return hrb;
    }

    public String getSearchName() {
        return searchName;
    }

    public List<Hotel> getHotelLists() {
        return hotelLists;
    }

    public HotelRoomBeanRemote getHotelrb() {
        return hotelrb;
    }

    public List<Hotel> getHotelhotels() {
        return hotelhotels;
    }

    public void setHotelhotels(List<Hotel> hotelhotels) {
        this.hotelhotels = hotelhotels;
    }
}
