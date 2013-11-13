/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.CIRBeanRemote;
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
import java.util.Iterator;
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
public class CustomerAnalysisManagedBean implements Serializable {

    @EJB
    private CIRBeanRemote cirbb;
    @EJB
    private CustomerBeanRemote cbb;
    //private List<Membership> MembershipList;
    private Map<String, String> membershipList = new HashMap<String, String>();
    private double avgExpenditure;
    private String redeemPoint;
    private String membership;
    private String updateMs;
    private String msg;
    private List<Customer> CustomerList;
    private Customer Customer;
    private List<PointTrans> TransactionList;
    private double CLV;
    private int life;
    private int visit = 12;
    private int GPM;
    private int acquisition;
    private double Maxclv;
    private double Minclv;

    public CustomerAnalysisManagedBean() {
    }
//CLV= Customer life expectancy (in month)* Average expenditure per visit*
    //Average visit per month* Gross profit margin– Average acquisition cost – bad debt

    @PostConstruct
    public void init() {
    }

    public void calculateCLV(ActionEvent event) throws IOException, ExistException {
        try {
            System.out.println("CustomerAnalysisManagedBean --> calculateCLV");
            CustomerList = cbb.getCustomerList();
            for (int i = 0; i < CustomerList.size(); i++) {
                Customer=new Customer();
                Customer = CustomerList.get(i);
                System.out.println("CustomerAnalysisManagedBean --> calculateCLV"+Customer.getId());
                avgExpenditure = cirbb.getAvgExpenditure(Customer.getId());
                System.out.println("CustomerAnalysisManagedBean --> calculateCLV"+avgExpenditure);
                acquisition=0;
                visit=10;
                if (Customer.getMembership().getMembershipType().equalsIgnoreCase("Premier")) {
                    life = 12;
                  
                } else if (Customer.getMembership().getMembershipType().equalsIgnoreCase("Gold")) {
                    life = 24;
                   
                } else if (Customer.getMembership().getMembershipType().equalsIgnoreCase("Platinum")) {
                    life = 36;
                  
                } else if (Customer.getMembership().getMembershipType().equalsIgnoreCase("Diamond")) {
                    life = 48;
                    
                }

                CLV = avgExpenditure * life * visit - acquisition; 
                System.out.println("CustomerAnalysisManagedBean --> calculateCLV"+CLV+Customer.getUserName());
                cirbb.setCustomerClv(Customer.getId(), CLV);
            }
           

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Calculation Error", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Calculated Successfully", ""));
          //  FacesContext.getCurrentInstance().getExternalContext().redirect("crmAdminViewCustomerAnalysisResult.xhtml");   
    }

    public void customerClassification(ActionEvent event) throws IOException, ExistException {
        try {
            System.out.println("CustomerAnalysisManagedBean --> customerClassification");
            CustomerList = cbb.getCustomerList();
            Maxclv = cirbb.getMaxClv();
            Minclv = cirbb.getMinClv();
            double g2up = 0.8 * Maxclv + 0.2 * Minclv;
            double g3up = 0.6 * Maxclv + 0.4 * Minclv;
            double g4up = 0.4 * Maxclv + 0.6 * Minclv;
            double g5up = 0.2 * Maxclv + 0.8 * Minclv;
            System.out.println("CustomerAnalysisManagedBean --> customerClassification"+g2up+g3up+g4up+g5up);
            
            for (int i = 0; i < CustomerList.size(); i++) {
                Customer = CustomerList.get(i);
                CLV=Customer.getClv();
                System.out.println("CustomerAnalysisManagedBean --> customerClassification"+CLV);
                if(CLV>g2up) cirbb.setCustomerClvGroup(Customer.getId(), "Group1");
                   
                if((CLV>g3up) && (CLV<=g2up))cirbb.setCustomerClvGroup(Customer.getId(), "Group2");
                if((CLV>g4up)&& (CLV<=g3up))cirbb.setCustomerClvGroup(Customer.getId(), "Group3");
                if((CLV>g5up)&& (CLV<=g4up))cirbb.setCustomerClvGroup(Customer.getId(), "Group4");
                if(CLV<=g5up)cirbb.setCustomerClvGroup(Customer.getId(), "Group5");
               
               // System.out.println("CustomerAnalysisManagedBean --> customerClassification"+Customer.getClassificationGroup());
            }
            

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Classification Error", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Classification Successfully", ""));
    }
}