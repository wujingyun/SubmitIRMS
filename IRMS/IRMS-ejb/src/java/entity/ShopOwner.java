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
import javax.persistence.OneToOne;

/**
 *
 * @author wangxiahao
 */
@Entity
public class ShopOwner implements Serializable {
   
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long ShopOwnerID;
    
    private String Name;
    private String Password;
    
    @OneToOne
    private Shop shop;
    
    public Long getShopOwnerID() {
        return ShopOwnerID;
    }

    public void setShopOwnerID(Long ShopOwnerID) {
        this.ShopOwnerID = ShopOwnerID;
    }

    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    
    public String getPassword(){
        return Password;
    }
    public void setPassword(String Password){
        this.Password=Password;
    }
    
    public Shop getShop(){
        return shop;
    }
    public void setShop(Shop shop){
        this.shop=shop;
    }
    
}
