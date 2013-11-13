/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbQuotation implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int headCount;
    private double totalPrice;
    
    @OneToOne
    private FbBanquetReservation reservation;
    @OneToOne
    private FbOrderList orderList;
    

    public FbQuotation() {
    }
    
    public void createQuotation(int headCount, double totalPrice){
        this.setHeadCount(headCount);
        this.setTotalPrice(totalPrice);
    }

    public FbBanquetReservation getReservation() {
        return reservation;
    }

    public void setReservation(FbBanquetReservation reservation) {
        this.reservation = reservation;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public FbOrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(FbOrderList orderList) {
        this.orderList = orderList;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHeadCount() {
        return headCount;
    }

    public void setHeadCount(int headCount) {
        this.headCount = headCount;
    }

   
    
}
