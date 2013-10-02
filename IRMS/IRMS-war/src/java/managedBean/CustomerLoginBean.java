/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import java.io.Serializable;
import ejb.AdminUserBeanRemote;
import ejb.CustomerBeanRemote;
import entity.Customer;
import entity.AccessRight;
import entity.UserAccount;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class CustomerLoginBean implements Serializable {

    //   @EJB
    //AdminUserBeanRemote aub;
    @EJB
    CustomerBeanRemote cbb;
    private Customer customer;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private boolean active;
    private String activateAccountName;
    private String deactivateAccountName;
    private String UserExist = "";
    private String confirmpassword;
    private String match;
    private Map<String, String> ageGroups = new HashMap<String, String>();
    private String ageGroup;
    private String gender;

    public CustomerLoginBean() {

        ageGroups.put("0-18", "1");
        ageGroups.put("18-25", "2");
        ageGroups.put("25-35", "3");
        ageGroups.put("35-45", "4");
        ageGroups.put("45-55", "5");
        ageGroups.put("55-65", "6");
        ageGroups.put("others", "7");

    }

    public String login(ActionEvent actionEvent) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirct;

        System.out.println("username is " + username + " password is " + password);
        String hashPassword = cbb.hashPassword(password);
        //if attmep number lesser than 5, allow login
        //if attemp number larger than 5 and account has locked, only allow login after 5 mins .  

        //attemp number lesser than 5 or it's 5 mins after last attemp, allow login
        if ((cbb.getLoginAttemp(username) <= 5) || (cbb.checkLockOut(username) == true)) {
            System.out.println("login number============" + cbb.getLoginAttemp(username));
            //have to reset attemp number to 0 and update attemp time(in the case where account is unlocked
            //, otherwise, if login fails again, the account will be locked for another 5 min
            if (cbb.checkLockOut(username)) {
                cbb.setLoginAttempToZero(username);
                cbb.updateLoginAttempTime(username);
            }
            //auth
            if (username != null && password != null && cbb.verifyPassword(username, hashPassword)) {
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
             
                customer = cbb.findCustomer(username);
                cbb.setLoginAttempToZero(username);
                cbb.updateLoginAttempTime(username);
                System.out.println("get role ============");
                request.getSession().setAttribute("role", "customer");
                System.out.println("get role finish============");
                request.getSession().setAttribute("isLogin", true);



                // success,装入session中

                Map<String, Object> map = facesContext.getExternalContext().getSessionMap();

                map.put("user", customer);

                this.customer = customer;

                redirct = "fail";
              
              
            } //auth fails
            
             else if (!cbb.checkUserExist(username)) {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Username doesn't exist");
                customer = null;

                redirct = "fail";

            } else if (cbb.checkUserExist(username) && (!cbb.verifyPassword(username, hashPassword))) {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Wrong password");
                customer = null;

                redirct = "fail";


            } else {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid Credentials");
                customer = null;

                redirct = "fail";
            }
        } //time diff is less than 5 mins, don't allow login 
        else {
            System.out.println("Exceed max login nunmber");
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Exceed Max Login Number! Please Try Login After 5 Mins");
            redirct = "fail";

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        return redirct;

    }

    public void logout() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean logout = false;
        /*getSession(boolean create)如果 create 参数为 true，则创建（如有必要）并返回一个与当前请求关联的会话实例。如果 create 参数为 false，则返回与当前请求关联的任何现有会话实例；如果没有这样的会话，则返回 null。*/
        FacesMessage msg = null;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
 //HttpServletRequest requests = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        HttpSession session = (HttpSession) externalContext.getSession(false);
     //   requests.getSession().setAttribute("isLogin",false);
       // requests.getSession().setAttribute("role",null);
       // requests.getSession().invalidate();
       // HttpSession session = (HttpSession) externalContext.getSession(false);

        if (null != session) {

            session.invalidate();

            logout = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout", username);
            try {

                externalContext.redirect("loginCustomer.xhtml");

            } catch (IOException e) {

                e.printStackTrace();

            }

        }
        else {logout = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout", username);}
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("logout", logout);

    }

    public void register() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean register = false;
        active = true;

      /*  if (username != null && password != null) {
            String hashPassword = cbb.hashPassword(password);

            cbb.createCustomer(username, hashPassword, firstName, lastName, address, email, ageGroup, gender, phone);
            register = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reigstered Successfully", username);


        } else {
            register = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Register Error", "Register Failed");

        }
*/
        if (cbb.checkUserExist(username)) {
            register = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reigstered Failed", "Username Existed, use another username.");
        } 
        else if (!password.equals(confirmpassword)) {
            register = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reigstered Failed", "Passwords don't match.");
        }
        else if (username != null && password != null ) {
            String hashPassword = cbb.hashPassword(password);
            //cbb.createCustomer(username, hashPassword, firstName, lastName, address, email, ageGroup, gender, phone);
            register = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reigstered Successfully", username);
        } 
        else {
            register = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Register Error", "Register Failed");

        }
        
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("register", register);


    }

    public void isUsernameValid() {
        boolean ifUserExist = cbb.checkUserExist(username);
        if (ifUserExist) {
            UserExist = "Invalid Username, same username already exist.";

        } else {
            UserExist = "";
        }

    }

    public void updatePassword() {
    }

    public void matchPassword() {
        FacesMessage msg = null;
        if (password.equals(confirmpassword)) {
            match = "";
        } else {
            match = "Password didn't Match";
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Password doesn't matcb", "re-enter password");
        }
    }

    public CustomerBeanRemote getCbb() {
        return cbb;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }

    public String getActivateAccountName() {
        return activateAccountName;
    }

    public String getDeactivateAccountName() {
        return deactivateAccountName;
    }

    public String getUserExist() {
        return UserExist;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public String getMatch() {
        return match;
    }

    public void setCbb(CustomerBeanRemote cbb) {
        this.cbb = cbb;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setActivateAccountName(String activateAccountName) {
        this.activateAccountName = activateAccountName;
    }

    public void setDeactivateAccountName(String deactivateAccountName) {
        this.deactivateAccountName = deactivateAccountName;
    }

    public void setUserExist(String UserExist) {
        this.UserExist = UserExist;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public Map<String, String> getAgeGroups() {
        return ageGroups;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setAgeGroups(Map<String, String> ageGroups) {
        this.ageGroups = ageGroups;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
