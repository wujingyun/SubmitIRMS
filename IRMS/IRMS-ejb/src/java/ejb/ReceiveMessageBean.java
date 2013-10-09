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
import javax.persistence.NoResultException;
import javax.persistence.Query;
/**
 *
 * @author WU JINGYUN
 */
@Stateless
@LocalBean
public class ReceiveMessageBean  {

   @PersistenceContext
    private EntityManager em;
    
    private InternalMessageBean mm;
    
    
    public InternalMessageReceive getReceiverInfoById(Long Id)throws ExistException
    {
        InternalMessageReceive info = em.find(InternalMessageReceive.class, Id);
        if(info == null) {
            throw new ExistException("No Internal Message Received!");
        }
        return info;
    }
    

    public void addReceiverInfo(InternalMessageReceive info)
    {
        System.out.println("ReceiveMessageBean --> add ReceiverInfo");
        em.persist(info);
    }
   
    public void editEmployee(InternalMessageReceive info)
    {
        System.out.println("ReceiveMessageBean --> edit ReceiverInfo");
        em.merge(info);
    }
    

    public void removeReceiverInfo(Long infoId) throws ExistException
    {
        InternalMessageReceive receiverInfo = em.find(InternalMessageReceive.class, infoId);
        if(receiverInfo == null) {
            throw new ExistException("ReceiverInfo does not exist!");
        }
         System.out.println("ReceiveMessageBean --> remove ReceiverInfo");
        em.remove(receiverInfo);
    }
 
    public List<InternalMessageReceive> getAllReceiverInfo(){
        Query query = em.createQuery("SELECT im FROM InternalMessageReceive im");
        return query.getResultList();
    }
  
    public List<InternalMessageReceive> getRecInfoBySender(long id){
        Query query = em.createQuery("SELECT im FROM InternalMessageReceive im WHERE im.senderId = :recID");
        query.setParameter("recID", id);
        List<InternalMessageReceive> recInfo = null;
        
        try{
            recInfo = query.getResultList();
        }
        catch(NoResultException ex){
            ex.printStackTrace();
        }
        
        return recInfo;
        
    }
 
    public List<InternalMessage> getMessageBySender(long id){
        List<InternalMessageReceive> recInfo = getRecInfoBySender(id);
        int i = 0;
        List<InternalMessage> msg = new ArrayList<InternalMessage>();
        while (recInfo.get(i) != null){
            msg.add(recInfo.get(i).getMessage());
        }
        
        return msg;
    }

    public List<InternalMessageReceive> getRecInfoByReceiver(long id){
        Query query = em.createQuery("SELECT s1 FROM InternalMessageReceive im WHERE im.receiverId = ?1");
        query.setParameter(1, id);
        List<InternalMessageReceive> recInfo = null;
        System.out.println("ReceiveMessageBean-->getRecInfoByReceiver");
        try{
            recInfo = query.getResultList();
        }
        catch(NoResultException ex){
            ex.printStackTrace();
        }
        
        return recInfo;
    }

    public List<InternalMessage> getMessageByReceiver(long id){
        List<InternalMessageReceive> recInfo = getRecInfoByReceiver(id);
        List<InternalMessage> msg = new ArrayList<InternalMessage>();
        System.out.println("ReceiveMessageBean --> getMessageByReceiver"+recInfo.size());
        for (int i=0;i<recInfo.size();i++) {
            msg.add(recInfo.get(i).getMessage());
        }
        
        return msg;
    }
    

    public InternalMessageBean getMm() {
        return mm;
    }

    public void setMm(InternalMessageBean mm) {
        this.mm = mm;
    }
    

}
