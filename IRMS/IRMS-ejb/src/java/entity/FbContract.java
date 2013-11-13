/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author wangxiahao
 */
@Entity
public class FbContract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
 
    private String contractStatus;
    private String contractCreator;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date bookingDate;   

    @OneToOne
    private FbQuotation quote;

    public FbContract() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FbQuotation getQuote() {
        return quote;
    }

    public void setQuote(FbQuotation quote) {
        this.quote = quote;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    } 

    public String getContractCreator() {
        return contractCreator;
    }

    public void setContractCreator(String contractCreator) {
        this.contractCreator = contractCreator;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    
}
