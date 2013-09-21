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


@Entity
public class AccessRight implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ar_id;
    private String ar_name;
    private String description;
    

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Long getAr_Id() {
        return ar_id;
    }

    public void setAr_Id(Long ar_id) {
        this.ar_id = ar_id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ar_id != null ? ar_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessRight)) {
            return false;
        }
        AccessRight other = (AccessRight) object;
        if ((this.ar_id == null && other.ar_id != null) || (this.ar_id != null && !this.ar_id.equals(other.ar_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ET_AccessRight[ ar_id=" + ar_id + " ]";
    }
    
}
