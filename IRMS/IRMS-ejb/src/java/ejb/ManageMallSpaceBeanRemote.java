/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Mall;
import entity.Unit;
import exception.ExistException;
import exception.MaxQuotaException;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface ManageMallSpaceBeanRemote {
   public List<String> DisplayRepartitionMall(String mallName);
   public void addNewUnit(String unitNo, int unitSpace, String mallName) throws MaxQuotaException;
   public void deleteUnit(List selectedUnits) throws ExistException ;
   public void createMall(String mallName);
     public void getMall(double CommisionRate);
      public double getMallRate();
       public List<Unit> getUnitList();
}
