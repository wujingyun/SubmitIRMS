package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 * @author Jiao Shen
 */

@Entity
public class ShowRequest implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long requestId;
    private String showName;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date showDate;
    private String showVenue;
    private String organizer;
    
    @OneToOne
    private EntShow entShow;
    
    public ShowRequest() {}
    
    public void createShow (String showName, Date showDate, String showVenue, String organizer) {
        this.setShowName(showName);
        this.setShowDate(showDate);
        this.setShowVenue(showVenue);
        this.setOrganizer(organizer);
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public EntShow getEntShow() {
        return entShow;
    }

    public void setEntShow(EntShow entShow) {
        this.entShow = entShow;
    }

}