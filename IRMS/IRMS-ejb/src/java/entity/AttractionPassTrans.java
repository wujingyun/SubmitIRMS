/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author WU JINGYUN
 */
@Entity
public class AttractionPassTrans implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     @ManyToOne
    private Customer customer;
     private int quantity;

    @Temporal(javax.persistence.TemporalType.DATE)
     private Calendar purchaseDate;
    @OneToOne (cascade = {CascadeType.ALL})
       private AttractionPass attractionPass; 
    @OneToOne(cascade = {CascadeType.ALL})
    private OnlinePayment onlinePayment; 
    private double amount;
    public AttractionPassTrans() {
    }

     public void createAttractionPassTrans (Customer customer,int quantity, Calendar purchaseDate, AttractionPass attractionPass) {
        this.setCustomer(customer);
        this.setAttractionPass(attractionPass);
        this.setPurchaseDate(purchaseDate);
        this.setQuantity(quantity);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public AttractionPass getAttractionPass() {
        return attractionPass;
    }

    public void setAttractionPass(AttractionPass attractionPass) {
        this.attractionPass = attractionPass;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOnlinePayment(OnlinePayment onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OnlinePayment getOnlinePayment() {
        return onlinePayment;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchaseDate(Calendar purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public Calendar getPurchaseDate() {
        return purchaseDate;
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
        if (!(object instanceof AttractionPassTrans)) {
            return false;
        }
        AttractionPassTrans other = (AttractionPassTrans) object;
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
