/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.AttractionPassTrans;
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
import java.util.Collections;
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
        private List<Customer> customerList;
    private OnlinePayment onlinePayment;
       private DirectPayment directPayment;
    private List<AttractionTicketTrans> attractionTicketTrans;
       private List<AttractionPassTrans> attractionPassTrans;
     private List<ShowTicketTrans> showTicketTrans;
      private PackageTrans packageTrans;
       private List<Double> clvList;
    private double totalExpenditure;
    private double numberOfVisit;
    private double compare=0;
    @Override
    public double getAvgExpenditure(long customerId) {
        totalExpenditure = 0;
        numberOfVisit = 0;
     
        Query query = em.createQuery( "select c from Customer c where c.Id=?1");
                /*+ "select op from OnlinePayment op, AttractionTicketTrans a, ShowTicketTrans s,"
                + " PackageTrans p where op.paymentStatus='paid' and a.onlinePayment.id=op.id and "
                + "s.onlinePayment.id=op.id and p.onlinePayment.id=op.id*/
         query.setParameter(1, customerId);
      customer= (Customer) query.getSingleResult();
        totalExpenditure = customer.getTotalAmountSpend();
        System.out.println("CIRBean --> getAvgExpenditure"+totalExpenditure);
        Query query2 = em.createQuery( "select a from AttractionPassTrans a where a.customer.Id=?1");
         query2.setParameter(1, customerId);
      attractionPassTrans=query2.getResultList();
            numberOfVisit = attractionPassTrans.size();
            System.out.println("CIRBean --> getAvgExpenditure1"+numberOfVisit);
            
             Query query3 = em.createQuery( "select at from AttractionTicketTrans at where at.customer.Id=?1");
         query3.setParameter(1, customerId);
      attractionTicketTrans=query3.getResultList();
            numberOfVisit = numberOfVisit+attractionTicketTrans.size();
            System.out.println("CIRBean --> getAvgExpenditure2"+numberOfVisit);
             Query query4 = em.createQuery( "select s from ShowTicketTrans s where s.customer.Id=?1");
         query4.setParameter(1, customerId);
      showTicketTrans=query4.getResultList();
            numberOfVisit = numberOfVisit+showTicketTrans.size();
        System.out.println("CIRBean --> getAvgExpenditure3"+numberOfVisit);
      if((numberOfVisit==compare)||(totalExpenditure==compare)){
          return 0.0; }
      else{  return totalExpenditure/numberOfVisit;}
    }
    

      @Override
    public void setCustomerClv(Long customerId,double CLV) throws ExistException {
         Query query = em.createQuery( "select c from Customer c where c.Id=?1");
         query.setParameter(1, customerId);
        if(query.getResultList().size()==0)
        {throw new ExistException("No customer find!");}
         customer= (Customer) query.getSingleResult();
        
        customer.setClv(CLV);
     }
          @Override
    public void setCustomerClvGroup(Long customerId,String group) throws ExistException {
         Query query = em.createQuery( "select c from Customer c where c.Id=?1");
         query.setParameter(1, customerId);
        if(query.getResultList().size()==0)
        {throw new ExistException("No customer find!");}
         customer= (Customer) query.getSingleResult();
        
        customer.setClassificationGroup(group);
     }
     
     @Override
    public double getMaxClv() {
           
         clvList=new ArrayList();System.out.println("CIRBean --> getMaxClv");
         Query query = em.createQuery( "select c from Customer c");
          System.out.println("CIRBean --> getMaxClv2");
         customerList= query.getResultList();
       
        for (int i=0;i<customerList.size();i++)
        { clvList.add(customerList.get(i).getClv());}
        double MaxClv= Collections.max(clvList);
        System.out.println("CIRBean --> getMaxClv"+MaxClv);
        return MaxClv;
     }
     
       @Override
    public double getMinClv() {
         clvList=new ArrayList();System.out.println("CIRBean --> getMinClv");
         Query query = em.createQuery( "select c from Customer c");
          System.out.println("CIRBean --> getMinClv");
         customerList= query.getResultList();
       
        for (int i=0;i<customerList.size();i++)
        { clvList.add(customerList.get(i).getClv());}
        double MinClv= Collections.min(clvList);
        System.out.println("CIRBean --> MinClv"+MinClv);
        return MinClv;
     }
     
    
}
