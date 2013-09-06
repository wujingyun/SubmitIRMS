package entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author wangxiahao
 */
@Entity
public class Contract implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long ContractId;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar DateOfExecution;
    
    private String Landlord;
    private String Tenant;
    private String TenantTradeName;
    private String UnitNo;
    private String NameOfShoppingCenter;
    private String FloorArea;
    private String Purpose;
    private String MinimumRent;
    private String RentRate;
    private String TenantAddress;
    private String LandlordContact;
    private String TenantContact;
    private String BrokerForLandlord;
    private String BrokerForTenant;
    private String Guarantor;
    
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
    
    public String UnitNo(){
        return UnitNo;
    }
    public void setUnitNo(String UnitNo){
        this.UnitNo=UnitNo;
    }
    
    public String getNameOfShoppingCenter(){
        return NameOfShoppingCenter;
    }    
    public void setNameOfShoppingCenter(String NameOfShoppingCenter){
        this.NameOfShoppingCenter=NameOfShoppingCenter;
    }
    
    public String getFloorArea(){
        return FloorArea;
    }
    public void setFloorArea(String FloorArea){
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
    
    public String getBrokerForLandlord(){
        return BrokerForLandlord;
    }    
    public void setBrokerForLandlord(String BrokerForLandlord){
        this.BrokerForLandlord=BrokerForLandlord;
    }
    
    public String getBrokerForTenant(){
        return BrokerForTenant;
    }
    public void setBrokerForTenant(String BrokerForTenant){
        this.BrokerForTenant=BrokerForTenant;
    }
    
    public String getGuarantor(){
        return Guarantor;
    }
    public void setGuarantor(String Guarantor){
        this.Guarantor= Guarantor;
    }
    
}
