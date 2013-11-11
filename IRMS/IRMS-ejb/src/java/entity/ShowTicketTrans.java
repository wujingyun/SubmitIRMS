/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author WU JINGYUN
 */
@Entity
public class ShowTicketTrans implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     @ManyToOne
    private Customer customer;
     @OneToMany(mappedBy="showTicketTrans", cascade= CascadeType.ALL)
        private List<TicketSeat> ticketSeat= new ArrayList();
        @OneToOne(cascade = {CascadeType.ALL})
    private OnlinePayment onlinePayment; 
    public ShowTicketTrans() {
    }

      public void create(Customer customer, List<TicketSeat> ticketSeat) {
    this.setCustomer(customer);
    this.setTicketSeat(ticketSeat);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<TicketSeat> getTicketSeat() {
        return ticketSeat;
    }

    public void setTicketSeat(List<TicketSeat> ticketSeat) {
        this.ticketSeat = ticketSeat;
    }

    public void setOnlinePayment(OnlinePayment onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OnlinePayment getOnlinePayment() {
        return onlinePayment;
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
        if (!(object instanceof ShowTicketTrans)) {
            return false;
        }
        ShowTicketTrans other = (ShowTicketTrans) object;
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
