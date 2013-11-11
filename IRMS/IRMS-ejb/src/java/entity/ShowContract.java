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
    private String showName;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar showDate;
    private String showVenue;
    private Long staffId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar signDate;
    
   // @OneToOne(mappedBy="show")
   // private Show show;
    
    public ShowContract() {}
    
    public void createShowContract (String showName, Calendar showDate, String showVenue, Calendar signDate) {
        //this.setContractId(contractId);
        this.setShowName(showName);
        this.setShowDate(showDate);
        this.setShowVenue(showVenue);
        this.setSignDate(signDate);
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
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

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Calendar getSignDate() {
        return signDate;
    }

    public void setSignDate(Calendar signDate) {
        this.signDate = signDate;
    }
    
}