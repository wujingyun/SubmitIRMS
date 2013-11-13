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
import javax.persistence.ManyToOne;

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbTable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableNo;
    
    private int tableCapacity;
    private boolean NoonStatus;
    private boolean DinnerStatus;
    private boolean SupperStatus;
    private boolean inReservation;
   
    @ManyToOne 
    private FbRestaurant restaurant;
    
    public FbTable() {
    }
    public void createTable (int tableCapacity){
        this.setTableCapacity(tableCapacity);
        this.setNoonStatus(true);
        this.setDinnerStatus(true);
        this.setSupperStatus(true);
        this.setInReservation(false);
    }

    public FbRestaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(FbRestaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    
    public Long getTableNo() {
        return tableNo;
    }

    public void setTableNo(Long tableNo) {
        this.tableNo = tableNo;
    }

    public int getTableCapacity() {
        return tableCapacity;
    }

    public void setTableCapacity(int tableCapacity) {
        this.tableCapacity = tableCapacity;
    }

    public boolean isNoonStatus() {
        return NoonStatus;
    }

    public void setNoonStatus(boolean NoonStatus) {
        this.NoonStatus = NoonStatus;
    }

    public boolean isDinnerStatus() {
        return DinnerStatus;
    }

    public void setDinnerStatus(boolean DinnerStatus) {
        this.DinnerStatus = DinnerStatus;
    }

    public boolean isSupperStatus() {
        return SupperStatus;
    }

    public void setSupperStatus(boolean SupperStatus) {
        this.SupperStatus = SupperStatus;
    }

    

   

    public boolean isInReservation() {
        return inReservation;
    }

    public void setInReservation(boolean inReservation) {
        this.inReservation = inReservation;
    }

}
