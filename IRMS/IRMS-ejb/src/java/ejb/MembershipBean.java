/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.InternalMessage;
import entity.LoyaltyPlan;
import entity.Membership;
import entity.UserAccount;
import entity.UserContact;
import exception.ExistException;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author WU JINGYUN
 */
@Stateless
@LocalBean
public class MembershipBean implements MembershipBeanLocal {
    
    @PersistenceContext
    private EntityManager em;
    @EJB
    //ReceiveMessageBean infoManager = new ReceiveMessageBean();
    private LoyaltyPlanBean lpb;
    private Membership membershipEntity;
    private LoyaltyPlan loyaltyPlanEntity;
    
    public void createMembership(String membershipType, int rewardPoint, int redeemPoint) {
        membershipEntity = new Membership();
        loyaltyPlanEntity = new LoyaltyPlan();
        
        membershipEntity.create(membershipType);
        loyaltyPlanEntity.create(rewardPoint, redeemPoint);
        membershipEntity.setLoyaltyPlan(loyaltyPlanEntity);
        
        em.flush();
        em.persist(membershipEntity);
    }
    
    public void updateMembership(Long membershipId, String membershipType, int rewardPoint, int redeemPoint) throws ExistException {
        Membership mb = em.find(Membership.class, membershipId);
        if (mb == null) {
            throw new ExistException("Membership does not exist!");
        }
        mb.setMembershipType(membershipType);
        loyaltyPlanEntity = new LoyaltyPlan();
        loyaltyPlanEntity = mb.getLoyaltyPlan();
        loyaltyPlanEntity.setRewardPoint(rewardPoint);
        loyaltyPlanEntity.setRedeemPoint(redeemPoint);
        mb.setLoyaltyPlan(loyaltyPlanEntity);
        em.flush();
        em.persist(mb);
    }
    
    public List<Membership> getAllMembership() {
        Query query = em.createQuery("SELECT ms FROM Membership ms");
        return query.getResultList();
    }
}
