/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Mall;
import exception.ExistException;
import exception.MaxQuotaException;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface ManageMallSpaceBeanRemote {
   public Mall DisplayRepartitionMall(String mallName);
   public void addNewUnit(String unitNo, int unitSpace, String mallName) throws MaxQuotaException;
   public void deleteUnit(String unitNo, String mallName) throws ExistException;
   public void createMall(String mallName);
}
