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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbRequest implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String venue;
    private String customerName;
    private int capacity;
    private String email;
     
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date bookingDate;
    private String contact;
    private int duration;
    
    @OneToOne
    private FbBanquetReservation reservation;
    
    public FbRequest(){}
    
    public void createRequest(String venue, String customerName, int capacity, Date bookingDate, int duration,String contact, String email){
        this.setVenue(venue);
        this.setCustomerName(customerName);
        this.setCapacity(capacity);
        this.setBookingDate(bookingDate);
        this.setDuration(duration);
        this.setContact(contact);
        this.setEmail(email);
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FbBanquetReservation getReservation() {
        return reservation;
    }

    public void setReservation(FbBanquetReservation reservation) {
        this.reservation = reservation;
    }

    
}
