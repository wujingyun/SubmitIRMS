/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.AdminUserBeanRemote;
import ejb.InternalMessageBean;
import ejb.ReceiveMessageBean;
import entity.InternalMessage;
import exception.ExistException;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author WU JINGYUN
 */
@SessionScoped
@ManagedBean
public class ReceivedMsgManagedBean implements Serializable {

    @EJB
    ReceiveMessageBean infoManager;
    @EJB
    InternalMessageBean messageManager;
    @EJB
    AdminUserBeanRemote employeeManager;
    private List<InternalMessage> messageList;
    private InternalMessage selectedMessage;
    private String sender;
    private String sendTime;
    private String title;
    private String content;
    private InternalMessage message;

    @PostConstruct
    public void init() {
        System.out.println("getmessage0==============");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.messageList = infoManager.getMessageByReceiver((Long) request.getSession().getAttribute("userId"));

        for (int i = 0; i < messageList.size(); i++) {
            long senderId = messageList.get(i).getRecInfo().get(0).getSenderId();
            String senderName = employeeManager.getUserById(senderId).getUserName();
            messageList.get(i).setSenderName(senderName);

            String status;
            if (messageList.get(i).getRecInfo().get(0).isOpened()) {
                status = "Read";
            } else {
                status = "Unread";
            }
            messageList.get(i).setStatus(status);
        }

    }

    public ReceivedMsgManagedBean() {
    }

    public void viewContent(ActionEvent event) throws IOException {
        
        content=selectedMessage.getContent();
        selectedMessage.getRecInfo().get(0).setOpened(true);
        messageManager.editMessage(selectedMessage);
        System.out.println(content+"---------------");
    }

    public void deleteMsg(ActionEvent event) throws ExistException {
        System.out.println("Deleting================ ");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        long receiverId = (Long) request.getSession().getAttribute("userId");
       //System.out.println("Delete Message for User " + receiverId);
       
       // Long id=(Long)event.getComponent().getAttributes().get("deleteMsg");
        long id=selectedMessage.getMessageId();
     // Long id=(Long)event.getComponent().getAttributes().get("deleteMsg");
        System.out.println("Delete Message number "+ id);
        

        messageManager.removeMessage(id, receiverId);
        //selectedMessage = new InternalMessage();
        
        messageList.remove(selectedMessage);
        
        System.err.println("messageList.size(): " + messageList.size());
    }

    public void View(PhaseEvent event) {

        if (message == null) {

            message = (InternalMessage) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("msg");
        }
        sender = message.getSenderName();
        sendTime = message.getSendTime();
        title = message.getTitle();
        content = message.getContent();
        message.getRecInfo().get(0).setOpened(true);
        messageManager.editMessage(message);

    }

    public void goBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./smpTest.xhtml");
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the sendTime
     */
    public String getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime the sendTime to set
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the message
     */
    public InternalMessage getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(InternalMessage message) {
        this.message = message;
    }

    /**
     * @return the messageList
     */
    public List<InternalMessage> getMessageList() {
        return messageList;
    }

    /**
     * @param messageList the messageList to set
     */
    public void setMessageList(List<InternalMessage> messageList) {
        this.messageList = messageList;
    }

    /**
     * @return the selectedMessage
     */
    public InternalMessage getSelectedMessage() {
        return selectedMessage;
    }

    /**
     * @param selectedMessage the selectedMessage to set
     */
    public void setSelectedMessage(InternalMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public void setInfoManager(ReceiveMessageBean infoManager) {
        this.infoManager = infoManager;
    }

    public void setMessageManager(InternalMessageBean messageManager) {
        this.messageManager = messageManager;
    }

    public void setEmployeeManager(AdminUserBeanRemote employeeManager) {
        this.employeeManager = employeeManager;
    }

    public ReceiveMessageBean getInfoManager() {
        return infoManager;
    }

    public InternalMessageBean getMessageManager() {
        return messageManager;
    }

    public AdminUserBeanRemote getEmployeeManager() {
        return employeeManager;
    }
}
