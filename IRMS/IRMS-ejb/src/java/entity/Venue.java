package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author Jiao Shen
 */

@Entity
public class Venue implements Serializable {

    @Id
    //@GeneratedValue (strategy = GenerationType.IDENTITY)
    //private Long venueId;
    private String venueName;
    private String venueAddr;
    private double prevailingRates;
    
    @OneToOne
    private EntShow entShow;
    
    public Venue() {}
    
    public void createVenue (String venueName,String venueAddr, double prevailingRates) {
        this.setVenueName(venueName);
        this.setVenueAddr(venueAddr);
        this.setPrevailingRates(prevailingRates);
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
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

    public EntShow getEntShow() {
        return entShow;
    }

    public void setEntShow(EntShow entShow) {
        this.entShow = entShow;
    }

}