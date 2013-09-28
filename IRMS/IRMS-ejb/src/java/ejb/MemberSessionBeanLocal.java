package ejb;
import entity.Customer;
import javax.ejb.Local;
@Local
public interface MemberSessionBeanLocal
{
    public boolean verifyMember(String userName,String password);
    public boolean registerMember(String email,String password, String userName);
    public boolean check(String memId, String email);
    public void resetPassword(String password, String email);
}