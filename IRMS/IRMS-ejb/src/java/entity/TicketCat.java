package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author Jiao Shen
 */

@Entity
public class TicketCat implements Serializable {
 @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;
    private String catName;
    private double catPrice;
    private int totalNum;
    private int availNum;
    @OneToMany(mappedBy="ticketCat",cascade= CascadeType.ALL)
    private List<TicketSeat> ticketSeat = new ArrayList();
    @ManyToOne
    private EntShow entshow;
    
    public TicketCat() {}
    
    public void createTicketCat (String catName, double catPrice, int totalNum, int availNum) {
        this.setCatName(catName);
        this.setCatPrice(catPrice);
        this.setTotalNum(totalNum);
        this.setAvailNum(availNum);
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setTicketSeat(List<TicketSeat> ticketSeat) {
        this.ticketSeat = ticketSeat;
    }

    public void setEntshow(EntShow entshow) {
        this.entshow = entshow;
    }

    public List<TicketSeat> getTicketSeat() {
        return ticketSeat;
    }

    public EntShow getEntshow() {
        return entshow;
    }

    public double getCatPrice() {
        return catPrice;
    }

    public void setCatPrice(double catPrice) {
        this.catPrice = catPrice;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getAvailNum() {
        return availNum;
    }

    public void setAvailNum(int availNum) {
        this.availNum = availNum;
    }

}