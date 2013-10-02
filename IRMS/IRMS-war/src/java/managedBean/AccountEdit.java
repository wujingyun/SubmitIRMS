/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ejb.AdminUserBeanRemote;
import entity.UserAccount;
import exception.ExistException;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class AccountEdit implements Serializable {

    @EJB
    AdminUserBeanRemote editaub;
    private String editaccount;
    private String editemail;
    private String editphone;
    private HttpServletRequest editrequest;
    private long edituid;
    private UserAccount edituser;

    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        long uid = (Long) request.getSession().getAttribute("userId");
        System.out.println(uid+"account edit get user id ================");
        UserAccount edituser = editaub.getUserById(uid);
        editaccount = edituser.getUserName();
        editemail = edituser.getContact().getEmail();
        editphone = edituser.getContact().getPhone();
    }

    public AccountEdit() {
        
      
    }
    
    public void update(ActionEvent event) throws  ExistException{
          HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       long uid = (Long) request.getSession().getAttribute("userId");
        UserAccount edituser = editaub.getUserById(uid);
        edituid=uid;
        System.out.println(edituid+"update");
        editaub.updateUserById(edituid, editaccount, editemail, editphone);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated",""));
              
    }

    public void setEditaub(AdminUserBeanRemote editaub) {
        this.editaub = editaub;
    }

    public void setEditaccount(String editaccount) {
        this.editaccount = editaccount;
    }

    public void setEditemail(String editemail) {
        this.editemail = editemail;
    }

    public void setEditphone(String editphone) {
        this.editphone = editphone;
    }

    public void setEditrequest(HttpServletRequest editrequest) {
        this.editrequest = editrequest;
    }

    public void setEdituid(long edituid) {
        this.edituid = edituid;
    }

    public void setEdituser(UserAccount edituser) {
        this.edituser = edituser;
    }

    public AdminUserBeanRemote getEditaub() {
        return editaub;
    }

    public String getEditaccount() {
        return editaccount;
    }

    public String getEditemail() {
        return editemail;
    }

    public String getEditphone() {
        return editphone;
    }

    public HttpServletRequest getEditrequest() {
        return editrequest;
    }

    public long getEdituid() {
        return edituid;
    }

    public UserAccount getEdituser() {
        return edituser;
    }

 
}
