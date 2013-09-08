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
    private String UnitNo;
    
    private boolean unitAvailability =true;
    
    
    @ManyToOne
    private Mall mall;
    
    
    @ManyToOne
    private Contract contract;
    
   
    public Unit(){}
    
    public void createUnit(String UnitNo,boolean unitAvailability){
        this.UnitNo=UnitNo;
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

 
  public boolean isUnitAvailability() {
        return unitAvailability;
    }

    public void setUnitAvailability(boolean unitAvailability) {
        this.unitAvailability = unitAvailability;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
    
    
}
