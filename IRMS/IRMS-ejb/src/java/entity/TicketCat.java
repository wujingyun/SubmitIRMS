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

/**
 * @author Jiao Shen
 */

@Entity
public class TicketCat implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long catId;
    private String catName;
    private double catPrice;
    private int totalNum;
    private int availNum;
    
    @ManyToOne
    private EntShow entShow;
    @OneToMany(mappedBy="ticketCat", cascade=CascadeType.ALL)
    private List<TicketSeat> ticketSeats = new ArrayList<TicketSeat>();
    
    public TicketCat() {}
    
    public void createTicketCat (String catName, double catPrice, int totalNum, int availNum) {
        this.setCatName(catName);
        this.setCatPrice(catPrice);
        this.setTotalNum(totalNum);
        this.setAvailNum(availNum);
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
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

    public EntShow getEntShow() {
        return entShow;
    }

    public void setEntShow(EntShow entShow) {
        this.entShow = entShow;
    }

    public List<TicketSeat> getTicketSeats() {
        return ticketSeats;
    }

    public void setTicketSeats(List<TicketSeat> ticketSeats) {
        this.ticketSeats = ticketSeats;
    }

}