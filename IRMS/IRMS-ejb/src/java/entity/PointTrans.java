/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author WU JINGYUN
 */
@Entity
public class PointTrans implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar date_of_pointTrans;
    @ManyToOne
    private Customer customer; 
    private int point; 
    private Long shopId;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDate_of_pointTrans(Calendar date_of_pointTrans) {
        this.date_of_pointTrans = date_of_pointTrans;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Calendar getDate_of_pointTrans() {
        return date_of_pointTrans;
    }

    public int getPoint() {
        return point;
    }

    public Long getShopId() {
        return shopId;
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
        if (!(object instanceof PointTrans)) {
            return false;
        }
        PointTrans other = (PointTrans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PointTrans[ id=" + id + " ]";
    }

    public void setDate_of_pointTrans(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}
