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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private List<Unit> unitList;

    // clear , a list of all the units will be passed to the front 
    @Override
    public List<String> DisplayRepartitionMall(String mallName) {
        System.out.println("AddNewUnit:  query for mall : ");
        String ejbql = "SELECT m FROM Mall m WHERE m.mallName =?1";
        Query q = em.createQuery(ejbql);
        q.setParameter(1, "IRMall");
        System.out.println("AddNewUnit:  query for mall After: ");
        mallEntity = new Mall();

        System.out.println("AddNewUnit:  getting result for mall: ");
        mallEntity = (Mall) q.getSingleResult();
        System.out.println("Dispaly Mall Name: " + mallEntity.getMallName());
        unitEntity = new Unit();

        units = new ArrayList();
        for (Iterator it = mallEntity.getUnits().iterator(); it.hasNext();) {

            unitEntity = (Unit) it.next();
            System.out.println("Dispaly unit No" + unitEntity.getUnitNo());
            if (unitEntity.isUnitAvailability() == true) {
                units.add(unitEntity.getUnitNo());
            }
        }
        for (String unit : units) {
            System.out.println("Dispaly unit No : " + unit);
        }
        return units;
    }
    
    @Override
    public List<Unit> getUnitList(){
        String ejbql = "SELECT u FROM Unit u";
        Query q = em.createQuery(ejbql);
        unitList= new ArrayList();
        System.err.println("getting units"+unitList.size());
        for(Object o: q.getResultList()){
             Unit u = (Unit)o;
             unitList.add(u);
        }
        return unitList;
    }

    @Override
    public void getMall(double CommisionRate) {
        mallEntity = new Mall();
        
        String ejbql = "SELECT m FROM Mall m WHERE m.mallName =?1";
        Query q = em.createQuery(ejbql);
        q.setParameter(1, "IRMall");
        mallEntity = (Mall) q.getSingleResult();
        System.err.println("session bean : mall Name "+mallEntity.getMallName());
        mallEntity.setCommissionRate(CommisionRate);
        em.flush();    
    }
     @Override
    public double getMallRate(){
        mallEntity = new Mall();       
        String ejbql = "SELECT m FROM Mall m WHERE m.mallName =?1";
        Query q = em.createQuery(ejbql);
        q.setParameter(1, "IRMall");
        mallEntity = (Mall) q.getSingleResult();
        double rate = mallEntity.getCommissionRate();
        System.err.println("session bean : mall Name "+mallEntity.getMallName());
        em.flush();
        return rate;
    }
     

    // finished addNewUnit
    @Override
    public void addNewUnit(String unitNo, int unitSpace, String mallName) throws MaxQuotaException {


        int totalArea = currentUsedSpace() + unitSpace;
        System.out.println("currentUsedSpace After : " + totalArea);

        if (totalArea > maxArea) {
            throw new MaxQuotaException(MaxQuotaException.getINSUFFICIENT_SPACE());
        }
        unitEntity = new Unit();
        unitEntity.createUnit(unitNo, unitSpace);


        System.out.println("AddNewUnit:  query for mall : ");
        String ejbql = "SELECT m FROM Mall m WHERE m.mallName =?1";
        Query q = em.createQuery(ejbql);
        q.setParameter(1, "IRMall");
        System.out.println("AddNewUnit:  query for mall After: ");
        mallEntity = new Mall();

        System.out.println("AddNewUnit:  getting result for mall: ");
        mallEntity = (Mall) q.getSingleResult();
        System.out.println("AddNewUnit:  getting size for mall: " + mallEntity.getUnits().size());
        //   System.out.println("AddnewUnit persist After : UnitNo "+unitEntity.getUnitNo()+"Unit space"+unitEntity.getUnitSpace());
        System.out.println("Session Bean: Add new Unit: " + mallEntity.getMallName());
        mallEntity.getUnits().add(unitEntity);
        unitEntity.setMall(mallEntity);

        System.out.println("Session Bean: Add new Unit: " + mallEntity.getMallID());
        em.persist(unitEntity);
        em.flush();
    }

    @Override
    public void deleteUnit(List selectedUnits) throws ExistException {

        String ejbql = "SELECT m FROM Mall m";
        Query q = em.createQuery(ejbql);
        mallEntity = new Mall();
        mallEntity = (Mall) q.getSingleResult();

        for (Iterator it = selectedUnits.iterator(); it.hasNext();) {
            unitEntity = new Unit();
            String result = (String) it.next();
            unitEntity = em.find(Unit.class, result);
            if (unitEntity.getContract() != null) {
                System.out.println("contract ID" + unitEntity.getContract().getId());
                throw new ExistException("The unit has been taken,contract has not been terminated！");
            }

            mallEntity.getUnits().remove(unitEntity);
            em.remove(unitEntity);

        }


        em.flush();
    }
    // cleared

    public int currentUsedSpace() {
        int result = 0;
        unitEntity = new Unit();
        System.out.println("public int currentUsedSpace: " + result);
        Query q = em.createQuery("SELECT u FROM Unit u");
        System.out.println("list size " + q.getResultList().size());
        for (Object o : q.getResultList()) {
            unitEntity = (Unit) o;
            result += unitEntity.getUnitSpace();
        }
        System.out.println("public int currentUsedSpace After: " + result);
        em.flush();
        return result;
    }
    //cleared

    @Override
    public void createMall(String mallName) {
        mallEntity = new Mall();
        Query q = em.createQuery("SELECT m FROM Mall m");
        for (Object o : q.getResultList()) {
            mallEntity = (Mall) o;
            /*    if(mallEntity.getMallName().equals(mallName)) try {
             throw new ExistException("The contract does not exist!");
             } catch (ExistException ex) {
             Logger.getLogger(ManageMallSpaceBean.class.getName()).log(Level.SEVERE, null, ex);
             } */
        }
        mallEntity.setMallName(mallName);
        mallEntity.setMallSize(maxArea);
        em.persist(mallEntity);
    }
}
