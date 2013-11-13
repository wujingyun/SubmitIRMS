/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.AdminUserBeanRemote;
import ejb.CustomerBeanRemote;
import ejb.InternalMessageBean;
import ejb.ShowBeanRemote;
import ejb.ShowExecutionBean;
import ejb.ShowExecutionBeanLocal;
import entity.Customer;
import entity.EntShow;
import entity.UserAccount;
import entity.InternalMessage;
import entity.TicketCat;
import entity.TicketSeat;
import exception.ExistException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@ViewScoped
public class ShowPurchaseManagedBean implements Serializable {

    @EJB
    ShowBeanRemote showBeanRemote;
    @EJB
    CustomerBeanRemote aub;
    private Customer customer;
    private String showTitle;
    private List<EntShow> getList;
    private Map<String, String> entShowList = new HashMap<String, String>();
    // private List<EntShow> entShowList;
    private List<Long> ticketSeatList;
    private String purchaseShowId;
    private List<TicketCat> catList;
    private List<TicketSeat> allticketSeatList;
    private Long showId=Long.valueOf(1);
   private List<String> catAvailabilityList;
    private List<String> seatAvailabilityList;
        private List<Long> seatIdList;
     private TicketCat firstCat;
      private TicketCat secondCat;
       private TicketCat thirdCat;
       private TicketCat forthCat;
       private TicketCat fifthCat;
        private String selectedCat;
    /**
     * Creates a new instance of CreateInternalMsgManagedBean
     */
    public ShowPurchaseManagedBean() {
    }

    @PostConstruct
    public void init() throws ExistException {
        System.out.println("ShowPurchaseManagedBean --> init============");
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        showTitle = paramMap.get("showTitle");

        getList = showBeanRemote.getShowByName(showTitle);
        System.out.println("ShowPurchaseManagedBean --> init============" + getList.size());
       
        for (EntShow entShow : getList) {
            System.out.println("ShowPurchaseManagedBean --> init============" + entShow.getShowDate().toString());
            System.out.println("ShowPurchaseManagedBean --> init============" + entShow.getShowId().toString());
            entShowList.put(entShow.getShowDate().toString(), entShow.getShowId().toString());
           }
        
        allticketSeatList=new ArrayList();
        allticketSeatList=showBeanRemote.getAllSeat(showId);
         catList=new ArrayList();
        catList=(List<TicketCat>) showBeanRemote.getCategoryInfo(showId); 
        
        firstCat=catList.get(0);
        secondCat=catList.get(1);
        thirdCat=catList.get(2);
        forthCat=catList.get(3);
        fifthCat=catList.get(4);
         catAvailabilityList=new ArrayList ();
        for (int i=0;i<catList.size();i++) {
            if(catList.get(i).getAvailNum()>0)
            {   catAvailabilityList.add("Available");
            }else
            {catAvailabilityList.add("Not available");
            }
           }
        seatAvailabilityList=new ArrayList();
        seatIdList=new ArrayList();
    
         for (int i=0;i<allticketSeatList.size();i++) {
    if(allticketSeatList.get(i).getSeatStatus())
            {   seatAvailabilityList.add("Available");
            seatIdList.add(allticketSeatList.get(i).getSeatId());
            }else
            {seatAvailabilityList.add("Not available");
             seatIdList.add(allticketSeatList.get(i).getSeatId());
            }
           }
    }
    

    
    public void buyInfo() throws ExistException{
    this.selectedCat= FacesContext.getCurrentInstance().
		getExternalContext().getRequestParameterMap().get("buyInfo");
this.setSelectedCat(selectedCat);
    System.out.println("ShowPurchaseManagedBean --> BUYINFO============" +selectedCat);
    String url = "irmsShowPurchase.xhtml";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect(url);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while redirecting page: " + ex.getMessage(), ""));
        }
    }
    
    
     /*public void BookTicket() throws ExistException{
  try{ HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        long uid =1;
                //= (Long) request.getSession().getAttribute("userId");
        ticketSeatList=new ArrayList();
         ticketSeatList.add(Long.valueOf("1"));
         ticketSeatList.add(Long.valueOf("2"));
         ticketSeatList.add(Long.valueOf("3"));
         ticketSeatList.add(Long.valueOf("4"));
         showBeanRemote.makeBooking(uid, ticketSeatList);
    }
   catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ticket Reservation Fail",""));
                return;
            }

        
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ticket Reserved Successfully",""));
                
     }
*/
    public Customer getCustomer() {
        return customer;
    }

    public List<Long> getTicketSeatList() {
        return ticketSeatList;
    }

    public List<Long> getSeatIdList() {
        return seatIdList;
    }

    public List<TicketSeat> getAllticketSeatList() {
        return allticketSeatList;
    }

    public List<String> getSeatAvailabilityList() {
        return seatAvailabilityList;
    }
    public List<String> getCatAvailabilityList() {
        return catAvailabilityList;
    }

    public void setSelectedCat(String selectedCat) {
        this.selectedCat = selectedCat;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    

    public TicketCat getFirstCat() {
        return firstCat;
    }

    public TicketCat getSecondCat() {
        return secondCat;
    }

    public TicketCat getThirdCat() {
        return thirdCat;
    }

    public TicketCat getForthCat() {
        return forthCat;
    }

    public TicketCat getFifthCat() {
        return fifthCat;
    }

    public String getSelectedCat() {
        return selectedCat;
    }

    
    public Long getShowId() {
        return showId;
    }

   
    public List<EntShow> getGetList() {
        return getList;
    }

    public Map<String, String> getEntShowList() {
        return entShowList;
    }

    public void setGetList(List<EntShow> getList) {
        this.getList = getList;
    }

    public void setCatList(List<TicketCat> catList) {
        this.catList = catList;
    }

    public List<TicketCat> getCatList() {
        return catList;
    }

    public void setEntShowList(Map<String, String> entShowList) {
        this.entShowList = entShowList;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

   

    public String getPurchaseShowId() {
        return purchaseShowId;
    }

    public void setPurchaseShowId(String purchaseShowId) {
        this.purchaseShowId = purchaseShowId;
    }
}
