/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Customer;
import entity.Membership;
import entity.PointTrans;
import entity.ShowTicketTrans;
import exception.ExistException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author WU JINGYUN
 */
@Remote
public interface LoyaltyPlanBeanRemote {
      public List<Membership> getMembersihpType ();
     public boolean updatePoint(String membership, int rewardPoint, int redeemPoint);
       public String getRedeemPoint(String membership);
          public String getRewardPoint(String membership);
           public boolean updateMembership(long customerId, String membership) throws ExistException;
       public List<PointTrans> getPointTransByCID(long id) ;
       public int getPointByCID(long id) ;
       public List<ShowTicketTrans>  getShowTicketTransByCID(long id) throws ExistException;
       
           public List<Customer> getMarketingEmailCustomerList(List <String> marketingclsgroup,
    List <String> marketingMembership,List <String> marketingGender,List <String> marketingAge )throws ExistException;
}
