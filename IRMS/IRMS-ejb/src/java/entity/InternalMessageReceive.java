/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import entity.InternalMessage;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
/**
 *
 * @author WU JINGYUN
 */

@Entity
public class InternalMessageReceive implements Serializable {
       @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiveMsgId;
    private boolean deleted;
    private boolean opened;
    private long receiverId;
    private long senderId;
    @ManyToOne (targetEntity = InternalMessage.class)
    private InternalMessage message;

    public Long getReceiveMsgId() {
        return receiveMsgId;
    }

    public void setReceiveMsgId(Long receiveMsgId) {
        this.receiveMsgId = receiveMsgId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiveMsgId != null ? receiveMsgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the receiveMsgId fields are not set
        if (!(object instanceof InternalMessageReceive)) {
            return false;
        }
        InternalMessageReceive other = (InternalMessageReceive) object;
        if ((this.receiveMsgId == null && other.receiveMsgId != null) || (this.receiveMsgId != null && !this.receiveMsgId.equals(other.receiveMsgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "internalMessaging.entity.ReceiverInfo[ receiveMsgId=" + receiveMsgId + " ]";
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the opened
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * @param opened the opened to set
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    /**
     * @return the senderId
     */
    public long getSenderId() {
        return senderId;
    }

    /**
     * @param senderId the senderId to set
     */
    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    /**
     * @return the receiverId
     */
    public long getReceiverId() {
        return receiverId;
    }

    /**
     * @param receiverId the receiverId to set
     */
    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
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

}
