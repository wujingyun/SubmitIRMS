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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class ShowPurchaseConfirmManagedBean implements Serializable {

    @EJB
    ShowBeanRemote showBeanRemote;
    @EJB
    AdminUserBeanRemote aub;
    @EJB
    CustomerBeanRemote cbb;
    private Customer customer;
    private String username;
    private String password;
    private String showTitle;
 private String[] array ;
    private String showId;
private Long showIdlong;
        private String seat;
         private List<Long> ticketSeatList;
         private double totalAmount;
    /**
     * Creates a new instance of CreateInternalMsgManagedBean
     */
    public ShowPurchaseConfirmManagedBean() {
    }

    @PostConstruct
    public void init() throws ExistException {
        System.out.println("init");
         FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
       
         showId = paramMap.get("showId");
         System.out.println("init"+showId);
        showIdlong = Long.parseLong(showId);
         showTitle=showBeanRemote.getShowNameById(showIdlong);
         System.out.println("init"+showTitle);
          seat = paramMap.get("seat");
          System.out.println("init"+seat);
          
                  array = seat.split("\\,",-1); 
  System.out.println("init"+array.length);
      
        ticketSeatList=new ArrayList();
        for (int i=0;i<array.length-1;i++){
            ticketSeatList.add(Long.valueOf(array[i])); 
            System.out.println("init"+array[i]);
        }
        totalAmount=showBeanRemote.getTotalAmount(ticketSeatList);
        
    }

     public String login(ActionEvent actionEvent) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirct;

        System.out.println("username is " + username + " password is " + password);
        String hashPassword = cbb.hashPassword(password);
        //if attmep number lesser than 5, allow login
        //if attemp number larger than 5 and account has locked, only allow login after 5 mins .  

        //attemp number lesser than 5 or it's 5 mins after last attemp, allow login
        if ((cbb.getLoginAttemp(username) <= 2) || (cbb.checkLockOut(username) == true)) {
           // System.out.println("login number============" + cbb.getLoginAttemp(username));
            //have to reset attemp number to 0 and update attemp time(in the case where account is unlocked
            //, otherwise, if login fails again, the account will be locked for another 5 min
            if (cbb.checkLockOut(username)) {
                cbb.setLoginAttempToZero(username);
                cbb.updateLoginAttempTime(username);
            }
            System.out.println("login number============" +username);
            System.out.println("login number============" + hashPassword);
            //auth
            if ((username != null) && (password != null) && (cbb.verifyPassword(username, hashPassword))) {
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
             
                customer = cbb.findCustomer(username);
                cbb.setLoginAttempToZero(username);
                cbb.updateLoginAttempTime(username);
                long customerId=cbb.findCustomer(username).getId();
                 request.getSession().setAttribute("userId", customerId);
                System.out.println("get role ============");
                request.getSession().setAttribute("role", "customer");
                System.out.println("get role finish============");
                request.getSession().setAttribute("isLogin", true);



                // success,装入session中

                Map<String, Object> map = facesContext.getExternalContext().getSessionMap();

                map.put("user", customer);

                this.customer = customer;

                redirct = "fail";
              
              
            } //auth fails
            
             else if (!cbb.checkCustomerExist(username)) {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Username doesn't exist");
                customer = null;

                redirct = "fail";

            } else if (cbb.checkCustomerExist(username) && (!cbb.verifyPassword(username, hashPassword))) {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Wrong password");
                customer = null;

                redirct = "fail";


            } else {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid Credentials");
                customer = null;

                redirct = "fail";
            }
        } //time diff is less than 5 mins, don't allow login 
        else {
            System.out.println("Exceed max login nunmber");
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Exceed Max Login Number! Please Try Login After 5 Mins");
            redirct = "fail";

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        return redirct;

    }

    public void BookTicket(ActionEvent actionEvent) throws ExistException{
         RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
 //String[] splitArray = seat.split(",");
  array = seat.split("\\,",-1); 
  System.out.println("bookTicket"+array.length);
  try{ HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        long uid =1;
                //= (Long) request.getSession().getAttribute("userId");
        ticketSeatList=new ArrayList();
        for (int i=0;i<array.length-1;i++){
            ticketSeatList.add(Long.valueOf(array[i])); 
            System.out.println("bookTicket"+array[i]);
        }
        
         
         showBeanRemote.makeBooking(uid, ticketSeatList);
    }
   catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ticket Reservation Fail",""));
                context.addCallbackParam("loggedIn", false);
                return;
                
            }

        
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ticket Reserved Successfully",""));
          context.addCallbackParam("loggedIn", true);      
    
    }

    public AdminUserBeanRemote getAub() {
        return aub;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public String getShowId() {
        return showId;
    }

    public String getSeat() {
        return seat;
    }

    public void setAub(AdminUserBeanRemote aub) {
        this.aub = aub;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public void setShowIdlong(Long showIdlong) {
        this.showIdlong = showIdlong;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getShowIdlong() {
        return showIdlong;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
 
    

}
