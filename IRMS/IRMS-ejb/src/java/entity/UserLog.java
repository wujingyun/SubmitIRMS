/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
public class UserLog implements Serializable {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logTime;
    private String userName;
     private String description;
     @ManyToOne
    private UserAccount useraccount;

 public void create(String logTime, String userName, String description) {
        this.setLogTime(logTime);
        this.setUserName(userName);
        this.setDescription(description);
        
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUseraccount(UserAccount useraccount) {
        this.useraccount = useraccount;
    }

    public Long getId() {
        return id;
    }

    public String getLogTime() {
        return logTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getDescription() {
        return description;
    }

    public UserAccount getUseraccount() {
        return useraccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserLog)) {
            return false;
        }
        UserLog other = (UserLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UserLog[ id=" + id + " ]";
    }
    
}
