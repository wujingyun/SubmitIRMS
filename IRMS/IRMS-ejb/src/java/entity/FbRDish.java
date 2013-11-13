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

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbRDish implements Serializable {
   
    @Id
    private String name;
    
    private String type;
    
    private double price;
    
    private boolean inMealSet;
    
    public FbRDish() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInMealSet() {
        return inMealSet;
    }

    public void setInMealSet(boolean inMealSet) {
        this.inMealSet = inMealSet;
    }
    
}
