/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author wangxiahao
 */
@Entity
public class Fbalacarte implements Serializable {

    @Id
    private String dishName;
    private double price;
    private String type;
    private String description;
    private boolean inPackage;

    public Fbalacarte() {
    }

    public void createAlacarteDish(String dishName, double price, String type, String description) {
        this.setDishName(dishName);
        this.setPrice(price);
        this.setType(type);
        this.setDescription(description);
        this.setInPackage(false);
    }

    public void editAlacarteDish(double price, String type, String description) {
        this.setPrice(price);
        this.setType(type);
        this.setDescription(description);
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInPackage() {
        return inPackage;
    }

    public void setInPackage(boolean inPackage) {
        this.inPackage = inPackage;
    }
}
