package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Jiao Shen
 */

@Entity
public class TicketSeat implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long seatId;
    private boolean seatStatus;
   
    @ManyToOne
        private ShowTicketTrans showTicketTrans;
    @ManyToOne
    private TicketCat ticketCat;
    
    public TicketSeat() {}
    
    public void createTicketSeat (boolean seatStatus) {
     
        this.setSeatStatus(seatStatus);
    }

    public long getSeatId() {
        return seatId;
    }

    public void setSeatId(long seatId) {
        this.seatId = seatId;
    }

    public boolean getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(boolean seatStatus) {
        this.seatStatus = seatStatus;
    }

    public void setShowTicketTrans(ShowTicketTrans showTicketTrans) {
        this.showTicketTrans = showTicketTrans;
    }

    public void setTicketCat(TicketCat ticketCat) {
        this.ticketCat = ticketCat;
    }

    public ShowTicketTrans getShowTicketTrans() {
        return showTicketTrans;
    }

    public TicketCat getTicketCat() {
        return ticketCat;
    }

}