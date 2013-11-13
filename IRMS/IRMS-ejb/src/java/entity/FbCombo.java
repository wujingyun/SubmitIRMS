/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbCombo implements Serializable {
  
    @Id
    private String name;
    
    @ManyToOne
    private FbRestaurant restaurant;
    
    @OneToMany
    private Collection<FbRDish> dish = new ArrayList<FbRDish>();
    
    private double price;
    
    public FbCombo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FbRestaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(FbRestaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Collection<FbRDish> getDish() {
        return dish;
    }

    public void setDish(Collection<FbRDish> dish) {
        this.dish = dish;
    }   

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
}
