package entity;

import java.io.Serializable;
import java.util.Calendar;
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
public class ShowContract implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long contractId;
    //@Id
    private String showName;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date showDate;
    private String showVenue;
    private Long staffId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date signDate;
    //private String status; // "reserved" or "signed"
    
    @OneToOne
    private EntShow entShow;
    
    public ShowContract() {}
    
    public void createShowContract (String showName, Date showDate, String showVenue, Long staffId, Date signDate) {
        this.setShowName(showName);
        this.setShowDate(showDate);
        this.setShowVenue(showVenue);
        this.setStaffId(staffId);
        this.setSignDate(signDate);
        //this.setStatus(status);
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

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public EntShow getEntShow() {
        return entShow;
    }

    public void setEntShow(EntShow entShow) {
        this.entShow = entShow;
    }

}