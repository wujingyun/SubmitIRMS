/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
 * @author WU JINGYUN
 */
@Entity
public class Membership implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String membershipType;
    @OneToMany(mappedBy="membership",cascade= CascadeType.ALL)
     private List<Customer> customer;
    @OneToOne(cascade = {CascadeType.ALL})
    private LoyaltyPlan loyaltyPlan;
    
    public void create(String membershipType) {
        this.setMembershipType(membershipType);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }

  
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public void setLoyaltyPlan(LoyaltyPlan loyaltyPlan) {
        this.loyaltyPlan = loyaltyPlan;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public LoyaltyPlan getLoyaltyPlan() {
        return loyaltyPlan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Membership)) {
            return false;
        }
        Membership other = (Membership) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Membership[ id=" + id + " ]";
    }
}
