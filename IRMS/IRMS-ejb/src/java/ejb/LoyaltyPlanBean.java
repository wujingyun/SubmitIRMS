/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.InternalMessage;
import entity.InternalMessageReceive;
import entity.PointTrans;
import java.util.ArrayList;
import java.util.List;
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
@LocalBean
public class LoyaltyPlanBean implements LoyaltyPlanBeanLocal {

    @PersistenceContext
    private EntityManager em;

    public List<PointTrans> getPointTransByCID(long id) {
        Query query = em.createQuery("SELECT pt FROM PointTrans pt WHERE pt.customerId = ?1");
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
        return totalPoint;
    }
}
