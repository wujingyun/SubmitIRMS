/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class RoomReservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private Collection<Room> rooms=new ArrayList();
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Staff staff;
    private String hotelName;
    private String roomType;
    private Integer quantity;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateReserved;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar startDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar endDate;
    private String type;//Confirmed,Guaranteed,Pending, Cancelled
    private String source;//Internal or External
    private String remark;//special requests
    private double total;
    private String paymentStatus;//Full/Partial/Nil
    private double paymentAmount;
    private String roomAllocationStatus;//Allocated/Pending

    public RoomReservation() {
    }

    public void create(String hotelName, String roomType, Integer quantity, Calendar startDate, Calendar endDate, String remark) {
        this.setHotelName(hotelName);
        this.setRoomType(roomType);
        this.setQuantity(quantity);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setRemark(remark);
        this.setType("Pending");
        this.setPaymentStatus("Pending");
        this.setRoomAllocationStatus("Pending");
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Calendar getDateReserved() {
        return dateReserved;
    }

    public void setDateReserved(Calendar dateReserved) {
        this.dateReserved = dateReserved;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getRoomAllocationStatus() {
        return roomAllocationStatus;
    }

    public void setRoomAllocationStatus(String roomAllocationStatus) {
        this.roomAllocationStatus = roomAllocationStatus;
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
        if (!(object instanceof RoomReservation)) {
            return false;
        }
        RoomReservation other = (RoomReservation) object;
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
