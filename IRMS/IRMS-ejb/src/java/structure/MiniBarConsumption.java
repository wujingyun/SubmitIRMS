/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import entity.MiniBarItem;
import exception.ExistException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yang Zhennan
 */
public class MiniBarConsumption {
    @PersistenceContext()
    EntityManager em;
    private MiniBarItem miniBarItem;
    private Integer quantity;
    
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

    public void setMiniBarItem(String itemName) throws ExistException {
        this.miniBarItem = em.find(MiniBarItem.class, itemName);
        if(this.miniBarItem==null){
            throw new ExistException("MINI BAR ITEM NOTE EXIST.");
        }
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
