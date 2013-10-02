/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.io.Serializable;

/**
 *
 * @author Yang Zhennan
 */
public class IncidentalCharge implements Serializable{
    
    private String chargeName;
    private String description;
    private double price;
    
    public IncidentalCharge(){
    }

    public IncidentalCharge(String chargeName, double price, String description) {
        this.chargeName=chargeName;
        this.price=price;
        this.description=description;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
