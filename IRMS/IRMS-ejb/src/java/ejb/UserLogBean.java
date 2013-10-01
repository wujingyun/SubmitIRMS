/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;
import exception.ExistException;
import entity.UserAccount;
import entity.UserContact;
import entity.UserLog;
import entity.UserRole;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author WU JINGYUN
 */
@Stateless
public class UserLogBean implements UserLogBeanRemote {
@PersistenceContext()
    EntityManager em;
    UserAccount ua;
      UserLog ul;
    private UserAccount user;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Override
public void addLog(long userId, String userName, String description){
      
        user = em.find(UserAccount.class, userId);
         if (user==null)   {System.out.println("no such user===================");}
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
 
        try {
            
            String sendTime = dateFormat.format(calendar.getTime());
            ul.create(sendTime, userName, description);
            } catch (Exception e) {
            e.printStackTrace();
        }
       
        //contact = new UserContact();
       List <UserLog> logList =(List <UserLog>) user.getUserLog();
       
       logList.add(ul);
       user.setUserLog(logList);
        em.flush();//
        em.persist(user);//persist
           System.out.println( " log added =========");
      
    }
  

}
