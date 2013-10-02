
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;


/**
 *
 * @author WU JINGYUN
 */



@Entity


public class Customer implements Serializable 
{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    //@Column(length = 32)
    
    @OneToMany(mappedBy="customer")
    /*@JoinTable(
            name="CUSTOMER_ROOMRESERVATION",
            joinColumns={@JoinColumn(name="CUSTOMER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROOMRESERVATION_ID", referencedColumnName="ID")}
    )*/
    private Collection<RoomReservation> roomReservations=new ArrayList();

    private String userName;
    @Column(length = 32)
    private String password;
    private String firstName;
    @Column(length = 32)
    private String lastName;
    @Column(length = 64)
    private String address;
    @Column(length = 64)
    private String email;
    private String ageGroup;
    private String gender;
   
    private String mobilePhoneNumber;
    private Integer loyaltyPointBalance;
     private String securityQuestion;
    private String answer;
    
    
    private int logginAttemp;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar registrationTimestamp;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar last_attemp;
    
    
    public void create(String userName, String password, String firstName, String lastName, String address, String email,  
            String ageGroup, String gender, String moilePhoneNumber, String securityQuestion,String answer) {
       this.setUserName(userName);
       this.setPassword(password);
       this.setFirstName(firstName);
       this.setLastName(lastName);
       this.setAddress(address);
       this.setEmail(email);
       this.setAgeGroup(ageGroup);
       this.setGender(gender);
       this.setMobilePhoneNumber(moilePhoneNumber);
       this.setSecurityQuestion(securityQuestion);
       this.setAnswer(answer);
    }

    public Long getId() {
        return Id;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

   

    public Long getCustomerId() {
        return Id;
    }

    public Collection<RoomReservation> getRoomReservations() {
        return roomReservations;
    }

    public String getUserName() {
        return userName;
    }

    

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public Integer getLoyaltyPointBalance() {
        return loyaltyPointBalance;
    }

    public Calendar getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public int getLogginAttemp() {
        return logginAttemp;
    }

    public Calendar getLast_attemp() {
        return last_attemp;
    }

    public void setCustomerId(Long customerId) {
        this.Id = customerId;
    }

    public void setRoomReservations(Collection<RoomReservation> roomReservations) {
        this.roomReservations = roomReservations;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public void setLoyaltyPointBalance(Integer loyaltyPointBalance) {
        this.loyaltyPointBalance = loyaltyPointBalance;
    }

    public void setRegistrationTimestamp(Calendar registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public void setLogginAttemp(int logginAttemp) {
        this.logginAttemp = logginAttemp;
    }

    public void setLast_attemp(Calendar last_attemp) {
        this.last_attemp = last_attemp;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Id != null ? Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the customerId fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.Id == null && other.Id != null) || (this.Id != null && !this.Id.equals(other.Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ customerId=" + Id + " ]";
    }

  

  
}