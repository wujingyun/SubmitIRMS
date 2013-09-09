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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class InternalRoomRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Staff staff;
    @OneToMany(mappedBy="internalRoomRequest")
    private Collection<InternalRoomReservation> internalRoomReservations;
    private String hotelName;
    private String roomRequested[][];
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar startDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar endDate;
    private Integer headcount;
    private String remark;

    public InternalRoomRequest() {
    }
    
    public void create(String hotelName, Calendar startDate, Calendar endDate, Integer headcount, String remark){
        this.setHotelName(hotelName);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setHeadcount(headcount);
        this.setRemark(remark);
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Collection<InternalRoomReservation> getInternalRoomReservations() {
        return internalRoomReservations;
    }

    public void setInternalRoomReservations(Collection<InternalRoomReservation> internalRoomReservations) {
        this.internalRoomReservations = internalRoomReservations;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String[][] getRoomRequested() {
        return roomRequested;
    }

    public void setRoomRequested(String[][] roomRequested) {
        this.roomRequested = roomRequested;
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

    public Integer getHeadcount() {
        return headcount;
    }

    public void setHeadcount(Integer headcount) {
        this.headcount = headcount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        if (!(object instanceof InternalRoomRequest)) {
            return false;
        }
        InternalRoomRequest other = (InternalRoomRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.InternalRoomRequest[ id=" + id + " ]";
    }
    
}
