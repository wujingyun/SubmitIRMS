package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author wangxiahao
 */
@Entity
public class Contract implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long ContractId;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar DateOfExecution;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar DateOfExpiry;
    
    
    private String ContractType;
    private String Landlord;
    private String Tenant;
    private String IdentityCard;
    private String TenantTradeName;
    private String NameOfShoppingCenter;
    private int FloorArea;
    private String Purpose;// category of the shop
    private String MinimumRent;
    private String RentRate;
    private String TenantAddress;
    private String LandlordContact;
    private String TenantContact;
    private String upfrontRentalDeposit;
    private boolean contractStatus;
    private boolean contractExpiry;
    
    @OneToOne
    private ShopOwner shopTenant;
    
    @OneToOne
    private Shop shop;
    
    @OneToMany(mappedBy="contract")
    private Collection<Unit> units = new ArrayList<Unit>();
    
    
    
    public void createContract(String ContractType,String Landlord,
           String TenantTradeName,
            String NameOfShoppingCenter,String Purpose
            ,String MinimumRent,String RentRate,String LandlordContact
           ,String upfrontRentalDeposit){
        this.setContractType(ContractType);
        this.setLandlord(Landlord);   
        this.setTenantTradeName(TenantTradeName);
        this.setNameOfShoppingCenter(NameOfShoppingCenter);
        this.setFloorArea(FloorArea);
        this.setPurpose(Purpose);
        this.setMinimumRent(MinimumRent);
        this.setRentRate(RentRate);
        this.setTenantAddress(TenantAddress);
        this.setLandlordContact(LandlordContact);
        this.setTenantContact(TenantContact);
        this.setUpfrontRentalDeposit(upfrontRentalDeposit); 
        this.setContractStatus(true);
        this.setContractExpiry(false);
    }  
    
    public void createTenantInfo(String Tenant,String IdentityCard,String TenantAddress,String TenantContact){
        this.setTenant(Tenant);
        this.setIdentityCard(IdentityCard);
        this.setTenantAddress(TenantAddress);
        this.setTenantContact(TenantContact);
       
    }
    
   public void renewContract(String ContractType,String Landlord,
           String TenantTradeName,
            String NameOfShoppingCenter,String Purpose
            ,String MinimumRent,String RentRate,String LandlordContact
           ,String upfrontRentalDeposit,Collection<Unit> units){
        this.setContractType(ContractType);
        this.setLandlord(Landlord);   
        this.setTenantTradeName(TenantTradeName);
        this.setNameOfShoppingCenter(NameOfShoppingCenter);
        this.setFloorArea(FloorArea);
        this.setPurpose(Purpose);
        this.setMinimumRent(MinimumRent);
        this.setRentRate(RentRate);
        this.setTenantAddress(TenantAddress);
        this.setLandlordContact(LandlordContact);
        this.setTenantContact(TenantContact);
        this.setUpfrontRentalDeposit(upfrontRentalDeposit); 
        this.setUnits(units);
        this.setContractStatus(true);
        this.setContractExpiry(false);
    }  
    
    
    public Contract(){
        
    }
    

    public Long getContractId() {
        return ContractId;
    }

    public void setContractId(Long ContractId) {
        this.ContractId = ContractId;
    }

    public Long getId() {
        return ContractId;
    }

    public void setId(Long ContractId) {
        this.ContractId = ContractId;
    }
     
    public Calendar getDateOfExecution(){
        return DateOfExecution;
    }
    public void setDateOfExecution (Calendar DateOfExeution){
        this.DateOfExecution = DateOfExeution;
    }
    
    public String getLandlord(){
        return Landlord;
    }
    public void setLandlord(String Landlord){
        this.Landlord=Landlord;      
    }
    
    public String getTenant(){
        return Tenant;
    }
    public void setTenant(String Tenant){
        this.Tenant=Tenant;
    }

    
    public String TenantTradeName(){
        return TenantTradeName;
    }
    public void setTenantTradeName(String TenantTradeName){
        this.TenantTradeName=TenantTradeName;
    }

    public Collection<Unit> getUnits() {
        return units;
    }

    public void setUnits(Collection<Unit> units) {
        this.units = units;
    }
        
    
    public String getNameOfShoppingCenter(){
        return NameOfShoppingCenter;
    }    
    public void setNameOfShoppingCenter(String NameOfShoppingCenter){
        this.NameOfShoppingCenter=NameOfShoppingCenter;
    }
    
    public int getFloorArea(){
        return FloorArea;
    }
    public void setFloorArea(int FloorArea){
        this.FloorArea=FloorArea;
    }
    
    public String getPurpose (){
        return Purpose;
    }    
    public void setPurpose(String Purpose){
        this.Purpose =Purpose;
    }
    
    public String getMinimumRent(){
        return MinimumRent;
    }
    public void setMinimumRent(String MinimumRent){
        this.MinimumRent=MinimumRent;
    }
    
    public String getRentRate(){
        return RentRate;
    }    
    public void setRentRate(String RentRate){
        this.RentRate=RentRate;
    } 
    
    public String getTenantAddress(){
        return TenantAddress;
    }
    public void setTenantAddress(String TenantAddress){
        this.TenantAddress=TenantAddress;
    }
    
    public String getLandlordContact(){
        return LandlordContact;
    }
    public void setLandlordContact(String LandlordContact){
        this.LandlordContact=LandlordContact;
    }
    
    public String getTenantContact(){
        return TenantContact;
    }
    public void setTenantContact(String TenantContact){
        this.TenantContact=TenantContact;
    }
    
    public String getUpfrontRentalDeposit(){
        return upfrontRentalDeposit;
    }
    public void setUpfrontRentalDeposit(String upfrontRentalDeposit){
        this.upfrontRentalDeposit=upfrontRentalDeposit;
    }

    public String getIdentityCard() {
        return IdentityCard;
    }

    public void setIdentityCard(String IdentityCard) {
        this.IdentityCard = IdentityCard;
    }

    public String getContractType() {
        return ContractType;
    }

    public void setContractType(String ContractType) {
        this.ContractType = ContractType;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public ShopOwner getShopTenant() {
        return shopTenant;
    }

    public void setShopTenant(ShopOwner shopTenant) {
        this.shopTenant = shopTenant;
    }

    public Calendar getDateOfExpiry() {
        return DateOfExpiry;
    }

    public void setDateOfExpiry(Calendar DateOfExpiry) {
        this.DateOfExpiry = DateOfExpiry;
    }

    public boolean isContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(boolean contractStatus) {
        this.contractStatus = contractStatus;
    }

    public boolean isContractExpiry() {
        return contractExpiry;
    }

    public void setContractExpiry(boolean contractExpiry) {
        this.contractExpiry = contractExpiry;
    }
    
}
