/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 package managedBean;
 
import entity.UserAccount; 
import ejb.AdminUserBeanRemote;
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
public class ResetPasswordManagedBean {
    
    @EJB
    private AdminUserBeanRemote aub ;
    @EJB
    private EmailSessionBean emailSessionBean;
    private String username;
     private String email;
    private UserAccount systemUser=null;
  
    public ResetPasswordManagedBean() {
    }

    public void resetPassword(ActionEvent event) throws IOException, ExistException {
     
         System.out.println("==================================username to reset password"+username);
        systemUser = aub.findUser(username);
        System.out.println("==================================user id to reset password");
         System.out.println(systemUser);
        if (systemUser == null) {
          System.out.println("==================================user is null"); 
          //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid UserName", ""));
System.out.println("==================================user is null2");
 


FacesMessage msg = null; 
 msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid User Name", username);

        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("==================================user is null3");
     
        }
        
        
       
        else {
                String uuid = UUID.randomUUID().toString();
                String[] sArray = uuid.split("-");
                String initialPwd = sArray[0];
                String hashPassword = aub.hashPassword(initialPwd);
                aub.setHashPassword(username, initialPwd);
                aub.setLoginAttempToZero(username);
                emailSessionBean.emailInitialPassward(systemUser.getContact().getEmail(), initialPwd); 
                FacesContext.getCurrentInstance().getExternalContext().redirect("ResetResult.xhtml");
            } 
              
      
    
    
    
    
    
    }
      public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
 public void setAub(AdminUserBeanRemote aub) {
        this.aub = aub;
    }

    public void setEmailSessionBean(EmailSessionBean emailSessionBean) {
        this.emailSessionBean = emailSessionBean;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSystemUser(UserAccount systemUser) {
        this.systemUser = systemUser;
    }

    public AdminUserBeanRemote getAub() {
        return aub;
    }

    public EmailSessionBean getEmailSessionBean() {
        return emailSessionBean;
    }

    public String getUsername() {
        return username;
    }

    public UserAccount getSystemUser() {
        return systemUser;
    }


}
