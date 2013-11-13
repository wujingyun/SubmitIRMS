/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbReservation implements Serializable {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String customerName;
    private String customerContact;
    private boolean status;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date reservationDate;
    private int NoonNum;
    private int DinnerNum;
    private int SupperNum;
    private String slot;
    
    
    
    @ManyToOne
    private FbRestaurant restaurant;
    
    public FbReservation() {
    }
    
    public void createReservation(String customerName,String customerContact, Date reservationDate,String slot){
        this.setCustomerContact(customerContact);
        this.setCustomerName(customerName);
        this.setReservationDate(reservationDate);
        this.setSlot(slot);
        this.setStatus(true);
       
        this.setNoonNum(10);
         this.setDinnerNum(20);
        this.setSupperNum(15);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getNoonNum() {
        return NoonNum;
    }

    public void setNoonNum(int NoonNum) {
        this.NoonNum = NoonNum;
    }

    public int getDinnerNum() {
        return DinnerNum;
    }

    public void setDinnerNum(int DinnerNum) {
        this.DinnerNum = DinnerNum;
    }

    public int getSupperNum() {
        return SupperNum;
    }

    public void setSupperNum(int SupperNum) {
        this.SupperNum = SupperNum;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }


    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public FbRestaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(FbRestaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

   
}
