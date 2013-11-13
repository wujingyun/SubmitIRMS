/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;
import ejb.CustomerBeanRemote;
import ejb.LoyaltyPlanBeanRemote;
import entity.Customer;
import entity.Membership;
import entity.PointTrans;
import entity.ShowTicketTrans;
import entity.TicketSeat;
import exception.ExistException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class CustomerLoyaltyProgramManagedBean implements Serializable {

    @EJB
    private LoyaltyPlanBeanRemote lpb;
    @EJB
    private CustomerBeanRemote cbb;
    
  
    private String rewardPoint;
    private String redeemPoint;
 
    private String msg;
    private Customer user;
   private List <ShowTicketTrans> showTicketTrans;
     private List <TicketSeat> ticketSeat;
    public CustomerLoyaltyProgramManagedBean() {
    }

    @PostConstruct
    public void init() throws ExistException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        long uid = (Long) request.getSession().getAttribute("userId");
        System.out.println(uid+"account edit get user id ================");
        user = cbb.getCustomerById(uid);
       showTicketTrans=lpb.getShowTicketTransByCID(uid);
       for (int i=0;i<showTicketTrans.size();i++)
       {
       ticketSeat=showTicketTrans.get(i).getTicketSeat();
      
       }
           
           }

    public List<TicketSeat> getTicketSeat() {
        return ticketSeat;
    }

    public void setTicketSeat(List<TicketSeat> ticketSeat) {
        this.ticketSeat = ticketSeat;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public List<ShowTicketTrans> getShowTicketTrans() {
        return showTicketTrans;
    }

    public void setShowTicketTrans(List<ShowTicketTrans> showTicketTrans) {
        this.showTicketTrans = showTicketTrans;
    }

   

    
    
    
    
   

    public void setLpb(LoyaltyPlanBeanRemote lpb) {
        this.lpb = lpb;
    }

  

    public void setRewardPoint(String rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public void setRedeemPoint(String redeemPoint) {
        this.redeemPoint = redeemPoint;
    }

    public LoyaltyPlanBeanRemote getLpb() {
        return lpb;
    }

    public void setCbb(CustomerBeanRemote cbb) {
        this.cbb = cbb;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCustomer(Customer user) {
        this.user = user;
    }

    public CustomerBeanRemote getCbb() {
        return cbb;
    }

    public String getMsg() {
        return msg;
    }

    public Customer getCustomer() {
        return user;
    }


    public String getRewardPoint() {
        return rewardPoint;
    }

    public String getRedeemPoint() {
        return redeemPoint;
    }

 
}