/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Customer;
import entity.InternalMessage;
import entity.InternalMessageReceive;
import entity.LoyaltyPlan;
import entity.Membership;
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
public class LoyaltyPlanBean implements LoyaltyPlanBeanRemote {

    @PersistenceContext
    private EntityManager em;
    //@EJB
    //CustomerBean cbb = new CustomerBean();
    private Customer customer;
    private Membership membership;
    private LoyaltyPlan loyaltyPlan;
    private List<Membership> membershipList;
    private List<LoyaltyPlan> loyaltyPlanList;
    private String point;
    private List<ShowTicketTrans> showTicketTrans;
    private List<Customer> customerList;

    @Override
    public List<Membership> getMembersihpType() {
        membershipList = new ArrayList();
        String ejbql = "SELECT DISTINCT m FROM Membership m GROUP BY m.membershipType";
        Query q = em.createQuery(ejbql);
        for (Object o : q.getResultList()) {
            Membership t = (Membership) o;
            membershipList.add(t);
        }
        em.flush();
        return membershipList;
    }

    @Override
    public String getRedeemPoint(String membership) {

        Query query = em.createQuery("SELECT DISTINCT m.loyaltyPlan.redeemPoint FROM Membership m where m.membershipType=?1");
        query.setParameter(1, membership);
        membershipList = new ArrayList();
        point = query.getResultList().get(0).toString();


        return point;
    }

    @Override
    public String getRewardPoint(String membership) {

        Query query = em.createQuery("SELECT DISTINCT m.loyaltyPlan.rewardPoint FROM Membership m where m.membershipType=?1");
        query.setParameter(1, membership);
        membershipList = new ArrayList();
        point = query.getResultList().get(0).toString();


        return point;
    }

    @Override
    public boolean updatePoint(String membership, int rewardPoint, int redeemPoint) {

        Query query = em.createQuery("SELECT lp FROM LoyaltyPlan lp, Membership m where lp.id=m.loyaltyPlan.id and m.membershipType=?1");
        query.setParameter(1, membership);
        loyaltyPlanList = new ArrayList();

        for (Object o : query.getResultList()) {
            LoyaltyPlan t = (LoyaltyPlan) o;
            loyaltyPlanList.add(t);
        }
        for (int i = 0; i < loyaltyPlanList.size(); i++) {
            loyaltyPlanList.get(i).setRedeemPoint(redeemPoint);
            loyaltyPlanList.get(i).setRewardPoint(rewardPoint);
            em.persist(loyaltyPlanList.get(i));
        }
        return true;

    }

    @Override
    public boolean updateMembership(long customerId, String membership) throws ExistException {

        Query query = em.createQuery("SELECT c from Customer c where c.Id=?1");
        query.setParameter(1, customerId);

        customer = (Customer) query.getSingleResult();
        if (customer == null) {
            throw new ExistException("customer does not exist!");

        } else {
            customer.getMembership().setMembershipType(membership);
            em.persist(customer);
            return true;
        }
    }

    @Override
    public List<PointTrans> getPointTransByCID(long id) {
        Query query = em.createQuery("SELECT pt FROM PointTrans pt WHERE pt.customer.Id = ?1");
        query.setParameter(1, id);
        List<PointTrans> PointTransactionsRecord = null;
        System.out.println("LoyaltyPlanBean-->getPointTransByCID");
        try {
            PointTransactionsRecord = query.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }

        return PointTransactionsRecord;
    }

    @Override
    public int getPointByCID(long id) {
        List<PointTrans> PointTransactionsRecord = getPointTransByCID(id);
        int totalPoint = 0;
        for (int i = 0; i < PointTransactionsRecord.size(); i++) {
            if (PointTransactionsRecord.get(i).getType().equalsIgnoreCase("reward")) {
                totalPoint = totalPoint + PointTransactionsRecord.get(i).getPoint();
            } else {
                totalPoint = totalPoint - PointTransactionsRecord.get(i).getPoint();
            }
        }
        System.out.println("LoyaltyPlanBean-->getPointByCID");
        //customer.setLoyaltyPointBalance(totalPoint);
        return totalPoint;
    }

