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
    private String unitNo;
    private int unitSpace;
    private boolean unitAvailability = true;
    @ManyToOne
    private Mall mall;
    @ManyToOne
    private Contract contract;
    
    public Unit() {
    }
    
    public void createUnit(String unitNo, int unitSpace) {
        this.unitNo = unitNo;
        this.setUnitAvailability(true);
        this.setUnitSpace(unitSpace);
    }
    
    public String getUnitNo() {
        return unitNo;
    }
    
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }
    
    public Mall getMall() {
        return mall;
    }
    
    public void setMall(Mall mall) {
        this.mall = mall;
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
    
    public int getUnitSpace() {
        return unitSpace;
    }
    
    public void setUnitSpace(int unitSpace) {
        this.unitSpace = unitSpace;
    }
}
