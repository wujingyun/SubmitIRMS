/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.AdminUserBeanRemote;
import ejb.InternalMessageBean;
import entity.UserAccount;
import entity.InternalMessage;
import exception.ExistException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@ViewScoped
public class InternalMessageManagedBean implements Serializable {
    @EJB
    InternalMessageBean messageManager;
    @EJB
    AdminUserBeanRemote aub;
    
    private InternalMessage message;
    private String msg;
    private String receiver;
    
    private long rid;
    
    private String msgType;
    private String title;
    

    /** Creates a new instance of CreateInternalMsgManagedBean */
    public InternalMessageManagedBean() {
    }

    public List<String> complete(String query) throws ExistException {
        List<String> results = new ArrayList<String>();

        List<UserAccount> employeeList = aub.getAllUsers();

        for (Object o : employeeList) {
            UserAccount emp = (UserAccount) o;
            if (emp.getUserName().startsWith(query)) {
                results.add(emp.getUserName());
            }
        }
        return results;
    }

    public void sendMsg(ActionEvent event) throws IOException, ExistException {
   
         System.out.println("send internal message sender id: ============");
         String type;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       long senderId = (Long)request.getSession().getAttribute("userId");
        System.out.println("send internal message sender id: ============"+ senderId);
           // long   senderId=2;
        UserAccount user = aub.getUserById(senderId);
        List<String> userType = new ArrayList<String>();

  
        
            try {
                
                long rid = aub.getUser(receiver).getId();
                System.out.println("1send internal message receiver id: ============"+rid);
                type = "important";
                System.out.println("1send internal message sending");
                messageManager.addMessage(senderId, rid, title, msg, type);
            } 
            catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Message Sending Error","No such person"));
                return;
            }

        
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Message Sent Successfully",""));
                
       // FacesContext.getCurrentInstance().getExternalContext().redirect("createInternalMsgResult.xhtml");
    }

    public void setMessage(InternalMessage message) {
        this.message = message;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

  
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessageManager(InternalMessageBean messageManager) {
        this.messageManager = messageManager;
    }

    public void setEmployee(AdminUserBeanRemote employee) {
        this.aub = employee;
    }

    public InternalMessage getMessage() {
        return message;
    }

    public String getMsg() {
        return msg;
    }

   

    public String getMsgType() {
        return msgType;
    }

    public String getTitle() {
        return title;
    }

    public InternalMessageBean getMessageManager() {
        return messageManager;
    }

    public AdminUserBeanRemote getEmployee() {
        return aub;
    }

    
    public String getReceiver() {
        return receiver;
    }

    

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    
}

