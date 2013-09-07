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
import javax.persistence.OneToOne;

/**
 *
 * @author wangxiahao
 */
@Entity
public class Shop implements Serializable {
   
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long ShopID;
    
    private String Name;
    private String Owner;
    private String Bill;
    private String UnitNo;
    private String totalArea;
    
    @OneToOne
    private Contract contract;
      
    @OneToOne(mappedBy="shop")
    private ShopOwner shopOwner;
    
    @OneToMany(mappedBy="shop")
    private Collection<ProductItem> productItems =
            new ArrayList <ProductItem>();
    
    
    public Long getShopID() {
        return  ShopID;
    }
    public void setShopID(Long  ShopID) {
        this.ShopID =  ShopID;
    }

    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    
    public String getOwner(){
        return Owner;
    }
    public void setOwner(String Owner){
        this.Owner=Owner;
    }
    
    public String getBill(){
        return Bill;
    }
    public void setBill(String Bill){
        this.Bill =Bill;
    }
    
    public String getUnitNo(){
        return UnitNo;
    }
    public void setUnitNo(String UnitNo){
        this.UnitNo = UnitNo;
    }
    
    public String getTotalArea(){
        return totalArea;
    }
    public void setTotalArea(String totalArea){
        this.totalArea=totalArea;
    }
    
    public Contract getContract(){
        return contract;
    }
    public void setContract(Contract contract){
        this.contract=contract;
    }
    
    
    public ShopOwner getShopOwner(){
        return shopOwner;
    }
    public void setShopOwner(ShopOwner shopOwner){
        this.shopOwner=shopOwner;
    }
    
     public void setProductItem (Collection<ProductItem> productItems){
        this.productItems=productItems;
    }

    public Collection<ProductItem> getReservations(){
        return productItems;
    }
}
