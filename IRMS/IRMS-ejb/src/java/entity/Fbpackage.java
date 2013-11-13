/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author wangxiahao
 */
@Entity
public class Fbpackage implements Serializable {
  
    @Id  
    private String packageName;
    
    @OneToMany
    private Collection<Fbalacarte> singleDish = new ArrayList<Fbalacarte>(); 
    
    private double totalPrice;
   
    public Fbpackage (){}
    
    public void createPackage(String packageName){
        this.setPackageName(packageName);
        this.setTotalPrice(0);
    }
    
    
     
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Collection<Fbalacarte> getSingleDish() {
        return singleDish;
    }

    public void setSingleDish(Collection<Fbalacarte> singleDish) {
        this.singleDish = singleDish;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
   

   
    
}
