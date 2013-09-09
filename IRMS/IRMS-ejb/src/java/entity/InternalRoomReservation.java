/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class InternalRoomReservation extends RoomReservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Staff staff;
    @ManyToOne
    private InternalRoomRequest internalRoomRequest;

    public InternalRoomReservation() {
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public InternalRoomRequest getInternalRoomRequest() {
        return internalRoomRequest;
    }

    public void setInternalRoomRequest(InternalRoomRequest internalRoomRequest) {
        this.internalRoomRequest = internalRoomRequest;
    }
   
    @Override
    public Long getId() {
        return id;
    }

    @Override
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
        if (!(object instanceof InternalRoomReservation)) {
            return false;
        }
        InternalRoomReservation other = (InternalRoomReservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.InternalRoomReservation[ id=" + id + " ]";
    }
    
}
