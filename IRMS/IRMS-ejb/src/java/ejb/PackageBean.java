/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.AttractionTicketTrans;
import entity.Customer;
import entity.DirectPayment;
import entity.LoyaltyPlan;
import entity.Membership;
import entity.OnlinePayment;
import entity.PackageTrans;
import entity.Package;
import entity.ShowTicketTrans;
import exception.ExistException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author WU JINGYUN
 */
@Stateless
public class PackageBean implements PackageBeanRemote {

    @PersistenceContext
    private EntityManager em;
    //@EJB
    //CustomerBean cbb = new CustomerBean();
    private Package packages;
   
    @Override
    public void getAvgExpenditure(long customerId) {
     
     }
    
}
