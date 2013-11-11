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
import entity.PointTrans;
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
public class CIRBean implements CIRBeanRemote {

    @PersistenceContext
    private EntityManager em;
    //@EJB
    //CustomerBean cbb = new CustomerBean();
    private Customer customer;
    private OnlinePayment onlinePayment;
       private DirectPayment directPayment;
    private AttractionTicketTrans attractionTicketTrans;
     private ShowTicketTrans showTicketTrans;
      private PackageTrans packageTrans;
       
    private List<OnlinePayment> onlinePaymentList;
    private List<DirectPayment> directPaymentList;
    private double totalExpenditure;
    private double numberOfVisit;
    
    @Override
    public double getAvgExpenditure(long customerId) {
        totalExpenditure = 0;
        numberOfVisit = 0;
        onlinePaymentList = new ArrayList();
        directPaymentList = new ArrayList();
        Query query = em.createQuery( "select op from OnlinePayment op, AttractionTicketTrans a, ShowTicketTrans s,"
                + " PackageTrans p where op.paymentStatus='paid' and a.onlinePayment.id=op.id and "
                + "s.onlinePayment.id=op.id and p.onlinePayment.id=op.id");
         query.setParameter(1, customerId);
      
        for (Object o : query.getResultList()) {
            OnlinePayment t = (OnlinePayment) o;
            numberOfVisit = numberOfVisit + 1;
            totalExpenditure = totalExpenditure + t.getPaymentAmount();
        }
         Query query2 = em.createQuery( "SELECT  dp FROM DirectPayment dp, AttractionTicketTrans a, ShowTicketTrans s,"
                + " PackageTrans p where dp.paymentStatus='paid' and a.onlinePayment.id=dp.id and "
                + "s.onlinePayment.id=dp.id and p.onlinePayment.id=dp.id");
         query2.setParameter(1, customerId);
        for (Object o2 : query2.getResultList()) {
            DirectPayment t2 = (DirectPayment) o2;
            numberOfVisit = numberOfVisit + 1;
            totalExpenditure = totalExpenditure + t2.getPaymentAmount();
        }
        return totalExpenditure/numberOfVisit;
    }
    
    
     @Override
    public double getMaxClv() {
         Query query = em.createQuery( "select MAX(c.clv) from Customer c GROUP BY c.clv");
        
         customer= (Customer) query.getSingleResult();
        double MaxClv=customer.getClv();
        return MaxClv;
     }
     
       @Override
    public double getMinClv() {
         Query query = em.createQuery( "select MIN(c.clv) from Customer c GROUP BY c.clv");
        
         customer= (Customer) query.getSingleResult();
        double MinClv=customer.getClv();
        return MinClv;
     }
    
}
