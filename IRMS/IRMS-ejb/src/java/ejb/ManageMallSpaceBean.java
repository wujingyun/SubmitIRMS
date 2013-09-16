/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Mall;
import entity.Unit;
import exception.ExistException;
import exception.MaxQuotaException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class ManageMallSpaceBean implements ManageMallSpaceBeanRemote {

    @PersistenceContext()
    EntityManager em;
    Unit unitEntity;
    Mall mallEntity;
    int maxArea = 200000;

  

    @Override
    public Mall DisplayRepartitionMall(String mallName) {
        mallEntity = new Mall();
        Query q=em.createQuery("SELECT * FROM mall WHERE mallName = : mName");
        q.setParameter("mName", mallName);
        mallEntity =(Mall)q.getSingleResult();

        return mallEntity;       
    }
    

    @Override
    public void addNewUnit(String unitNo, int unitSpace, String mallName) throws MaxQuotaException {
        System.out.println("public void unitNo:"+unitNo+" unitspace: "+ unitSpace+ " mallName: "+mallName);
        unitEntity = new Unit();
        System.out.println("currentUsedSpace:");
        int totalArea = currentUsedSpace();
        System.out.println("currentUsedSpace After : "+totalArea);

        if (totalArea > maxArea) {
            throw new MaxQuotaException(MaxQuotaException.getINSUFFICIENT_SPACE());
        }

        unitEntity.createUnit(unitNo, unitSpace);
        System.out.println("createUnit: "+unitNo+" UnitSpace");

        em.persist(unitEntity);
        System.out.println("currentUsedSpace After : ");

        mallEntity = new Mall();
        System.out.println("AddNewUnit:  query for mall : ");
        /*Query q = em.createQuery("SELECT m FROM mall WHERE mallName = : "
                + "mname");*/
        Query q = em.createQuery("SELECT * FROM mall_name m");
        
        mallEntity = (Mall) q.getSingleResult();

        mallEntity.getUnits().add(unitEntity);
        em.refresh(mallEntity);
        em.flush();
    }

    @Override
    public void deleteUnit(String unitNo, String mallName) throws ExistException {
        unitEntity = new Unit();
        unitEntity = em.find(Unit.class, unitNo);

        if (unitEntity.getContract() != null) {
            throw new ExistException("The unit has been taken,contract has not been terminated！");
        }

        Query q = em.createQuery("SELECT * FROM mall WHERE mallName = : "
                + "mname");
        q.setParameter("mname", mallName);
        mallEntity = new Mall();
        mallEntity = (Mall) q.getSingleResult();
        
        mallEntity.getUnits().remove(unitEntity);
        em.remove(unitEntity);
        em.flush();
    }

    public int currentUsedSpace() {
        int result = 0;
         System.out.println("public int currentUsedSpace: "+result);
        Query q = em.createNativeQuery("SELECT * FROM unit u");
        System.out.println("list size "+ q.getResultList().size());
        for (Object o : q.getResultList()) {
            unitEntity = (Unit) o;
            result += unitEntity.getUnitSpace();
        }
        System.out.println("public int currentUsedSpace After: "+result);
        return result;
    }
    
    @Override
    public void createMall(String mallName) {
        mallEntity = new Mall();
        mallEntity.setMallName(mallName);
        mallEntity.setMallSize(maxArea);
        em.persist(mallEntity);
    }
}
