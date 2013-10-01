/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.InternalMessage;
import exception.ExistException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author WU JINGYUN
 */
@Remote
public interface InternalMessageBeanRemote {
      public InternalMessage getMessageById(Long messageId) throws ExistException;
      public void addMessage(long senderId,long receiverId,String title,String msg,String type);
       public void editMessage(InternalMessage message);
        public void removeMessage(Long messageId, String receiverId)throws ExistException;
          public List<InternalMessage> getAllMessages();
}
