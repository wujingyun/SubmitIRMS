/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author WU JINGYUN
 */
@Local
public interface EmailSessionBeanLocal {
    public void emailInitialPassward(String toEmailAdress, String initialPassword);
     public void emailRestaurantResConfirm(String contactInfo, String customerName, String fbRName,Date attendDate, String selectPeriod ) ;
       public void sendMarketingEmail(List<String> arketingEmailList, String marketingTitle,String marketingMsg) ;
}
