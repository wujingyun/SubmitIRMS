/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class RoomServiceOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hotelName;
    @OneToMany(mappedBy="roomServiceOrder")
    private List<RoomService> roomServices;
    @ManyToOne
    private AccommodationBill accommodationBill;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar orderTime;
    private double total;

    public RoomServiceOrder() {
    }  
    
    public void create(){
        this.setOrderTime(Calendar.getInstance());
    }
    public Calendar getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Calendar orderTime) {
        this.orderTime = orderTime;
    }

    public List<RoomService> getRoomServices() {
        return roomServices;
    }

    public void setRoomServices(List<RoomService> roomServices) {
        this.roomServices = roomServices;
    }

    public AccommodationBill getAccommodationBill() {
        return accommodationBill;
    }

    public void setAccommodationBill(AccommodationBill accommodationBill) {
        this.accommodationBill = accommodationBill;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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
        if (!(object instanceof RoomServiceOrder)) {
            return false;
        }
        RoomServiceOrder other = (RoomServiceOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomServiceOrder[ id=" + id + " ]";
    }
    
}
