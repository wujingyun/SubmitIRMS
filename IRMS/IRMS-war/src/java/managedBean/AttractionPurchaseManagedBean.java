/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.AttractionSessionBeanRemote;
import ejb.CustomerBeanRemote;
import entity.Attraction;
import entity.AttractionPass;
import entity.AttractionTicket;
import entity.Customer;
import exception.ExistException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@ViewScoped
public class AttractionPurchaseManagedBean implements Serializable {

   // @EJB
  //  AttractionSessionBeanRemote asb;
    @EJB
    AttractionSessionBeanRemote asbr;
    @EJB
    CustomerBeanRemote cbb;
     private List<Attraction> attList;
      private String attractionName;
        private List<AttractionPass> ap;
        private Map<String, String> apList = new HashMap<String, String>();
        private Map<String, String> atList = new HashMap<String, String>();
    private List<AttractionTicket> at;
        private AttractionPass selectedTicket;
    private AttractionPass selectedPass;
     private String username;
    private String password;
    private Customer customer;
    private Date doa;
   // private Calendar doaCalender;
   private String selectPassId1;
   private String selectPassId2;
   private String selectPassId3;
   private String quantity1;
   private String quantity2;
   private String quantity3;
    private String selectTicketId1;
   private String selectTicketId2;
   private String selectTicketId3;
   private String quantity4;
   private String quantity5;
   private String quantity6;
  private int q1=0, q2=0, q3=0;
  private int q4=0, q5=0, q6=0;
    /**
     * Creates a new instance of CreateInternalMsgManagedBean
     */
    public AttractionPurchaseManagedBean() {
        
    }

    @PostConstruct
    private void init(){
        this.attList = asbr.getAttractions();
      
    }
    

    public void handleAttractionChange() throws ExistException {
      
        if (attractionName != null && !attractionName.equals("")) {
            ap = asbr.getPass(attractionName); 
            at = asbr.getTicket(attractionName);
            System.out.println("handlechage ap"+ap.size());
              System.out.println("handlechage at"+at.size());
            for (int i=0;i<ap.size();i++){
                apList.put(ap.get(i).getPassName(), ap.get(i).getPassName());
            System.out.println("apList"+apList.get(ap.get(i).getPassName()));
            }
            for (int i=0;i<at.size();i++){
               atList.put(at.get(i).getTicketName(), at.get(i).getTicketName());
            System.out.println("apList"+atList.get(at.get(i).getTicketName()));
            }
        } else {
            System.out.println("empty attraction name");
        }
    }
    public void buyInfo() throws ExistException{
   
    }
    public void handlepassChange() {
            System.out.println("handlepassChange"+quantity1);
              System.out.println("handlepassChange"+quantity2);
              System.out.println("handlepassChange"+quantity3);
        if (quantity1 != null && !quantity1.equals("")) {
            q1=Integer.parseInt(quantity1);
             System.out.println("handlepassChange"+quantity1);
        }}
   
