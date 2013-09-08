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
import javax.persistence.OneToOne;

/**
 *
 * @author wangxiahao
 */
@Entity
public class Unit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String UnitNo;
    private boolean unitAvailability;
    
    
    @ManyToOne
    private Mall mall;
    
    @OneToOne
    private Shop shop;
    
    public Long getId() {
        return id;
    }
    
    public Unit(){}
    
    public void createUnit(String UnitNo){
        this.UnitNo=UnitNo;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUnitNo(){
        return UnitNo;
    }
    
    public void setUnitNo(String UnitNo){
        this.UnitNo=UnitNo;
    }
    
    public Mall getMall(){
        return mall;
    }
    
    public void setMall(Mall mall){
        this.mall=mall;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public boolean isUnitAvailability() {
        return unitAvailability;
    }

    public void setUnitAvailability(boolean unitAvailability) {
        this.unitAvailability = unitAvailability;
    }
    
    
}
