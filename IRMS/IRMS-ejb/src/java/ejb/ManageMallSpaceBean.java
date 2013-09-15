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
    int maxArea = 20000;

  

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
        unitEntity = new Unit();
        int totalArea = currentUsedSpace();

        if (totalArea > maxArea) {
            throw new MaxQuotaException(MaxQuotaException.getINSUFFICIENT_SPACE());
        }

        unitEntity.createUnit(unitNo, unitSpace);

        em.persist(unitEntity);

        mallEntity = new Mall();

        Query q = em.createQuery("SELECT * FROM mall WHERE mallName = : "
                + "mname");
        q.setParameter("mname", mallName);
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
        Query q = em.createQuery("SELECT * FROM unit u");
        for (Object o : q.getResultList()) {
            unitEntity = (Unit) o;
            result += unitEntity.getUnitSpace();
        }
        return result;
    }
    
    @Override
    public void createMall(String mallName) {
        mallEntity = new Mall();
        mallEntity.setMallName(mallName);
    }
}