     public void BookPass() throws ExistException{
        RequestContext context = RequestContext.getCurrentInstance();
       try{ 
           HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         System.out.println("BookPass"+username);
         long customerId = (Long)request.getSession().getAttribute("userId");
       
           System.out.println("BookPass"+customer.getUserName());
             System.out.println("BookPass"+quantity1);
              System.out.println("BookPass"+selectPassId1);
               System.out.println("BookPass"+selectPassId2);
             q1=Integer.parseInt(quantity1);
               q2=Integer.parseInt(quantity2);
                 q3=Integer.parseInt(quantity3);
       if (q1!=0){ System.out.println("BookPass"+selectPassId1);asbr.buyPass(customerId, q1,selectPassId1);}
       if (q2!=0){System.out.println("BookPass"+selectPassId2);asbr.buyPass(customerId, q2,selectPassId2);}
       if (q3!=0){System.out.println("BookPass"+selectPassId3);asbr.buyPass(customerId, q3,selectPassId3);}
       }
        catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attraction Pass purchase Fail",""));
               
                return;
                
            }

        
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attraction Pass Successfully purchased",""));
              
       }
     
     
       public void BookTicket() throws ExistException{
        RequestContext context = RequestContext.getCurrentInstance();
       try{ 
           HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         System.out.println("BookTicket"+username);
         long customerId = (Long)request.getSession().getAttribute("userId");
       
           System.out.println("BookTicket"+customer.getUserName());
             System.out.println("BookTicket"+quantity4);
              System.out.println("BookTicket"+selectTicketId1);
               System.out.println("BookTicket"+selectTicketId2);
             q4=Integer.parseInt(quantity4);
               q5=Integer.parseInt(quantity5);
                 q6=Integer.parseInt(quantity6);
                  System.out.println("BookTicket"+selectTicketId2);
                 Calendar doaCalender= Calendar.getInstance();
  doaCalender.setTime(doa);
 System.out.println("BookTicket----->set Calendar"+doaCalender);
       if (q4!=0){ System.out.println("BookTicket"+selectTicketId1);asbr.purchaseTicket(customerId, q4,selectTicketId1, doaCalender);}
       if (q5!=0){System.out.println("BookTicket"+selectTicketId2);asbr.purchaseTicket(customerId, q5,selectTicketId2,doaCalender);}
       if (q6!=0){System.out.println("BookTicket"+selectTicketId3);asbr.purchaseTicket(customerId, q6,selectTicketId3,doaCalender);}
       }
        catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attraction Ticket purchase Fail",""));
               
                return;
                
            }

        
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attraction Ticket Successfully purchased",""));
              
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setSelectPassId1(String selectPassId1) {
        this.selectPassId1 = selectPassId1;
    }

    public Map<String, String> getApList() {
        return apList;
    }

    public void setApList(Map<String, String> apList) {
        this.apList = apList;
    }

    public void setSelectedTicket(AttractionPass selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public void setSelectTicketId1(String selectTicketId1) {
        this.selectTicketId1 = selectTicketId1;
    }

    public void setSelectTicketId2(String selectTicketId2) {
        this.selectTicketId2 = selectTicketId2;
    }

    public void setSelectTicketId3(String selectTicketId3) {
        this.selectTicketId3 = selectTicketId3;
    }

    public void setQuantity4(String quantity4) {
        this.quantity4 = quantity4;
    }

    public void setQuantity5(String quantity5) {
        this.quantity5 = quantity5;
    }

    public void setQuantity6(String quantity6) {
        this.quantity6 = quantity6;
    }

    public void setQ1(int q1) {
        this.q1 = q1;
    }

    public void setQ2(int q2) {
        this.q2 = q2;
    }

    public void setQ3(int q3) {
        this.q3 = q3;
    }

    public void setQ4(int q4) {
        this.q4 = q4;
    }

    public void setQ5(int q5) {
        this.q5 = q5;
    }

    public void setQ6(int q6) {
        this.q6 = q6;
    }

    public AttractionPass getSelectedTicket() {
        return selectedTicket;
    }

    public String getSelectTicketId1() {
        return selectTicketId1;
    }

    public String getSelectTicketId2() {
        return selectTicketId2;
    }

    public String getSelectTicketId3() {
        return selectTicketId3;
    }

    public String getQuantity4() {
        return quantity4;
    }

    public String getQuantity5() {
        return quantity5;
    }

    public String getQuantity6() {
        return quantity6;
    }

    public int getQ1() {
        return q1;
    }

    public int getQ2() {
        return q2;
    }

    public int getQ3() {
        return q3;
    }

    public int getQ4() {
        return q4;
    }

    public int getQ5() {
        return q5;
    }

    public int getQ6() {
        return q6;
    }

    public void setSelectPassId2(String selectPassId2) {
        this.selectPassId2 = selectPassId2;
    }

    public void setSelectPassId3(String selectPassId3) {
        this.selectPassId3 = selectPassId3;
    }

    public void setQuantity1(String quantity1) {
        this.quantity1 = quantity1;
    }

    public void setQuantity2(String quantity2) {
        this.quantity2 = quantity2;
    }

    public void setQuantity3(String quantity3) {
        this.quantity3 = quantity3;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getSelectPassId1() {
        return selectPassId1;
    }

    public String getSelectPassId2() {
        return selectPassId2;
    }

    public String getSelectPassId3() {
        return selectPassId3;
    }

    public String getQuantity1() {
        return quantity1;
    }

    public String getQuantity2() {
        return quantity2;
    }

    public String getQuantity3() {
        return quantity3;
    }

    public AttractionSessionBeanRemote getAsb() {
        return asbr;
    }

    public List<Attraction> getAttList() {
        return attList;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public List<AttractionPass> getAp() {
        return ap;
    }

    public List<AttractionTicket> getAt() {
        return at;
    }

    public AttractionPass getSelectedPass() {
        return selectedPass;
    }

    public void setAttList(List<Attraction> attList) {
        this.attList = attList;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public void setAp(List<AttractionPass> ap) {
        this.ap = ap;
    }

    public void setAt(List<AttractionTicket> at) {
        this.at = at;
    }

    public void setSelectedPass(AttractionPass selectedPass) {
        this.selectedPass = selectedPass;
    }

    public void setAtList(Map<String, String> atList) {
        this.atList = atList;
    }

    public Map<String, String> getAtList() {
        return atList;
    }

    public Date getDoa() {
        return doa;
    }

    public void setDoa(Date doa) {
        this.doa = doa;
    }
}
