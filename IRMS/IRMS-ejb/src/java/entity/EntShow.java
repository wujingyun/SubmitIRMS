package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
public class  EntShow implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long showId;
    private String showName;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date showDate;
    private String showVenue;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startSalesDate;
    private Long duration;
    private String language;
    
    @OneToOne
    private Venue venue;
    @OneToOne
    private ShowRequest showRequest;
    @OneToOne
    private ShowContract showContract;
    @OneToMany(mappedBy="entshow")
    private List<TicketCat> ticketCat = new ArrayList();
    
    
    public  EntShow() {}
    
    public void createShow (String showName, Date showDate, String showVenue, Date startSalesDate, Long duration, String language) {
        this.setShowName(showName);
        this.setShowDate(showDate);
        this.setShowVenue(showVenue);
        this.setStartSalesDate(startSalesDate);
        this.setDuration(duration);
        this.setLanguage(language);
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void setShowRequest(ShowRequest showRequest) {
        this.showRequest = showRequest;
    }

    public void setTicketCat(List<TicketCat> ticketCat) {
        this.ticketCat = ticketCat;
    }

    public Venue getVenue() {
        return venue;
    }

    public ShowRequest getShowRequest() {
        return showRequest;
    }

    public List<TicketCat> getTicketCat() {
        return ticketCat;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public String getShowVenue() {
        return showVenue;
    }

    public void setShowVenue(String showVenue) {
        this.showVenue = showVenue;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ShowContract getShowContract() {
        return showContract;
    }

    public void setShowContract(ShowContract showContract) {
        this.showContract = showContract;
    }
        
}