/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;


import ejb.ContractBeanRemote;
import ejb.ManageMallSpaceBeanRemote;
import entity.Mall;
import entity.Shop;
import entity.ShopOwner;
import entity.Unit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author wangxiahao
 */
@ManagedBean
@RequestScoped
public class ShoppingMallManagedBean implements Serializable{

    @EJB
    ContractBeanRemote cbr;
    @EJB
    ManageMallSpaceBeanRemote mmsbr;
    
    private String ContractType;
    private String Landlord;
    private String Tenant;
    private String IdentityCard;
    private String TenantTradeName;
    private String NameOfShoppingCenter;
    private int FloorArea;
    private String Purpose;                  // category of the shop
    private String MinimumRent;
    private String RentRate;
    private String TenantAddress;
    private String LandlordContact;
    private String TenantContact;
    private String upfrontRentalDeposit;
    
    private Shop shop;
    private ShopOwner tenant;
    private Mall mall;
  //  private Unit units;
    private List<String> units = new ArrayList();
    private List<String> selectedUnits;
    private Unit mallUnit;
    private static String mName="IRMall";
    
   
  //  private Map<String,String> mallUnits;
    
    public ShoppingMallManagedBean() {        
    }

      
    
     public void contractCreation(ActionEvent event){
         try{
             cbr.signContract(ContractType, Landlord, Tenant, IdentityCard, 
                     TenantTradeName, getSelectedUnits(),NameOfShoppingCenter, 
                     Purpose, MinimumRent, RentRate, TenantAddress, LandlordContact, 
                     TenantContact, upfrontRentalDeposit);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  
                 "New Contract created successfully", ""));
         }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  
                    "An error has occurred while creating the new book: " + ex.getMessage(), ""));
         }
         
     }
     
     public List<String> getUnits(){
            
         units = mmsbr.DisplayRepartitionMall(mName);
 
         return units;
     } 
     
    public String getContractType() {
        return ContractType;
    }

    public void setContractType(String ContractType) {
        this.ContractType = ContractType;
    }

    public String getLandlord() {
        return Landlord;
    }

    public void setLandlord(String Landlord) {
        this.Landlord = Landlord;
    }

    public String getTenant() {
        return Tenant;
    }

    public void setTenant(String Tenant) {
        this.Tenant = Tenant;
    }

    public String getNameOfShoppingCenter() {
        return NameOfShoppingCenter;
    }

    public void setNameOfShoppingCenter(String NameOfShoppingCenter) {
        this.NameOfShoppingCenter = NameOfShoppingCenter;
    }

    public int getFloorArea() {
        return FloorArea;
    }

    public void setFloorArea(int FloorArea) {
        this.FloorArea = FloorArea;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String Purpose) {
        this.Purpose = Purpose;
    }

    public String getMinimumRent() {
        return MinimumRent;
    }

    public void setMinimumRent(String MinimumRent) {
        this.MinimumRent = MinimumRent;
    }

    public String getRentRate() {
        return RentRate;
    }

    public void setRentRate(String RentRate) {
        this.RentRate = RentRate;
    }

    public String getTenantAddress() {
        return TenantAddress;
    }

    public void setTenantAddress(String TenantAddress) {
        this.TenantAddress = TenantAddress;
    }

    public String getLandlordContact() {
        return LandlordContact;
    }

    public void setLandlordContact(String LandlordContact) {
        this.LandlordContact = LandlordContact;
    }

    public String getTenantContact() {
        return TenantContact;
    }

    public void setTenantContact(String TenantContact) {
        this.TenantContact = TenantContact;
    }

    public String getUpfrontRentalDeposit() {
        return upfrontRentalDeposit;
    }

    public void setUpfrontRentalDeposit(String upfrontRentalDeposit) {
        this.upfrontRentalDeposit = upfrontRentalDeposit;
    }

    public String getIdentityCard() {
        return IdentityCard;
    }

    public void setIdentityCard(String IdentityCard) {
        this.IdentityCard = IdentityCard;
    }

    public String getTenantTradeName() {
        return TenantTradeName;
    }

    public void setTenantTradeName(String TenantTradeName) {
        this.TenantTradeName = TenantTradeName;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public ShopOwner getShopOwner() {
        return tenant;
    }

    public void setShopOwner(ShopOwner tenant) {
        this.tenant = tenant;
    }

    public void setUnits(List<String> units) {
        this.units = units;
   }

    public Mall getMall() {
        return mall;
    }

    public void setMall(Mall mall) {
        this.mall = mall;
    }

    public Unit getMallUnit() {
        return mallUnit;
    }

    public void setMallUnit(Unit mallUnit) {
        this.mallUnit = mallUnit;
    }

    public List<String> getSelectedUnits() {
        return selectedUnits;
    }

    public void setSelectedUnits(List<String> selectedUnits) {
        this.selectedUnits = selectedUnits;
    }
    
    
}
