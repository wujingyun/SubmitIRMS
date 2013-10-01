/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.UserAccount;
import exception.ExistException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author WU JINGYUN
 */
@Remote
public interface UserLogBeanRemote {

    public void addLog(long userId, String userName, String description);
    
  
}
