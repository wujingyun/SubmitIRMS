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
import javax.servlet.http.HttpServletRequest;


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
    private String password;
    private String newpass1;
    private String newpass2;
    private String oldpass;
    
    
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
    
    
    
       public void changePass(ActionEvent event) throws IOException, ExistException {
        FacesMessage msg = null;
         HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
           long userId = (Long)request.getSession().getAttribute("userId");
           System.err.println(userId+".......................");
           username= aub.getUserById(userId).getUserName();
        
        System.out.println("==================================username to reset password" + username);
        systemUser = aub.findUser(username);
   
        
        String hashPassword = aub.hashPassword(oldpass);
       if (!aub.verifyPassword(username, hashPassword)) {
            System.out.println("==================================old pass wrong "+hashPassword);
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Old Password is wrong", "");

            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        } 
        else if ((newpass1.equalsIgnoreCase("")||newpass2.equalsIgnoreCase(""))){
        System.out.println("==================================empty new pass "+newpass1+newpass2);
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Empty new password", "");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else if (newpass1.compareTo(newpass2) != 0) {
              System.out.println("==================================new pass don't match ");
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwords don't match", "");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            System.out.println("==================================change pass ");
            aub.setHashPassword(username, newpass1);
            aub.setLoginAttempToZero(username);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Passwords changed", "Please logout and login again");

            FacesContext.getCurrentInstance().addMessage(null, msg);
            //FacesContext.getCurrentInstance().getExternalContext().redirect("crm.xhtml");
        }







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
