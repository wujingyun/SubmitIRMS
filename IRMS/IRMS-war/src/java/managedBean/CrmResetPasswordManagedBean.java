/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import entity.Customer;
import ejb.CustomerBeanRemote;
import ejb.EmailSessionBean;
import exception.ExistException;
import java.io.IOException;
import java.util.UUID;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author wujingyun
 */
@ManagedBean
@ViewScoped
public class CrmResetPasswordManagedBean {

    @EJB
    private CustomerBeanRemote cbb;
    @EJB
    private EmailSessionBean emailSessionBean;
    private String username;
    private String email;
    private Customer customer = null;
    private String password;
    private String newpass1;
    private String newpass2;
    private String oldpass;

    public CrmResetPasswordManagedBean() {
    }

    public void resetPassword(ActionEvent event) throws IOException, ExistException {

        System.out.println("==================================username to reset password" + username);
        customer = cbb.findCustomer(username);
        System.out.println("==================================user id to reset password");
        System.out.println(customer);
        if (customer == null) {
            System.out.println("==================================user is null");
            // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid UserName", ""));
            System.out.println("==================================user is null2");



            FacesMessage msg = null;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid User Name", username);

            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println("==================================user is null3");

        } else {
            String uuid = UUID.randomUUID().toString();
            String[] sArray = uuid.split("-");
            String initialPwd = sArray[0];
            String hashPassword = cbb.hashPassword(initialPwd);
            cbb.setHashPassword(username, initialPwd);
            cbb.setLoginAttempToZero(username);
            emailSessionBean.emailInitialPassward(customer.getEmail(), initialPwd);
            FacesContext.getCurrentInstance().getExternalContext().redirect("crmResetResult.xhtml");
        }

    }

    public void changePass(ActionEvent event) throws IOException, ExistException {
        FacesMessage msg = null;
        System.out.println("==================================username to reset password" + username);
        customer = cbb.findCustomer(username);
        System.out.println("==================================user id to reset password");
        System.out.println(customer);
        String hashPassword = cbb.hashPassword(oldpass);
        if (customer == null) {
            System.out.println("==================================user is null");
            // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid UserName", ""));
            System.out.println("==================================user is null2");

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid User Name", username);

            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println("==================================user is null3");

        } else if (!cbb.verifyPassword(username, hashPassword)) {
            System.out.println("==================================old pass wrong "+hashPassword);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Old Password is wrong", "");

            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        } 
        else if ((newpass1.equalsIgnoreCase("")||newpass2.equalsIgnoreCase(""))){
        System.out.println("==================================empty new pass "+newpass1+newpass2);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empty new password", "");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else if (newpass1.compareTo(newpass2) != 0) {
              System.out.println("==================================new pass don't match ");
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Passwords don't match", "");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            System.out.println("==================================change pass ");
            cbb.setHashPassword(username, newpass1);
            cbb.setLoginAttempToZero(username);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Passwords changed", "Please logout and login again");

            FacesContext.getCurrentInstance().addMessage(null, msg);
            //FacesContext.getCurrentInstance().getExternalContext().redirect("crm.xhtml");
        }







    }

    public void setCbb(CustomerBeanRemote cbb) {
        this.cbb = cbb;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewpass1(String newpass1) {
        this.newpass1 = newpass1;
    }

    public void setNewpass2(String newpass2) {
        this.newpass2 = newpass2;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

    public CustomerBeanRemote getCbb() {
        return cbb;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getPassword() {
        return password;
    }

    public String getNewpass1() {
        return newpass1;
    }

    public String getNewpass2() {
        return newpass2;
    }

    public String getOldpass() {
        return oldpass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAub(CustomerBeanRemote aub) {
        this.cbb = aub;
    }

    public void setEmailSessionBean(EmailSessionBean emailSessionBean) {
        this.emailSessionBean = emailSessionBean;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSystemUser(Customer systemUser) {
        this.customer = systemUser;
    }

    public CustomerBeanRemote getAub() {
        return cbb;
    }

    public EmailSessionBean getEmailSessionBean() {
        return emailSessionBean;
    }

    public String getUsername() {
        return username;
    }

    public Customer getSystemUser() {
        return customer;
    }
}
