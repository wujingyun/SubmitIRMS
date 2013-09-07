
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author WU JINGYUN
 */



@Entity

@NamedQueries({
    @NamedQuery(name = "getAllCustomers", query = "SELECT c FROM Customer c ORDER BY c.customerId ASC")
})


public class Customer implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(length = 32)


    private String Username;
    @Column(length = 32)
    private String PW;
    private String firstName;
    @Column(length = 32)
    private String lastName;
    @Column(length = 64)
    private String address;
    @Column(length = 64)
    private String email;
    @Column(length = 16)
    private String password;
    @Column(length = 3)
    private String mobilePhoneCountryCode;
    @Column(length = 16)
    private String mobilePhoneNumber;
    private Integer loyaltyPointBalance;
    private Timestamp registrationTimestamp;
        
    public void create(String Username, String firstName, String lastName, String address, String email,  
            String mobilePhoneCountryCode, String moilePhoneNumber) {
       this.setUserName(Username);
       this.setFirstName(firstName);
       this.setLastName(lastName);
       this.setAddress(address);
       this.setEmail(email);
       this.setPassword(password);
       this.setMobilePhoneCountryCode(mobilePhoneCountryCode);
       this.setMobilePhoneNumber(mobilePhoneNumber);
       
    }

    
    
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the customerId fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ customerId=" + customerId + " ]";
    }

      public void getUserName(String Username) {
        this.Username = Username;
    }
      
        public void setUserName(String Username) {
        this.Username = Username;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhoneCountryCode() {
        return mobilePhoneCountryCode;
    }

    public void setMobilePhoneCountryCode(String mobilePhoneCountryCode) {
        this.mobilePhoneCountryCode = mobilePhoneCountryCode;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Integer getLoyaltyPointBalance() {
        return loyaltyPointBalance;
    }

    public void setLoyaltyPointBalance(Integer loyaltyPointBalance) {
        this.loyaltyPointBalance = loyaltyPointBalance;
    }

  
    public void setRegistrationTimestamp(Timestamp registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

  
}