/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbRestaurant implements Serializable {
   
    @Id
    private String restaurantName;
    
    private String Contact;
    
    
    @OneToMany(mappedBy = "restaurant")
    private Collection<FbReservation> reservation;
         
    @OneToMany(mappedBy="restaurant")
    private Collection<FbTable> tables;
    
    @OneToMany(mappedBy="restaurant")
   private Collection<FbCombo> combo;
    
    @OneToMany
    private Collection<FbRDish> dish;
    
    public FbRestaurant() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public Collection<FbTable> getTables() {
        return tables;
    }

    public void setTables(Collection<FbTable> tables) {
        this.tables = tables;
    }   

    public Collection<FbCombo> getCombo() {
       return combo;
    }

    public void setCombo(Collection<FbCombo> combo) {
        this.combo = combo;
   }

    public Collection<FbRDish> getDish() {
        return dish;
    }

    public void setDish(Collection<FbRDish> dish) {
        this.dish = dish;
    }

    public Collection<FbReservation> getReservation() {
        return reservation;
    }

    public void setReservation(Collection<FbReservation> reservation) {
        this.reservation = reservation;
    }
   
}
