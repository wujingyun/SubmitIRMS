/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author wangxiahao
 */
@Entity
public class FbBanquet implements Serializable {
    
    
    
    @Id
    private String banquetHallName;
    private String location;
    private int capacity;
    
    @OneToMany(mappedBy="banquet")
    private Collection<FbBanquetReservation> reservation = new ArrayList<FbBanquetReservation>();
    
    public FbBanquet() {}

    public void createBanquet(String banquetHallName, String location, int capacity){
        this.setBanquetHallName(banquetHallName);
        this.setLocation(location);
        this.setCapacity(capacity);
    }
    public String getBanquetHallName() {
        return banquetHallName;
    }

    public void setBanquetHallName(String banquetHallName) {
        this.banquetHallName = banquetHallName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Collection<FbBanquetReservation> getReservation() {
        return reservation;
    }

    public void setReservation(Collection<FbBanquetReservation> reservation) {
        this.reservation = reservation;
    }
  
}
