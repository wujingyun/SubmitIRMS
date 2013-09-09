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
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long ShopOwnerID;
    
    private String UserName;
    private String Password;
    // status, tenant name ,ic contact number.
    boolean status;
    private String tenantName;
    private String IC;
    private String contactNumber;
    
    @OneToOne(mappedBy="shopTenant")
    private Contract contract;
    
    public ShopOwner(){}
    
    public void createShopOwner(String UserName, String Password
            ,String tenantName,String IC,String contactNumber){
        this.setUserName(UserName);
        this.setPassword(Password);
        this.setStatus(true);
        this.setTenantName(tenantName);
        this.setIC(IC);
        this.setContactNumber(contactNumber);
    }
    
    
    public Long getShopOwnerID() {
        return ShopOwnerID;
    }

    public void setShopOwnerID(Long ShopOwnerID) {
        this.ShopOwnerID = ShopOwnerID;
    }

    public String getUserName(){
        return UserName;
    }
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    
    public String getPassword(){
        return Password;
    }
    public void setPassword(String Password){
        this.Password=Password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getIC() {
        return IC;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
    
}
