/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author WU JINGYUN
 */
@Remote
public interface AdminUserBeanRemote {
    public boolean verifyPassword(String userName, String password);
    public void register(String name, Collection role, String pw, String department, String phone_no, String email);
    public void terminate(long userId) ;
}
