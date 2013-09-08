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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class HotelTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private AccommodationBill accommodationBill;
    @OneToOne(mappedBy="hotelTransaction")
    private HotelPayment hotelPayment;
    private double creditCardNumber; 
    private double amount;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateTime;
    private String status;

    public AccommodationBill getAccommodationBill() {
        return accommodationBill;
    }

    public void setAccommodationBill(AccommodationBill accommodationBill) {
        this.accommodationBill = accommodationBill;
    }

    public HotelPayment getHotelPayment() {
        return hotelPayment;
    }

    public void setHotelPayment(HotelPayment hotelPayment) {
        this.hotelPayment = hotelPayment;
    }

    public double getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(double creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
        if (!(object instanceof HotelTransaction)) {
            return false;
        }
        HotelTransaction other = (HotelTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Transaction[ id=" + id + " ]";
    }
    
}
