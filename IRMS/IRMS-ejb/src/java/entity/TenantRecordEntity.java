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
public class TenantRecordEntity implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long RecordID;
    
    private String Tenant;
    private String IdentityCard;
     private String TenantAddress;
     private String TenantContact;
     
   public TenantRecordEntity (){
       
   }
   
     
   public void createTenantInfo(String Tenant,String IdentityCard,String TenantAddress,String TenantContact){
        this.setTenant(Tenant);
        this.setIdentityCard(IdentityCard);
        this.setTenantAddress(TenantAddress);
        this.setTenantContact(TenantContact);
    }
     
     
    public Long getRecordID() {
        return RecordID;
    }

    public void setRecordID(Long RecordID) {
        this.RecordID = RecordID;
    }
     
     
     
    public String getTenant() {
        return Tenant;
    }

    public void setTenant(String Tenant) {
        this.Tenant = Tenant;
    }

    public String getIdentityCard() {
        return IdentityCard;
    }

    public void setIdentityCard(String IdentityCard) {
        this.IdentityCard = IdentityCard;
    }

    public String getTenantAddress() {
        return TenantAddress;
    }

    public void setTenantAddress(String TenantAddress) {
        this.TenantAddress = TenantAddress;
    }

    public String getTenantContact() {
        return TenantContact;
    }

    public void setTenantContact(String TenantContact) {
        this.TenantContact = TenantContact;
    }
     
     

    
}
