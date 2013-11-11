/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author WU JINGYUN
 */
@Entity
public class DirectPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double paymentAmount;
    private String paymentStatus;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateOfPayment;
    private String paymentMode;
    private String creditCardinfo;
    public DirectPayment() {
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setDateOfPayment(Calendar dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setCreditCardinfo(String creditCardinfo) {
        this.creditCardinfo = creditCardinfo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Calendar getDateOfPayment() {
        return dateOfPayment;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public String getCreditCardinfo() {
        return creditCardinfo;
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
        if (!(object instanceof DirectPayment)) {
            return false;
        }
        DirectPayment other = (DirectPayment) object;
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
