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
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long ShopID;
    
    private String Name;
    private String Owner;
    private String UnitNo;
    private String totalArea;
    private String description;
    // operating hours, descrition created after signing contract
    private String storeContact;
    
    @OneToOne(mappedBy="shop")
    private Contract contract;
    
    @OneToMany(mappedBy="shop")
    private Collection<ProductItem> productItems =
            new ArrayList <ProductItem>();
    
    @OneToMany
    private Collection<Bill> bills = new ArrayList<Bill>();
    
    @OneToOne(mappedBy="unit")
    private Unit unit;
    
    public Shop(){}
    public void createShop(String Name,String Owner,String UnitNo,String totalArea
            ,String description,String storeContact){
        this.setName(Name);
        this.setOwner(Owner);
        this.setUnitNo(UnitNo);
        this.setTotalArea(totalArea);
        this.setDescription(description);
        this.setStoreContact(storeContact);
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

    public Collection<Bill> getBills() {
        return bills;
    }

    public void setBills(Collection<Bill> bills) {
        this.bills = bills;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    
}
