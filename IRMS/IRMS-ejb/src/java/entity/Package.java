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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author WU JINGYUN
 */
@Entity
public class Package implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private double packagePrice;
   private String condition;
    @Temporal(javax.persistence.TemporalType.DATE)
   private Calendar createDate;
    @Temporal(javax.persistence.TemporalType.DATE)
   private Calendar validDate;
   private int packageAmount;
    @OneToOne(cascade = {CascadeType.ALL})
    private RoomReservation roomReservation; 
     @OneToOne(cascade = {CascadeType.ALL})
    private AttractionTicketTrans attractionTicketTrans; 
      @OneToOne(cascade = {CascadeType.ALL})
    private ShowTicketTrans  showTicketTrans; 
      
      
    public void create(double packagePrice,String condition,Calendar createDate,Calendar validDate,int packageAmount) {
   this.setPackagePrice(packagePrice);
   this.setCondition(condition);
   this.setCreateDate(createDate);
   this.setValidDate(validDate);
   this.setPackageAmount(packageAmount);
      
    }
 
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(double packagePrice) {
        this.packagePrice = packagePrice;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public void setValidDate(Calendar validDate) {
        this.validDate = validDate;
    }

    public void setPackageAmount(int packageAmount) {
        this.packageAmount = packageAmount;
    }

    public void setRoomReservation(RoomReservation roomReservation) {
        this.roomReservation = roomReservation;
    }

    public void setAttractionTicketTrans(AttractionTicketTrans attractionTicketTrans) {
        this.attractionTicketTrans = attractionTicketTrans;
    }

    public void setShowTicketTrans(ShowTicketTrans showTicketTrans) {
        this.showTicketTrans = showTicketTrans;
    }

    public String getCondition() {
        return condition;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public Calendar getValidDate() {
        return validDate;
    }

    public int getPackageAmount() {
        return packageAmount;
    }

    public RoomReservation getRoomReservation() {
        return roomReservation;
    }

    public AttractionTicketTrans getAttractionTicketTrans() {
        return attractionTicketTrans;
    }

    public ShowTicketTrans getShowTicketTrans() {
        return showTicketTrans;
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
        if (!(object instanceof Package)) {
            return false;
        }
        Package other = (Package) object;
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
