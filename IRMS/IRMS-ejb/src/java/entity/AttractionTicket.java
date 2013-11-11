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
public class AttractionTicket implements Serializable {
    
    @Id
    private String ticketName;
    
    private double ticketPrice;
    private String ticketType;
    private String remarks;
    
    public AttractionTicket(){
        
    }
    public void createTicket(String ticketName, double ticketPrice, String ticketType, String remarks){
        this.setTicketName(ticketName);
        this.setTicketPrice(ticketPrice);
        this.setTicketType(ticketType);
        this.setRemarks(remarks);    
    }
    public void editTicket(double ticketPrice, String ticketType, String remarks){
        this.setTicketPrice(ticketPrice);
        this.setTicketType(ticketType);
        this.setRemarks(remarks);  
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
    
    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
 
    
}
