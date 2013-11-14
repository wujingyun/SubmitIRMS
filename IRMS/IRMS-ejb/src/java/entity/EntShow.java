package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 * @author Jiao Shen
 */

@Entity
public class EntShow implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long showId;
    //@Id
    private String showName;
    private String showVenue;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date showDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startSalesDate;
    //private int startTime;
    //private int endTime;
    private Long duration;
    private String description;
    
    @OneToOne(mappedBy="entShow")
    private Venue venue;
    @OneToOne(mappedBy="entShow")
    private ShowRequest showRequest;
    @OneToOne(mappedBy="entShow")
    private ShowContract showContract;
    @OneToMany(mappedBy="entShow", cascade=CascadeType.ALL)
    private List<TicketCat> ticketCats = new ArrayList<TicketCat>();

    public EntShow() {}
    
    public void createShow (String showName, String showVenue, Date showDate, Date startSalesDate, Long duration, String description) {
        this.setShowName(showName);
        this.setShowVenue(showVenue);
        this.setShowDate(showDate);
        this.setStartSalesDate(startSalesDate);
        this.setDuration(duration);
        this.setDescription(description);
    }
    
    /*
    public void addTicketCat (TicketCat ticketCat){
        ticketCats.add(ticketCat);
    }
    */

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowVenue() {
        return showVenue;
    }

    public void setShowVenue(String showVenue) {
        this.showVenue = showVenue;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Date getStartSalesDate() {
        return startSalesDate;
    }

    public void setStartSalesDate(Date startSalesDate) {
        this.startSalesDate = startSalesDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public ShowRequest getShowRequest() {
        return showRequest;
    }

    public void setShowRequest(ShowRequest showRequest) {
        this.showRequest = showRequest;
    }

    public ShowContract getShowContract() {
        return showContract;
    }

    public void setShowContract(ShowContract showContract) {
        this.showContract = showContract;
    }

    public List<TicketCat> getTicketCats() {
        return ticketCats;
    }

    public void setTicketCats(List<TicketCat> ticketCats) {
        this.ticketCats = ticketCats;
    }

}