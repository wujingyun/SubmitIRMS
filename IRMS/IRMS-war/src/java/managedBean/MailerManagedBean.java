/*
 * Copyright 2013 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

//import entity.Customer;
import ejb.MailerBean;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
//import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
//import javax.faces.context.FacesContext;
import javax.inject.Named;
import ejb.MemberSessionBeanLocal;


@Named
@RequestScoped
@ManagedBean(name="mailerManagerBean")
public class MailerManagedBean {
    private static final Logger logger = Logger.getLogger(
                "managedBean.MailerManagedBean");
    
    @EJB
    protected MailerBean mailerBean;
    @EJB
    protected MemberSessionBeanLocal memB;
    protected String username;
    protected String email;
    protected String status;
    protected Long memId;
    protected String mID;

    /** Creates a new instance of MailerManagedBean */
    public MailerManagedBean() {
    }

    public long getMemId() {
        return memId;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    /**
     *
     * @param memId
     */
    public void setMemId(Long memId) {
        this.memId = memId;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String send() {
        String response = "response";
        System.out.println("mmb lin 91");
   //newly added code     
        System.out.println(this.getmID()+"   "+this.getEmail());
        boolean flag = memB.check(this.getmID(),this.getEmail());
        System.out.println(flag);
        System.out.println("mailer bean 91");
        if (!flag) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Member ID and email do not match"));
            return "password";
        }
//till here
        try {
            Future<String> mailStatus = mailerBean.sendMessage(this.getEmail());

            System.out.println("mmb line 96");
            while (!mailStatus.isDone()) {
                this.setStatus("Processing...");
            }

            try {
                this.setStatus(mailStatus.get());
            } catch (ExecutionException ex) {
                this.setStatus(ex.getCause().toString());
            }
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }

        return response;
    }
}
