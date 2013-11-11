package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private Calendar showDate;
    private String showVenue;
    private long duration;
    private String language;
    private String organizer;
    
   // @OneToOne(mappedBy="show")
   // private Show show;
    
    public ShowRequest() {}
    
    public void createShow (String showName, Calendar showDate, String showVenue, Long duration, String language, String organizer) {
        this.setShowName(showName);
        this.setShowDate(showDate);
        this.setShowVenue(showVenue);
        this.setDuration(duration);
        this.setLanguage(language);
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

    public Calendar getShowDate() {
        return showDate;
    }

    public void setShowDate(Calendar showDate) {
        this.showDate = showDate;
    }

    public String getShowVenue() {
        return showVenue;
    }

    public void setShowVenue(String showVenue) {
        this.showVenue = showVenue;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

}