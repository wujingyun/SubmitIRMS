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
public class InternalLogoutManagedBean implements Serializable {

    //   @EJB
    //AdminUserBeanRemote aub;
    @EJB
    CustomerBeanRemote cbb;
    private Customer customer;
    private String username;

    public InternalLogoutManagedBean() {
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

                externalContext.redirect("loginInternalUser.xhtml");

            } catch (IOException e) {

                e.printStackTrace();

            }

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("logout", logout);

    }

    public void goBack() throws IOException 
        {

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String role = (String) request.getSession().getAttribute("role");
            if (role.equalsIgnoreCase("SuperAdmin")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
            } else if (role.contains("spm")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("smpMain.xhtml");
            } else if (role.contains("acm")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("acmMain.xhtml");
            }
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

    public CustomerBeanRemote getCbb() {
        return cbb;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getUsername() {
        return username;
    }
}
