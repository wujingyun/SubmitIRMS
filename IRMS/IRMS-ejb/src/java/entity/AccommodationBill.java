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
import structure.IncidentalCharge;
import structure.MiniBarConsumption;

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
    private Collection<MiniBarConsumption> miniBarConsumptions;
    @OneToMany(mappedBy="accommodationBill")
    private Collection<RoomServiceOrder> roomServiceOrders;
    @OneToOne(mappedBy="accommodationBill")
    private HotelPayment hotelPayment;
    private double overseasCallCharge;
    private Collection<IncidentalCharge> incidentalCharges;
    private double total;

    public AccommodationBill() {
    }
    
    public void create(){
        this.setOverseasCallCharge(0);
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

    public Collection<MiniBarConsumption> getMiniBarConsumptions() {
        return miniBarConsumptions;
    }

    public void setMiniBarConsumptions(Collection<MiniBarConsumption> miniBarConsumptions) {
        this.miniBarConsumptions = miniBarConsumptions;
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

    public double getOverseasCallCharge() {
        return overseasCallCharge;
    }

    public void setOverseasCallCharge(double overseasCallCharge) {
        this.overseasCallCharge = overseasCallCharge;
    }

    public Collection<IncidentalCharge> getIncidentalCharges() {
        return incidentalCharges;
    }

    public void setIncidentalCharges(Collection<IncidentalCharge> incidentalCharges) {
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
