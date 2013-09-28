/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.UserAccount;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
   public boolean checkUserExist(String username);
    public void activateAcct(String userName) ;
    public void deactivateAcct(String userName) ;
public String getUserRole(String username) ;
    public String hashPassword(String password);

  
    public String hashPassword2(long uid, String password);

    public List<UserAccount> getAccountByDivisionToA(String division);

    public List<UserAccount> getAccountByDivisionToDA(String division);

    public void updateLoginAttemp(String userName);

    public int getLoginAttemp(String userName);

    public boolean checkLockOut(String userName);

    public void setLoginAttempToZero(String userName);

    public void updateLoginAttempTime(String userName);

    public boolean setHashPassword(String userName, String password);

  
}
