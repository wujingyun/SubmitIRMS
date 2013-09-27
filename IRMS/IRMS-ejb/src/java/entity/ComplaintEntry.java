/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class ComplaintEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ComplaintRegister complaintRegister;
    private String customerName;
    private Integer roomNumber;
    private String contact;
    private String description;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateTime;
    private String status;
    
    public void create(String customerName, Integer roomNumber, String contact, String description, String status){
        this.setCustomerName(customerName);
        this.setRoomNumber(roomNumber);
        this.setContact(contact);
        this.setDescription(description);
        this.setStatus(status);
        this.setDateTime(Calendar.getInstance());
    }

    public ComplaintRegister getComplaintRegister() {
        return complaintRegister;
    }

    public void setComplaintRegister(ComplaintRegister complaintRegister) {
        this.complaintRegister = complaintRegister;
    }
    
     public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof ComplaintEntry)) {
            return false;
        }
        ComplaintEntry other = (ComplaintEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ComplaintEntry[ id=" + id + " ]";
    }
    
}
