/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import java.io.Serializable;
import ejb.AdminUserBeanRemote;
import entity.AccessRight;
import entity.UserAccount;
import exception.ExistException;
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
public class LoginBean implements Serializable {

    @EJB
    AdminUserBeanRemote aub;
    private String email;
    private String phone;
    private String username;
    private String password;
    private UserAccount user;
    private String division;
    private boolean active;
    private String subrole;
    private Map<String, String> divisions = new HashMap<String, String>();
    private Map<String, String> subroles = new HashMap<String, String>();
    private Map<String, Map<String, String>> suburbsData = new HashMap<String, Map<String, String>>();
    private List<UserAccount> divisionAccount = new ArrayList<UserAccount>();
    private Map<String, String> accountByDivisionMap;
    private String activateAccountName;
    private String deactivateAccountName;
    private String UserExist = "";
    private String confirmpassword;
    private String match;

    public LoginBean() {
        divisions.put("SuperAdmin", "SuperAdmin");
        divisions.put("Accomondation", "Accomondation");
        divisions.put("Shopping Mall", "Shopping Mall");
        divisions.put("Convention", "Convention");
        divisions.put("Food Beverage", "Food Beverage");
        divisions.put("Attraction", "Attraction");
        divisions.put("Entertainment Show", "Entertainment Show");



        Map<String, String> subrolesSuper = new HashMap<String, String>();
        subrolesSuper.put("Super Admin", "1");

        Map<String, String> subrolesAcc = new HashMap<String, String>();
        subrolesAcc.put("Acm Super Admin", "2");
        subrolesAcc.put("Acm Front Receptionist", "3");
        subrolesAcc.put("Acm Backend Offier", "4");

        Map<String, String> subrolesShop = new HashMap<String, String>();
        subrolesShop.put("Spm Super Admin", "5");
        subrolesShop.put("Cankaya", "5");
        subrolesShop.put("Yenimahalle", "6");

        Map<String, String> subrolesConvention = new HashMap<String, String>();
        subrolesConvention.put("Cesme", "7");
        subrolesConvention.put("Gumuldur", "8");
        subrolesConvention.put("Foca", "9");


        Map<String, String> subrolesFb = new HashMap<String, String>();
        subrolesFb.put("Cesme", "10");
        subrolesFb.put("Gumuldur", "11");
        subrolesFb.put("Foca", "12");

        Map<String, String> subrolesAttr = new HashMap<String, String>();
        subrolesAttr.put("Cesme", "13");
        subrolesAttr.put("Gumuldur", "14");
        subrolesAttr.put("Foca", "15");

        Map<String, String> subrolesEnter = new HashMap<String, String>();
        subrolesEnter.put("Cesme", "16");
        subrolesEnter.put("Gumuldur", "17");
        subrolesEnter.put("Foca", "18");


        suburbsData.put("SuperAdmin", subrolesSuper);
        suburbsData.put("Accomondation", subrolesAcc);
        suburbsData.put("Shopping Mall", subrolesShop);
        suburbsData.put("Convention", subrolesConvention);
        suburbsData.put("Food Beverage", subrolesFb);
        suburbsData.put("Attraction", subrolesAttr);
        suburbsData.put("Entertainment Show", subrolesEnter);

    }

    public void handleDivisionChange() {
        if (division != null && !division.equals("")) {
            subroles = suburbsData.get(division);
        } else {
            subroles = new HashMap<String, String>();
        }



        System.out.println("match password ======================================" + password);
        System.out.println("match password ======================================" + confirmpassword);

    }

    public void getAccountByDivisionToDe() {
        accountByDivisionMap = new HashMap<String, String>();
        if (division != null && !division.equals("")) {
            divisionAccount = aub.getAccountByDivisionToDA(division);
        } else {
            divisionAccount = new ArrayList<UserAccount>();
        }
        for (UserAccount u : divisionAccount) {

            accountByDivisionMap.put(u.getUserName(), u.getUserName());
        }
    }

    public void getAccountByDivisionToAc() {
        accountByDivisionMap = new HashMap<String, String>();
        if (division != null && !division.equals("")) {
            divisionAccount = aub.getAccountByDivisionToA(division);
        } else {
            divisionAccount = new ArrayList<UserAccount>();
        }
        for (UserAccount u : divisionAccount) {

            accountByDivisionMap.put(u.getUserName(), u.getUserName());
        }
    }

    public void displayLocation() {
        //   FacesMessage msg = new FacesMessage("Selected", "Division:" + division + ", Role: " + subrole);  
        //  FacesContext.getCurrentInstance().addMessage(null, msg);  
    }

