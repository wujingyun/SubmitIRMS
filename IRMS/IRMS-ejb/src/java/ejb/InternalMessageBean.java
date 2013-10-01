/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;
import exception.ExistException;
import javax.ejb.Stateless;
import entity.InternalMessage;
import entity.InternalMessageReceive;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import entity.UserAccount;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 *
 * @author WU JINGYUN
 */
@Stateless
@LocalBean
public class InternalMessageBean  {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    ReceiveMessageBean infoManager = new ReceiveMessageBean();
    
   
    public InternalMessage getMessageById(Long messageId) throws ExistException{
        System.out.println("Message----- get message with id" + messageId);
        InternalMessage message = em.find(InternalMessage.class, messageId);
        if(message == null) {
            throw new ExistException("Message does not exist!");
        }
        return message;
    }
    

    public void addMessage(long senderId,long receiverId,String title,String msg,String type)
    {
        System.out.println("Message------creating a new message....");
        InternalMessage message = new InternalMessage();
        
        InternalMessageReceive receiver = new InternalMessageReceive();
        receiver.setDeleted(false);
        receiver.setOpened(false);
        receiver.setReceiverId(receiverId);
        receiver.setSenderId(senderId);
        receiver.setMessage(message);
        //retrieve senderName from employee table
        UserAccount sender = em.find(UserAccount.class,senderId);
        message.setSenderName(sender.getUserName());
        
        List<InternalMessageReceive> infoList = new ArrayList<InternalMessageReceive>();
        infoList.add(receiver);
        
        message.setRecInfo(infoList);
        message.setTitle(title);
        message.setContent(msg);
        message.setType(type);
        
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
 
        try {
            
            String sendTime = dateFormat.format(calendar.getTime());
            message.setSendTime(sendTime);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Message------one new message sent: "+ msg + "--from person "+senderId);
        em.persist(message);
    }
    
    
    

    public void editMessage(InternalMessage message)
    {
        em.merge(message);
        System.out.println("Message------message updated as " + message.getContent());
        em.flush();
    }
    

    public void removeMessage(Long messageId, long receiverId)throws ExistException
    {
        InternalMessage message = em.find(InternalMessage.class, messageId);
//        List<ReceiverInfo> infoList = message.getRecInfo();
        if(message == null) {
            throw new ExistException("Message does not exist!");
        }
        for (int i=0; i<message.getRecInfo().size(); i++) {
            if (receiverId==message.getRecInfo().get(i).getReceiverId()){
                Long infoId = message.getRecInfo().get(i).getReceiveMsgId();
                
                message.getRecInfo().remove(i);
                editMessage(message);
                
                infoManager.removeReceiverInfo(infoId);

                break;
            } 
        }
        
        
        if (message.getRecInfo().size()>0){
            return;
        }
        else{
            em.remove(message);
        }        
        
    }


    public List<InternalMessage> getAllMessages(){
        Query query = em.createQuery("SELECT s1 FROM InternalMessage s1");
        return query.getResultList();
    }
    
}

