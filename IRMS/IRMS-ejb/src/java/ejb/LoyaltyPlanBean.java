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
   private  List<ShowTicketTrans> showTicketTrans;

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
    public List<ShowTicketTrans>  getShowTicketTransByCID(long id) throws ExistException {
      showTicketTrans = new ArrayList();
        Query query = em.createQuery("SELECT c from Customer c where c.Id=?1");
        query.setParameter(1, id);

        customer = (Customer) query.getSingleResult();
        if (customer == null) {
            throw new ExistException("customer does not exist!");

        } else {
            showTicketTrans=(List<ShowTicketTrans>) customer.getShowTicketTrans();
            em.persist(customer);
            return showTicketTrans;
        }
        }
}
