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
import javax.persistence.OneToMany;

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbOrderList implements Serializable {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany
    private Collection<Fbalacarte> singDish  = new ArrayList<Fbalacarte>();
    @OneToMany
    private Collection<Fbpackage> fbpack  = new ArrayList<Fbpackage>();
    
    private double price;

    public FbOrderList() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Fbalacarte> getSingDish() {
        return singDish;
    }

    public void setSingDish(Collection<Fbalacarte> singDish) {
        this.singDish = singDish;
    }

    public Collection<Fbpackage> getFbpack() {
        return fbpack;
    }

    public void setFbpack(Collection<Fbpackage> fbpack) {
        this.fbpack = fbpack;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