    @Override
    public List<ShowTicketTrans> getShowTicketTransByCID(long id) throws ExistException {
        showTicketTrans = new ArrayList();
        Query query = em.createQuery("SELECT c from Customer c where c.Id=?1");
        query.setParameter(1, id);

        customer = (Customer) query.getSingleResult();
        if (customer == null) {
            throw new ExistException("customer does not exist!");

        } else {
            showTicketTrans = (List<ShowTicketTrans>) customer.getShowTicketTrans();
            em.persist(customer);
            return showTicketTrans;
        }
    }

    @Override
    public List<Customer> getMarketingEmailCustomerList(List<String> marketingclsgroup,
            List<String> marketingMembership, List<String> marketingGender, List<String> marketingAge) throws ExistException {
        boolean toAdd = false;
        System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList");
        customerList = new ArrayList();


        //get all customer from that group 
        for (int a = 0; a < marketingclsgroup.size(); a++) {
            System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList-->marketingclsgroup -->" + marketingclsgroup.size());
            Query query = em.createQuery("SELECT DISTINCT c FROM Customer c where c.classificationGroup=?1");
            query.setParameter(1, marketingclsgroup.get(a));

            for (Object o : query.getResultList()) {
                Customer c = (Customer) o;
                customerList.add(c);
            }
            System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList-->Customer List after add -->" + customerList.size());
        }

        //gget all customer from that membership 
        for (int b = 0; b < marketingMembership.size(); b++) {
            System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList-->marketingMembership -->" + marketingMembership.size());
            Query query2 = em.createQuery("SELECT DISTINCT c FROM Customer c where c.membership.membershipType=?1");
            query2.setParameter(1, marketingMembership.get(b));

            for (Object o : query2.getResultList()) {
                Customer c2 = (Customer) o;
                // check against existing list if customer already exsits in the mailing list
                if (customerList.size() == 0) {
                    customerList.add(c2);
                } else {
                    for (int i = 0; i < customerList.size(); i++) {
                        if (customerList.get(i).getUserName().equals(c2.getUserName())) {
                           toAdd = false; 
                           break;
                        } else {
                           toAdd = true;
                        }
                    }
                    
                }if (toAdd == true) {
                        customerList.add(c2);
                    }
            }
            System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList-->Customer List after add -->" + customerList.size());
        }

        //get all customer who is that gender
        for (int c = 0; c < marketingGender.size(); c++) {
            System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList-->marketingGender -->" + marketingGender.size());
            Query query3 = em.createQuery("SELECT DISTINCT c FROM Customer c where c.gender=?1");
            query3.setParameter(1, marketingGender.get(c));

            for (Object o : query3.getResultList()) {
                Customer c3 = (Customer) o;
                // check against existing list if customer already exsits in the mailing list
                if (customerList.size() == 0) {
                    customerList.add(c3);
                } else {
                    for (int j = 0; j < customerList.size(); j++) {
                        System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList-->marketingGender -->compare" + customerList.get(j).getUserName() + c3.getUserName());
                        if (customerList.get(j).getUserName().equals(c3.getUserName()) ) {
                            toAdd = false; 
                           break;
                        } else {
                            toAdd = true;
                        }
                    }
                  
                }  if (toAdd == true) {
                        customerList.add(c3);
                    }
                System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList-->Customer List after add -->" + customerList.size());

            }
        }


        //get all customer belong to the selected age group 
        for (int d = 0; d < marketingAge.size(); d++) {
            System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList-->marketingAge -->" + marketingAge.size());
            Query query4 = em.createQuery("SELECT DISTINCT c FROM Customer c where c.ageGroup=?1");
            query4.setParameter(1, marketingAge.get(d));

            for (Object o : query4.getResultList()) {
                Customer c4 = (Customer) o;
                // check against existing list if customer already exsits in the mailing list
                if (customerList.size() == 0) {
                    customerList.add(c4);
                } else {
                    for (int k = 0; k < customerList.size(); k++) {
                        if (customerList.get(k).getUserName().equals(c4.getUserName())) {
                            toAdd = false; 
                           break;
                        } else {
                            toAdd = true;
                        }
                    }
                   
                } if (toAdd == true) {
                        customerList.add(c4);
                    }

            }
            System.out.println("LoyaltyPlanBean-->getMarketingEmailCustomerList-->Customer List after add -->" + customerList.size());
        }

        return customerList;
    }
}
