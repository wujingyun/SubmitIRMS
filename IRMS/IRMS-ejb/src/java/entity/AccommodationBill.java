/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class AccommodationBill implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateTime;
    @OneToOne
    private RoomReservation roomReservation;
    @OneToMany
    private Collection<DiscountScheme> discountSchemes;
    @OneToMany
    private Collection<MiniBarItem> miniBarItems;
    @OneToMany(mappedBy="accommodationBill")
    private Collection<RoomServiceOrder> roomServiceOrders;
    @OneToOne(mappedBy="accommodationBill")
    private HotelPayment hotelPayment;
    private double overseasCall[][];
    private String incidentalCharges[][];
    private double total;

    public AccommodationBill() {
    }
    
    public void create(){    
        this.dateTime=Calendar.getInstance();
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }
    
    public RoomReservation getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(RoomReservation roomReservation) {
        this.roomReservation = roomReservation;
    }

    public Collection<DiscountScheme> getDiscountSchemes() {
        return discountSchemes;
    }

    public void setDiscountSchemes(Collection<DiscountScheme> discountSchemes) {
        this.discountSchemes = discountSchemes;
    }

    public Collection<MiniBarItem> getMiniBarItems() {
        return miniBarItems;
    }

    public void setMiniBarItems(Collection<MiniBarItem> miniBarItems) {
        this.miniBarItems = miniBarItems;
    }

    public Collection<RoomServiceOrder> getRoomServiceOrders() {
        return roomServiceOrders;
    }

    public void setRoomServiceOrders(Collection<RoomServiceOrder> roomServiceOrders) {
        this.roomServiceOrders = roomServiceOrders;
    }

    public HotelPayment getHotelPayment() {
        return hotelPayment;
    }

    public void setHotelPayment(HotelPayment hotelPayment) {
        this.hotelPayment = hotelPayment;
    }

    public double[][] getOverseasCall() {
        return overseasCall;
    }

    public void setOverseasCall(double[][] overseasCall) {
        this.overseasCall = overseasCall;
    }

    public String[][] getIncidentalCharges() {
        return incidentalCharges;
    }

    public void setIncidentalCharges(String[][] incidentalCharges) {
        this.incidentalCharges = incidentalCharges;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
        if (!(object instanceof AccommodationBill)) {
            return false;
        }
        AccommodationBill other = (AccommodationBill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AccommodationBill[ id=" + id + " ]";
    }
    
}
