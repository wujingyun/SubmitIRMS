/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer roomNumber;
    @ManyToOne
    private Hotel hotel;
    private String type;
    private String description;
    private double rate;
    private String availabilityStatus;//available/reserved/rennovation,etc
    private String housekeepingStatus;
    @ManyToMany
    private Collection<RoomReservation> roomReservations = new ArrayList();

    public Room() {
    }

    public void create(Integer roomNumber, String type, String description, double rate) {
        this.setRoomNumber(roomNumber);
        this.setType(type);
        this.setDescription(description);
        this.setRate(rate);
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getHousekeepingStatus() {
        return housekeepingStatus;
    }

    public void setHousekeepingStatus(String housekeepingStatus) {
        this.housekeepingStatus = housekeepingStatus;
    }

    public Collection<RoomReservation> getRoomReservations() {
        return roomReservations;
    }

    public void setRoomReservations(Collection<RoomReservation> roomReservations) {
        this.roomReservations = roomReservations;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomNumber != null ? roomNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomNumber == null && other.roomNumber != null) || (this.roomNumber != null && !this.roomNumber.equals(other.roomNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Room[ id=" + roomNumber + " ]";
    }
}
