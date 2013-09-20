/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.UserAccount;
import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author WU JINGYUN
 */
@Remote
public interface AdminUserBeanRemote {
    public boolean verifyPassword(String userName, String password);
    public void register(String name, long role, String pw, String division,Boolean active, String phone_no, String email);
    public void terminate(long userId) ;
   public UserAccount findUser(String username) ;
    public void activateAcct(long userId) ;
    public void deactivateAcct(long userId) ;
}
