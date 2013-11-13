/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbBanquetReservation implements Serializable {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateReserved;
    private String purpose;
    private String customerName;
    private String contact;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEnded;
    private String customerEmail;
    
    @ManyToOne
    private FbBanquet banquet;
    
    
    public FbBanquetReservation() {
    }
    
    public void createReservation(Date dateReserved, Date dateEnded ,String purpose,String contact,String customerName,String customerEmail){
        this.setDateReserved(dateReserved);
        this.setDateEnded(dateEnded);
        this.setPurpose(purpose);
        this.setContact(contact);
        this.setCustomerName(customerName);
        this.setCustomerEmail(customerEmail);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateReserved() {
        return dateReserved;
    }

    public void setDateReserved(Date dateReserved) {
        this.dateReserved = dateReserved;
    }

    public Date getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(Date dateEnded) {
        this.dateEnded = dateEnded;
    }

   

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public FbBanquet getBanquet() {
        return banquet;
    }

    public void setBanquet(FbBanquet banquet) {
        this.banquet = banquet;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

   

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
}
