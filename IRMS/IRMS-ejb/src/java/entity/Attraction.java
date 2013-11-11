/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author WU JINGYUN
 */
@Entity
public class Attraction implements Serializable {
  
    @Id
    private String name;
    
    private int maxQuota;
  
    private String operatingHours;
    private String location;
    private String descriptions;
    
    @OneToMany(cascade={CascadeType.REMOVE})
    private Collection<AttractionPass> pass = new ArrayList<AttractionPass>();
    
    @OneToMany(cascade={CascadeType.REMOVE})
    private Collection<AttractionTicket> ticket = new ArrayList<AttractionTicket>();
    
    public Attraction(){}
    
    public void createAttraction(String name,int maxQuota,String operatingHours,String location,String descriptions){
        this.setName(name);
        this.setLocation(location);
        this.setMaxQuota(maxQuota);     
        this.setOperatingHours(operatingHours);
        this.setDescriptions(descriptions);
       
    }
    
    public void editAttraction(int maxQuota,String operatingHours,String location,String descriptions){
        
        this.setLocation(location);
        this.setMaxQuota(maxQuota);      
        this.setDescriptions(descriptions);
   
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxQuota() {
        return maxQuota;
    }

    public void setMaxQuota(int maxQuota) {
        this.maxQuota = maxQuota;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Collection<AttractionPass> getPass() {
        return pass;
    }

    public void setPass(Collection<AttractionPass> pass) {
        this.pass = pass;
    }

    public Collection<AttractionTicket> getTicket() {
        return ticket;
    }

    public void setTicket(Collection<AttractionTicket> ticket) {
        this.ticket = ticket;
    }  

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

   
    
}
