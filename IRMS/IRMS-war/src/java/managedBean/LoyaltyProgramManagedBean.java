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