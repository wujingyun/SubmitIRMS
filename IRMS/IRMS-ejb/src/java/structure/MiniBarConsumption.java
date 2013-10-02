/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import entity.MiniBarItem;
import exception.ExistException;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yang Zhennan
 */
public class MiniBarConsumption implements Serializable {
    @PersistenceContext()
    EntityManager em;
    private MiniBarItem miniBarItem;
    private Integer quantity;
    
    public MiniBarConsumption(){
    }
    
    public MiniBarConsumption(String itemName, Integer quantity) throws ExistException{
        this.miniBarItem=em.find(MiniBarItem.class, itemName);
        if(this.miniBarItem==null){
            throw new ExistException("MINI BAR ITEM NOTE EXIST.");
        }
        this.quantity=quantity;
    }

    public MiniBarItem getMiniBarItem() {
        return miniBarItem;
    }

    public void setMiniBarItem(MiniBarItem miniBarItem) throws ExistException {
        this.miniBarItem = miniBarItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public double getUnitPrice(){
        return this.miniBarItem.getPrice();
    }
    public double getTotalCharge(){
        return this.getUnitPrice()*this.quantity;
    }
}
