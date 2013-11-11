package entity;

import entity.EntShow;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author Jiao Shen
 */

@Entity
public class Venue implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long venueId;
    private String venueName;
    //private Calendar date;
    private String venueStatus;
    private String venueAddr;
    private double prevailingRates;
    
    //@OneToOne(mappedBy="show")
    //private Show show;
    
    public Venue() {}
    
    public void createVenue (String venueName, String venueStatus, String venueAddr, double prevailingRates) {
        //this.setVenueId(venueId);
        this.setVenueName(venueName);
        this.setVenueStatus(venueStatus);
        this.setVenueAddr(venueAddr);
        this.setPrevailingRates(prevailingRates);
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueStatus() {
        return venueStatus;
    }

    public void setVenueStatus(String venueStatus) {
        this.venueStatus = venueStatus;
    }

    public String getVenueAddr() {
        return venueAddr;
    }

    public void setVenueAddr(String venueAddr) {
        this.venueAddr = venueAddr;
    }

    public double getPrevailingRates() {
        return prevailingRates;
    }

    public void setPrevailingRates(double prevailingRates) {
        this.prevailingRates = prevailingRates;
    }

}