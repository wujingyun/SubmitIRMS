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
import java.util.Iterator;
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
    private List<String> units;
  

   // clear , a list of all the units will be passed to the front 
   @Override
   public List<String> DisplayRepartitionMall(String mallName) {
        System.out.println("AddNewUnit:  query for mall : ");
        String ejbql ="SELECT m FROM Mall m WHERE m.mallName =?1";       
        Query q = em.createQuery(ejbql);
        q.setParameter(1, "IRMall");
        System.out.println("AddNewUnit:  query for mall After: ");
        mallEntity = new Mall();
        
        System.out.println("AddNewUnit:  getting result for mall: ");
        mallEntity = (Mall) q.getSingleResult();
        System.out.println("Dispaly Mall Name: " +mallEntity.getMallName());
        unitEntity = new Unit();
        
        units = new ArrayList();
        for(Iterator it =mallEntity.getUnits().iterator();it.hasNext();){
              
             unitEntity = (Unit)it.next();
             System.out.println("Dispaly unit No"+unitEntity.getUnitNo());
             if(unitEntity.isUnitAvailability()==true){
                 units.add(unitEntity.getUnitNo());
             }
         } 
        for (String unit : units) {
            System.out.println("Dispaly unit No : "+unit);
        }
        return units;       
    }
    
    // finished addNewUnit
    @Override
    public void addNewUnit(String unitNo, int unitSpace, String mallName) throws MaxQuotaException {
        
       
        int totalArea = currentUsedSpace()+unitSpace;
        System.out.println("currentUsedSpace After : "+totalArea);

        if (totalArea > maxArea) {
            throw new MaxQuotaException(MaxQuotaException.getINSUFFICIENT_SPACE());
        }        
        unitEntity = new Unit();
        unitEntity.createUnit(unitNo, unitSpace);
        
  
        System.out.println("AddNewUnit:  query for mall : ");
        String ejbql ="SELECT m FROM Mall m WHERE m.mallName =?1";       
        Query q = em.createQuery(ejbql);
        q.setParameter(1, "IRMall");
        System.out.println("AddNewUnit:  query for mall After: ");
        mallEntity = new Mall();
        
        System.out.println("AddNewUnit:  getting result for mall: ");
        mallEntity = (Mall) q.getSingleResult();
        System.out.println("AddNewUnit:  getting size for mall: "+mallEntity.getUnits().size());
     //   System.out.println("AddnewUnit persist After : UnitNo "+unitEntity.getUnitNo()+"Unit space"+unitEntity.getUnitSpace());
        System.out.println("Session Bean: Add new Unit: "+mallEntity.getMallName());
        mallEntity.getUnits().add(unitEntity);
        unitEntity.setMall(mallEntity);
        
        System.out.println("Session Bean: Add new Unit: "+mallEntity.getMallName());
        em.persist(unitEntity);
        em.flush();
    }

    @Override
    public void deleteUnit(String unitNo, String mallName) throws ExistException {
        unitEntity = new Unit();
        unitEntity = em.find(Unit.class, unitNo);

        if (unitEntity.getContract() != null) {
            throw new ExistException("The unit has been taken,contract has not been terminated！");
        }

        String ejbql ="SELECT m FROM Mall m WHERE m.mallName =?1";       
        Query q = em.createQuery(ejbql);
        q.setParameter(1, "IRMall");
        mallEntity = new Mall();
        mallEntity = (Mall) q.getSingleResult();
        
        mallEntity.getUnits().remove(unitEntity);
        em.remove(unitEntity);
        em.flush();
    }
    // cleared
    public int currentUsedSpace() {
        int result = 0;
        unitEntity =new Unit();
        System.out.println("public int currentUsedSpace: "+result);
        Query q = em.createQuery("SELECT u FROM Unit u");
        System.out.println("list size "+ q.getResultList().size());
        for (Object o : q.getResultList()) {
            unitEntity = (Unit) o;
            result += unitEntity.getUnitSpace();
        }
        System.out.println("public int currentUsedSpace After: "+result);
        em.flush();
        return result;
    }
    //cleared
    @Override
    public void createMall(String mallName) {
        mallEntity = new Mall();
        mallEntity.setMallName(mallName);
        mallEntity.setMallSize(maxArea);
        em.persist(mallEntity);
    }
}
