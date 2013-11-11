/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Membership;
import entity.PointTrans;
import exception.ExistException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author WU JINGYUN
 */
@Remote
public interface CIRBeanRemote {
     public double getAvgExpenditure(long customerId);
      public double getMaxClv();
               public double getMinClv();
}
