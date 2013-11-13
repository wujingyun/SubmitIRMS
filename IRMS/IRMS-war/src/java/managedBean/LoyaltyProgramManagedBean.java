/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;
import ejb.CustomerBeanRemote;
import ejb.EmailSessionBean;
import ejb.LoyaltyPlanBeanRemote;
import entity.Customer;
import entity.Membership;
import entity.PointTrans;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class LoyaltyProgramManagedBean implements Serializable {

    @EJB
    private LoyaltyPlanBeanRemote lpb;
    @EJB
    private CustomerBeanRemote cbb;
      @EJB
    EmailSessionBean emailSessionBean;
    //private List<Membership> MembershipList;
    private Map<String, String> membershipList = new HashMap<String, String>();
    private String rewardPoint;
    private String redeemPoint;
    private String membership;
    private String updateMs;
    private String msg;
    private List<Customer> CustomerList;
    private Customer selectedCustomer;
     private List<PointTrans> TransactionList;
     
   private String marketingTitle;
   private List<String> marketingclsgroup;
   private List<String> marketingMembership;
   private List<String> marketingGender;
   private List<String> marketingAge;
    private List<Customer> marketingCustomerList;
    private List<String> marketingEmailList;
   private String marketingMsg;

     
     
     
    public LoyaltyProgramManagedBean() {
    }

    @PostConstruct
    public void init() {
        membershipList.put("Premier", "Premier");
        membershipList.put("Gold", "Gold");
        membershipList.put("Platinum", "Platinum");
        membershipList.put("Diamond", "Diamond");

        this.redeemPoint = "";
        this.rewardPoint = "";
        
        this.CustomerList = cbb.getCustomerList();
       
    }

    public void handleMembershipChange() {
        if (membership != null && !membership.equals("")) {
            redeemPoint = lpb.getRedeemPoint(membership);
            rewardPoint = lpb.getRewardPoint(membership);
        } else {
            redeemPoint = "";
            rewardPoint = "";
        }
    }

    public void updateRRPlan(ActionEvent event) throws IOException, ExistException {

        try {
            int rewP = Integer.parseInt(rewardPoint);
            int redP = Integer.parseInt(redeemPoint);
            boolean update = lpb.updatePoint(membership, rewP, redP);
            System.out.println("LoyaltyProgramManagedBean --> updateRRPlan============");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LoyaltyPlan update Error", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "LoyaltyPlan update Successfully", ""));
    }
    
    
    
    public void viewContent(ActionEvent event) throws IOException {
        TransactionList=lpb.getPointTransByCID(selectedCustomer.getId());
        TransactionList.size();
        System.err.println("LotaltyProgramManagedBean--> viewContent");
    }

    public void updateMembership(ActionEvent event) throws IOException, ExistException {
        try {
            System.out.println("LoyaltyProgramManagedBean --> updateMembership"+selectedCustomer.getId()+updateMs);
      
            boolean updateMembership = lpb.updateMembership(selectedCustomer.getId(),updateMs );
            this.CustomerList = cbb.getCustomerList();
            } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Membership update Error", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Membership updated Successfully", ""));
    }
    
    
    public void sendMsg(ActionEvent event) throws IOException, ExistException {
        try {
            if(marketingclsgroup==null){  System.out.println("LoyaltyProgramManagedBean --> marketingclsgroupnull)");}
             System.out.println("LoyaltyProgramManagedBean --> sendMsg");
        //     System.out.println("LoyaltyProgramManagedBean --> sendMsg"+marketingclsgroup.size()+marketingMembership.size()+marketingGender.size()+marketingAge.size()+
          //           marketingclsgroup.get(0).toString()+marketingTitle+marketingMsg);
            marketingEmailList=new ArrayList();
        marketingCustomerList=new ArrayList();
           marketingCustomerList= lpb.getMarketingEmailCustomerList(marketingclsgroup,marketingMembership,marketingGender,marketingAge );
            System.out.println("LoyaltyProgramManagedBean --> getMarketingEmailCustomerList"+marketingCustomerList.size());
      for (int i=0;i<marketingCustomerList.size();i++){
      marketingEmailList.add(marketingCustomerList.get(i).getEmail());
      System.out.println("LoyaltyProgramManagedBean --> sendMarketingEmail");
       }
        emailSessionBean.sendMarketingEmail(marketingEmailList,marketingTitle,marketingMsg);
        
        }
            

         catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fail to send email", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email Sent Successfully", ""));
    }


    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public String getUpdateMs() {
        return updateMs;
    }

    public void setUpdateMs(String updateMs) {
        this.updateMs = updateMs;
    }

    public List<PointTrans> getTransactionList() {
        return TransactionList;
    }

    public String getMarketingTitle() {
        return marketingTitle;
    }

    public void setMarketingTitle(String marketingTitle) {
        this.marketingTitle = marketingTitle;
    }

    public List<String> getMarketingclsgroup() {
        return marketingclsgroup;
    }

    public void setMarketingclsgroup(List<String> marketingclsgroup) {
        this.marketingclsgroup = marketingclsgroup;
    }

    public List<String> getMarketingMembership() {
        return marketingMembership;
    }

    public void setMarketingMembership(List<String> marketingMembership) {
        this.marketingMembership = marketingMembership;
    }

    public List<String> getMarketingGender() {
        return marketingGender;
    }

    public void setMarketingGender(List<String> marketingGender) {
        this.marketingGender = marketingGender;
    }

    public List<String> getMarketingAge() {
        return marketingAge;
    }

    public void setMarketingAge(List<String> marketingAge) {
        this.marketingAge = marketingAge;
    }

    public List<Customer> getMarketingCustomerList() {
        return marketingCustomerList;
    }

    public void setMarketingCustomerList(List<Customer> marketingCustomerList) {
        this.marketingCustomerList = marketingCustomerList;
    }

    public List<String> getMarketingEmailList() {
        return marketingEmailList;
    }

    public void setMarketingEmailList(List<String> marketingEmailList) {
        this.marketingEmailList = marketingEmailList;
    }

    public String getMarketingMsg() {
        return marketingMsg;
    }

    public void setMarketingMsg(String marketingMsg) {
        this.marketingMsg = marketingMsg;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void setTransactionList(List<PointTrans> TransactionList) {
        this.TransactionList = TransactionList;
    }
    

    public void setLpb(LoyaltyPlanBeanRemote lpb) {
        this.lpb = lpb;
    }

    public void setMembershipList(Map<String, String> MembershipList) {
        this.membershipList = MembershipList;
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

    public void setCustomerList(List<Customer> CustomerList) {
        this.CustomerList = CustomerList;
    }

    public CustomerBeanRemote getCbb() {
        return cbb;
    }

    public String getMsg() {
        return msg;
    }

    public List<Customer> getCustomerList() {
        return CustomerList;
    }

    public Map<String, String> getMembershipList() {
        return membershipList;
    }

    public String getRewardPoint() {
        return rewardPoint;
    }

    public String getRedeemPoint() {
        return redeemPoint;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getMembership() {
        return membership;
    }
}