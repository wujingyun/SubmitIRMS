/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author WU JINGYUN
 */
@Entity
public class AttractionTicketTrans implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     @ManyToOne
    private Customer customer;
   private int quantity;

    @Temporal(javax.persistence.TemporalType.DATE)
     private Calendar purchaseDate;
    @Temporal(javax.persistence.TemporalType.DATE)
     private Calendar attendDate;
    @OneToOne (cascade = {CascadeType.ALL})
       private AttractionTicket attractionTicket; 
    @OneToOne(cascade = {CascadeType.ALL})
    private OnlinePayment onlinePayment; 
     private double amount;
    public AttractionTicketTrans() {
    }
public void createAttractionTicketTrans (Customer customer,int quantity, Calendar purchaseDate, Calendar attendDate,  AttractionTicket attractionTicket) {
        this.setCustomer(customer);
        this.setAttractionTicket(attractionTicket);
        this.setPurchaseDate(purchaseDate);
        this.setQuantity(quantity);
        this.setAttendDate(attendDate);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Calendar attendDate) {
        this.attendDate = attendDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOnlinePayment(OnlinePayment onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchaseDate(Calendar purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setAttractionTicket(AttractionTicket attractionTicket) {
        this.attractionTicket = attractionTicket;
    }

    public Calendar getPurchaseDate() {
        return purchaseDate;
    }

    public AttractionTicket getAttractionTicket() {
        return attractionTicket;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OnlinePayment getOnlinePayment() {
        return onlinePayment;
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
        if (!(object instanceof AttractionTicketTrans)) {
            return false;
        }
        AttractionTicketTrans other = (AttractionTicketTrans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomReservation[ id=" + id + " ]";
    }

    
}
