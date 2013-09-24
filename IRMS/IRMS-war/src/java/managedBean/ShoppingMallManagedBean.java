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
import entity.TenantRecordEntity;
import entity.Unit;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

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
    private static String Tenant;
    private static String IdentityCard;
    private String TenantTradeName;
    private String NameOfShoppingCenter;
    private int FloorArea;
    private String Purpose;                  // category of the shop
    private String MinimumRent;
    private String RentRate;
    private static String TenantAddress;
    private String LandlordContact;
    private static String TenantContact;
    private String upfrontRentalDeposit;
    private static Date date;  
    private Shop shop;
    private ShopOwner tenant;
    private Mall mall;
    private TenantRecordEntity tenantRecord;
  //  private Unit units;
    private List<String> units = new ArrayList();
    private List<String> selectedUnits;
    private Unit mallUnit;
    private static String mName="IRMall";
    private List<TenantRecordEntity> tenantList;
    private List<String> listOfTenant;
    private TenantRecordEntity selectedRecord;
   
  //  private Map<String,String> mallUnits;
    
    public ShoppingMallManagedBean() {        
    }
    @PostConstruct
    public void init(){
        this.tenantList=cbr.getExistingTenant();
    }
    
    public Date getDate() {  
        return date;  
    }  
  
    public void setDate(Date date) {  
        ShoppingMallManagedBean.date = date;  
    }  
    
     public void handleDateSelect(SelectEvent event) {  
        FacesContext facesContext = FacesContext.getCurrentInstance();  
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));  
    } 
     
     public void save() {  
     
       HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
       Tenant = request.getParameter("form1:tenant");
        
        IdentityCard = request.getParameter("form1:identityCard");
       TenantAddress = request.getParameter("form1:tenantAddress");
        TenantContact = request.getParameter("form1:tenantContact");
            
          System.out.println( Tenant);
          System.out.println( IdentityCard);
          System.out.println(  TenantAddress);
          System.out.println( TenantContact); 
    }  
     
    public void setAttributes(ActionEvent event){
           Tenant =getSelectedRecord().getTenant();  
           IdentityCard = getSelectedRecord().getIdentityCard();
           TenantAddress = getSelectedRecord().getTenantAddress();
           TenantContact = getSelectedRecord().getTenantContact();
           System.out.println("found "+tenant);
    }
      
     public void contractCreation(ActionEvent event){
          
         try{
         
             cbr.signContract(ContractType, Landlord,  Tenant, IdentityCard, 
                     TenantTradeName, getSelectedUnits(),NameOfShoppingCenter, 
                     Purpose, MinimumRent, RentRate, TenantAddress, LandlordContact, 
                     TenantContact, upfrontRentalDeposit,getDate());
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  
                 "New Contract created successfully", ""));
         }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  
                    "An error has occurred while creating the new contract: " + ex.getMessage(), ""));
         }
         
     }
     
     public void renewContract(ActionEvent event){
         try{
             cbr.reNewContract(IdentityCard,  getSelectedUnits(), Landlord, Purpose, 
                     MinimumRent, RentRate, TenantAddress, 
                     LandlordContact, TenantContact, 
                     upfrontRentalDeposit, TenantTradeName);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  
                 "Contract has been renewed successfully", ""));
         }catch(Exception ex){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  
                    "An error has occurred while renewing the new contract: " + ex.getMessage(), ""));
         }
     }
     
    public void terminateContract(ActionEvent event){
        try{
            
            cbr.terminateContract(IdentityCard, TenantTradeName);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  
                 "Contract has been terminated successfully", ""));
        }catch(Exception ex){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  
                    "An error has occurred while terminating the new contract: " + ex.getMessage(), ""));
        }
    }
  
    
     
    public List<TenantRecordEntity> getTenantList(){
         System.out.println("Getting records for tenant ");
        return tenantList;
    }
    public void setTenantList(List<TenantRecordEntity> tenantList){
        this.tenantList =tenantList;
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

 /*   public void setTenant(String Tenant) {
        this.Tenant = Tenant;
    }*/

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

 /*   public void setTenantAddress(String TenantAddress) {
        this.TenantAddress = TenantAddress;
    }*/

    public String getLandlordContact() {
        return LandlordContact;
    }

    public void setLandlordContact(String LandlordContact) {
        this.LandlordContact = LandlordContact;
    }

    public String getTenantContact() {
        return TenantContact;
    }

 /*   public void setTenantContact(String TenantContact) {
        this.TenantContact = TenantContact;
    }*/

    public String getUpfrontRentalDeposit() {
        return upfrontRentalDeposit;
    }

    public void setUpfrontRentalDeposit(String upfrontRentalDeposit) {
        this.upfrontRentalDeposit = upfrontRentalDeposit;
    }

    public String getIdentityCard() {
        return IdentityCard;
    }

   /* public void setIdentityCard(String IdentityCard) {
        this.IdentityCard = IdentityCard;
    }*/

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

    public TenantRecordEntity getSelectedRecord() {
          System.out.println("get record 1");
          
       
        return selectedRecord;
    }

    public void setSelectedRecord(TenantRecordEntity selectedRecord) {
         System.out.println( "set record ");
        this.selectedRecord = selectedRecord;
    }

 
}
