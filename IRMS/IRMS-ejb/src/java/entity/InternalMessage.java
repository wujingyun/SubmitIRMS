/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import entity.InternalMessageReceive;
/**
 *
 * @author WU JINGYUN
 */
@Entity
public class InternalMessage implements Serializable {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    private String content;
    private String title;
    private String sendTime;
    private String senderName;
    private String status;
    private String type;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy="message")
    private List<InternalMessageReceive> recInfo = new ArrayList<InternalMessageReceive>();
    
    
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

   
    public String getContent() {
        return content;
    }

   
    public void setContent(String content) {
        this.content = content;
    }

  
    public List<InternalMessageReceive> getRecInfo() {
        return recInfo;
    }

    
    public void setRecInfo(List<InternalMessageReceive> recInfo) {
        this.recInfo = recInfo;
    }

   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the messageId fields are not set
        if (!(object instanceof InternalMessage)) {
            return false;
        }
        InternalMessage other = (InternalMessage) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.InternalMessage[ messageId=" + messageId + " ]";
    }
    
}
