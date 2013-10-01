/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 *
 * @author Yang Zhennan
 */
@Entity
public class MiniBarItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    @ManyToOne
    private Hotel hotel;
    private String description;
    private Integer quantity;
    private double price;

    public MiniBarItem() {
    }
    
    public void create(String name, String description, Integer quantity, double price){
        this.setName(name);
        this.setDescription(description);
        this.setQuantity(quantity);
        this.setPrice(price);
    }
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MiniBarItem)) {
            return false;
        }
        MiniBarItem other = (MiniBarItem) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MiniBarItem[ id=" + name + " ]";
    }
    
}
