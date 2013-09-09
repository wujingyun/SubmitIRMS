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
    private double RentalFee;
    private double Commision;
    private boolean billStatus;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateIssued;
    
    public ShopBill(){}
    
    public void createBill(double RentalFee,double Commision){
        this.setRentalFee(RentalFee);
        this.setCommision(Commision);
        this.setBillStatus(true);
    }
    
    
    public Long getBillID() {
        return BillID;
    }

    public void setBillID(Long BillID) {
        this.BillID = BillID;
    }
    
    public double getRentalFee(){
        return RentalFee;
    }
    public void setRentalFee(double RentalFee){
        this.RentalFee=RentalFee;
    }    
    
    public double getCommision(){
        return Commision;
    }
    public void setCommision(double Commision){
        this.Commision=Commision;
    }
    
    public Calendar getDateIssued(){
        return dateIssued;
    }
    public void setDateIssued(Calendar dateIssued){
        this.dateIssued=dateIssued;
    }

    public boolean isBillStatus() {
        return billStatus;
    }

    public void setBillStatus(boolean billStatus) {
        this.billStatus = billStatus;
    }
    
}
