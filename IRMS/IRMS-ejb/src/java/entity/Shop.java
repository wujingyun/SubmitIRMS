/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
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
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long ShopID;
    
    private String Name;
    private String Owner;
    private int totalArea;
    private String description;
    private String operatinghours;
    private String storeContact;
    
    @OneToOne(mappedBy="shop")
    private Contract contract;
    
    @OneToMany(mappedBy="shop")
    private Collection<ProductItem> productItems =
            new ArrayList <ProductItem>();
    
    @OneToMany(cascade={CascadeType.REMOVE})
    private Collection<ShopBill> bills = new ArrayList<ShopBill>();
    
   
    
    public Shop(){}
    public void createShop(String Name,String Owner,int totalArea){
        this.setName(Name);
        this.setOwner(Owner);
        this.setTotalArea(totalArea);
     //   this.setDescription(description);
      //  this.setStoreContact(storeContact);
    }
    
    public void updateShopInfo(String description, String storeContact){
        this.setDescription(description);
        this.setStoreContact(storeContact);
    }
    
    public void renewShop(int totalArea){
        this.setTotalArea(totalArea);
        
    }
       
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

  
    
    public int getTotalArea(){
        return totalArea;
    }
    public void setTotalArea(int totalArea){
        this.totalArea=totalArea;
    }
    
    public Contract getContract(){
        return contract;
    }
    public void setContract(Contract contract){
        this.contract=contract;
    }
    
    
     public void setProductItems (Collection<ProductItem> productItems){
        this.productItems=productItems;
    }

    public Collection<ProductItem> getProductItems(){
        return productItems;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreContact() {
        return storeContact;
    }

    public void setStoreContact(String storeContact) {
        this.storeContact = storeContact;
    }

    public Collection<ShopBill> getBills() {
        return bills;
    }

    public void setBills(Collection<ShopBill> bills) {
        this.bills = bills;
    }

  

    public String getOperatinghours() {
        return operatinghours;
    }

    public void setOperatinghours(String operatinghours) {
        this.operatinghours = operatinghours;
    }
    
    
}
