/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class ComplaintRegister implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy="complaintRegister")
    private Hotel hotel;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateCreated;
    @OneToMany(mappedBy="complaintRegister")
    private Collection<ComplaintEntry> complaintEntries=new ArrayList();

    public ComplaintRegister() {
    }
    
    public void create(){
        this.setDateCreated(Calendar.getInstance());
    }
    
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Collection<ComplaintEntry> getComplaintEntries() {
        return complaintEntries;
    }

    public void setComplaintEntries(Collection<ComplaintEntry> complaintEntries) {
        this.complaintEntries = complaintEntries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof ComplaintRegister)) {
            return false;
        }
        ComplaintRegister other = (ComplaintRegister) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ComplaintRegister[ id=" + id + " ]";
    }
    
}
