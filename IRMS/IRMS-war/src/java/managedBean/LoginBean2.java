/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import java.io.Serializable;
import ejb.AdminUserBeanRemote;
import entity.AccessRight;
import entity.UserAccount;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class LoginBean2 implements Serializable {

    @EJB
    AdminUserBeanRemote aub;
    private String username;
    private String password;
    private UserAccount user;
    private String email;
    private String phone;
    private String division;
    private boolean active;
    private String subrole;
    private Map<String, String> divisions = new HashMap<String, String>();
    private Map<String, String> subroles = new HashMap<String, String>();
    private Map<String, Map<String, String>> suburbsData = new HashMap<String, Map<String, String>>();

    public LoginBean2() {
        divisions.put("Accomondation", "Accomondation");
        divisions.put("Shopping Mall", "Shopping Mall");
        divisions.put("Convention", "Convention");
        divisions.put("Food Beverage", "Food Beverage");
        divisions.put("Attraction", "Attraction");
        divisions.put("Entertainment Show", "Entertainment Show");

        Map<String, String> subrolesAcc = new HashMap<String, String>();
        subrolesAcc.put("Front office counter", "1");
        subrolesAcc.put("Levent", "2");
        subrolesAcc.put("Cengelkoy", "3");

        Map<String, String> subrolesShop = new HashMap<String, String>();
        subrolesShop.put("Kecioren", "4");
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
    }

    public void displayLocation() {
        //   FacesMessage msg = new FacesMessage("Selected", "Division:" + division + ", Role: " + subrole);  
        //  FacesContext.getCurrentInstance().addMessage(null, msg);  
    }

    public String info() {

        return "manageContract2.xhtml?faces-redirect=true";
    }

    public String login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirct;
        if (username != null && password != null && aub.verifyPassword(username, password)) {
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
            //  user=aub.findUser(username);
            user = aub.findUser(username);

            // success,装入session中

            Map<String, Object> map = facesContext.getExternalContext().getSessionMap();

            map.put("user", user);

            this.user = user;

            redirct = "fail";




        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
            user = null;

            redirct = "loginSuccess";

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

                externalContext.redirect("login.xhtml");

            } catch (IOException e) {

                e.printStackTrace();

            }

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("logout", logout);

    }

    public void register() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean register = false;
        active = true;


        long thesubrole = Long.parseLong(subrole);


        email = "a0092208@nus.edu.sg";
        phone = "83686522";

        if (username != null && password != null && division != null) {

            //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);

            aub.register(username, thesubrole, password, division, active, phone, email);
            register = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reigstered Successfully", username);


        } else {
            register = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Register Error", "Register Failed");

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("register", register);


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
}
