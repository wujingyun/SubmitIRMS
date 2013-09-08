/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author wangxiahao
 */
@Entity
public class ShopBill implements Serializable {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BillID;
    private String RentalFee;
    private String Commision;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateIssued;
    
    public ShopBill(){}
    
    public void createBill(String RentalFee,String Commision){
        this.setRentalFee(RentalFee);
        this.setCommision(Commision);
    }
    
    
    public Long getBillID() {
        return BillID;
    }

    public void setBillID(Long BillID) {
        this.BillID = BillID;
    }
    
    public String getRentalFee(){
        return RentalFee;
    }
    public void setRentalFee(String RentalFee){
        this.RentalFee=RentalFee;
    }    
    
    public String getCommision(){
        return Commision;
    }
    public void setCommision(String Commision){
        this.Commision=Commision;
    }
    
    public Calendar getDateIssued(){
        return dateIssued;
    }
    public void setDateIssued(Calendar dateIssued){
        this.dateIssued=dateIssued;
    }
    
}
