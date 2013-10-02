/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author WU JINGYUN
 */
@Entity
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;
    private String role_name;
    private String description;
    @ManyToMany(cascade={CascadeType.PERSIST})
    public Collection<AccessRight> accessright = new ArrayList<AccessRight>();

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }
    
    public Collection<AccessRight> getAccessright() {
        return accessright;
    }

    public void setAccessright(Collection<AccessRight> accessright) {
        this.accessright = accessright;
    }
    
    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.role_name == null && other.role_name != null) || (this.role_name != null && !this.role_name.equals(other.role_name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ET_UserRole[ role_name=" + role_name + " ]";
    }
    
}
