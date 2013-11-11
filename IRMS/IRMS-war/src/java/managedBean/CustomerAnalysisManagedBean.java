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
                Customer = CustomerList.get(i);
               // avgExpenditure = cirbb.getAvgExpenditure(Customer.getId());
                avgExpenditure=100;
                visit=10;
                if (Customer.getMembership().getMembershipType().equalsIgnoreCase("Premier")) {
                    life = 12;
                    acquisition = 100;
                } else if (Customer.getMembership().getMembershipType().equalsIgnoreCase("Gold")) {
                    life = 24;
                    acquisition = 200;
                } else if (Customer.getMembership().getMembershipType().equalsIgnoreCase("Platinum")) {
                    life = 36;
                    acquisition = 300;
                } else if (Customer.getMembership().getMembershipType().equalsIgnoreCase("Diamond")) {
                    life = 48;
                    acquisition = 400;
                }

                CLV = avgExpenditure * life * visit - acquisition;
            }
            Customer.setClv(CLV);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Calculation Error", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Calculated Successfully", ""));
    }

    public void customerClassification(ActionEvent event) throws IOException, ExistException {
        try {
            System.out.println("CustomerAnalysisManagedBean --> customerClassification");
            CustomerList = cbb.getCustomerList();
            Maxclv = cirbb.getMaxClv();
            Minclv = cirbb.getMinClv();
            double g2up = (4 / 5) * Maxclv + (1 / 5) * Minclv;
            double g3up = (3 / 5) * Maxclv + (2 / 5) * Minclv;
            double g4up = (2 / 5) * Maxclv + (3 / 5) * Minclv;
            double g5up = (1 / 5) * Maxclv + (4 / 5) * Minclv;
            for (int i = 0; i < CustomerList.size(); i++) {
                Customer = CustomerList.get(i);
                CLV=Customer.getClv();
                if(CLV>g2up)Customer.setClassificationGroup("Group1");
                if((CLV>g3up) && (CLV<=g2up))Customer.setClassificationGroup("Group2");
                if((CLV>g4up)&& (CLV<=g3up))Customer.setClassificationGroup("Group3");
                if((CLV>g5up)&& (CLV<=g4up))Customer.setClassificationGroup("Group4");
                if(CLV<=g5up)Customer.setClassificationGroup("Group5");
            }
            

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Classification Error", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Classification Successfully", ""));
    }
}