    public String login(ActionEvent actionEvent) throws ExistException {


        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirct;

        System.out.println("username is " + username + " password is " + password);
        String hashPassword = aub.hashPassword(password);
        //if attmep number lesser than 5, allow login
        //if attemp number larger than 5 and account has locked, only allow login after 5 mins .  

        //attemp number lesser than 5 or it's 5 mins after last attemp, allow login
        if ((aub.getLoginAttemp(username) <= 5) || (aub.checkLockOut(username) == true)) {

            System.out.println("login number============" + aub.getLoginAttemp(username));

            //have to reset attemp number to 0 and update attemp time(in the case where account is unlocked
            //, otherwise, if login fails again, the account will be locked for another 5 min
            if (aub.checkLockOut(username)) {
                aub.setLoginAttempToZero(username);
                aub.updateLoginAttempTime(username);
            }
            //auth
            if (username != null && password != null && aub.verifyPassword(username, hashPassword)) {
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);

                user = aub.findUser(username);
                String role=aub.getUserRole(username);
                long userId=aub.getUser(username).getId();
                System.out.println("first ============");
                aub.setLoginAttempToZero(username);
                 System.out.println("second ============");
                aub.updateLoginAttempTime(username);
                 System.out.println("third ============");
               request.getSession().setAttribute("isLogin", true);
                request.getSession().setAttribute("role", role);
                request.getSession().setAttribute("userId", userId);
                System.out.println("get role finish============"+ role);
            
  System.out.println("get userid finish============"+ userId);

                // success,装入session中

                Map<String, Object> map = facesContext.getExternalContext().getSessionMap();

                map.put("user", user);

                this.user = user;


                if (role.equalsIgnoreCase("SuperAdmin")) {
                    try {

                        externalContext.redirect("/IRMS-war/admin.xhtml");

                    } catch (IOException e) {

                        e.printStackTrace();

                    }
                } else if (role.contains("spm")) {
                    try {

                        externalContext.redirect("/IRMS-war/smpMain.xhtml");

                    } catch (IOException e) {

                        e.printStackTrace();

                    }
                } else if (role.contains("acm")) {
                    try {

                        externalContext.redirect("/IRMS-war/acmMain.xhtml");

                    } catch (IOException e) {

                        e.printStackTrace();

                    }
                }

                redirct = "fail";

            } //auth fails
            else if (!aub.checkUserExist(username)) {
                loggedIn = false;
                aub.updateLoginAttemp(username);
                aub.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Username doesn't exist");
                user = null;

                redirct = "fail";

            } else if (aub.checkUserExist(username) && (!aub.verifyPassword(username, hashPassword))) {
                loggedIn = false;
                aub.updateLoginAttemp(username);
                aub.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Wrong password");
                user = null;

                redirct = "fail";


            } else {
                loggedIn = false;
                aub.updateLoginAttemp(username);
                aub.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid Credentials");
                user = null;

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
         
        HttpSession session = (HttpSession) externalContext.getSession(false);
         
        if (null != session) {

            session.invalidate();

            logout = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout", username);
        
            try {

                externalContext.redirect("/IRMS-war/loginInternalUser.xhtml");

            } catch (IOException e) {

                e.printStackTrace();

            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("logout", logout);

        }
    }


    public void register() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean register = false;
        active = true;

        long thesubrole = Long.parseLong(subrole);
        if (aub.checkUserExist(username)) {
            register = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reigstered Failed", "Username Existed, use another username.");
        } else if (!password.equals(confirmpassword)) {
            register = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reigstered Failed", "Passwords don't match.");

        } else if (username != null && password != null && division != null) {
            String hashPassword = aub.hashPassword(password);

            aub.register(username, thesubrole, hashPassword, division, active, phone, email);
            register = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reigstered Successfully", username);


        } else {
            register = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Register Error", "Register Failed");

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("register", register);


    }

    public void isUsernameValid() {
        boolean ifUserExist = aub.checkUserExist(username);
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

    public void deactive() {

        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;

        if (deactivateAccountName != null) {

            aub.deactivateAcct(deactivateAccountName);

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deactivate Successfully", username);


        } else {

            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Deactivate Error", "Select account to deactivate");

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void activate() {

        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;

        if (activateAccountName != null) {

            aub.activateAcct(activateAccountName);

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activate Successfully", username);


        } else {

            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Activate Error", "Select account to activate");

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public AdminUserBeanRemote getAub() {
        return aub;
    }

    public UserAccount getUser() {
        return user;
    }

    public String getDivision() {
        return division;
    }

    public String getSubrole() {
        return subrole;
    }

    public Map<String, String> getSubroles() {
        return subroles;
    }

    public void setSubrole(String subrole) {
        this.subrole = subrole;
    }

    public void setSubroles(Map<String, String> subroles) {
        this.subroles = subroles;
    }

    public Map<String, String> getDivisions() {
        return divisions;
    }

    public Map<String, Map<String, String>> getSuburbsData() {
        return suburbsData;
    }

    public void setAub(AdminUserBeanRemote aub) {
        this.aub = aub;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setDivisions(Map<String, String> divisions) {
        this.divisions = divisions;
    }

    public void setSuburbsData(Map<String, Map<String, String>> suburbsData) {
        this.suburbsData = suburbsData;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }

    public List<UserAccount> getDivisionAccount() {
        return divisionAccount;
    }

    public Map<String, String> getAccountByDivisionMap() {
        return accountByDivisionMap;
    }

    public String getActivateAccountName() {
        return activateAccountName;
    }

    public void setDivisionAccount(List<UserAccount> divisionAccount) {
        this.divisionAccount = divisionAccount;
    }

    public void setAccountByDivisionMap(Map<String, String> accountByDivisionMap) {
        this.accountByDivisionMap = accountByDivisionMap;
    }

    public void setActivateAccountName(String activateAccountName) {
        this.activateAccountName = activateAccountName;
    }

    public String getUserExist() {
        return UserExist;
    }

    public void setUserExist(String UserExist) {
        this.UserExist = UserExist;
    }

    public String getDeactivateAccountName() {
        return deactivateAccountName;
    }

    public void setDeactivateAccountName(String deactivateAccountName) {
        this.deactivateAccountName = deactivateAccountName;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }
}
