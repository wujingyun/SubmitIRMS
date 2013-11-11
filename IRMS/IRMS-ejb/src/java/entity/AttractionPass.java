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
public class AttractionPass implements Serializable {

    @Id
    private String passName;
    private double price;
    private String remarks;
    private String passType;

    public AttractionPass() {
    }

    public void createPass(String passName, double price, String passType,String remarks) {
        this.setPassName(passName);
        this.setPrice(price);
        this.setPassType(passType);
        this.setRemarks(remarks);

    }

    public void editPass(double price, String passType,String remarks ) {
        this.setPrice(price);
        this.setPassType(passType);
        this.setRemarks(remarks);
    }

    public String getPassName() {
        return passName;
    }

    public void setPassName(String passName) {
        this.passName = passName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }
}
