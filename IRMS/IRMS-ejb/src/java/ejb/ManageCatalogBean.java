/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.ConciergeOrder;
import entity.Contract;
import entity.Hotel;
import entity.Shop;
import exception.ExistException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class ManageCatalogBean implements ManageCatalogBeanRemote {
    
    @PersistenceContext()
    EntityManager em;
    Contract contractEntity;
    Shop shop;
    ConciergeOrder deliveryOrder;
    Hotel hotel;
    //find the category of the shop stored in contract

    public void viewTenancyMix() {
        contractEntity = new Contract();
        Map<String, Integer> m = new HashMap<String, Integer>();
        
       
        Query q = em.createQuery("SELECT * FROM contract c ");
        
        for (Object o : q.getResultList()) {
            contractEntity = (Contract) o;
            String category = contractEntity.getPurpose();
            if (m.containsKey(category)) {
       //         m.
            }
        }
    }
    
    public void editStoreInfo(String storeName, String description, String storeContact,String operatingHours) {
        shop =new Shop();
        Query q = em.createQuery("SELECT * FROM shop WHERE name = : storeName");
        q.setParameter("storeName", storeName);
        shop =(Shop)q.getSingleResult();
        shop.setDescription(description);
        shop.setContract(contractEntity);
        shop.setOperatinghours(operatingHours);
        em.flush();
    }
    
    public void deliveryItem(String hotelName,String customerName, String customerID, 
            String contactNumber, Integer numOfItems, String description) {
            deliveryOrder = new ConciergeOrder();
            hotel = new Hotel();
            
            deliveryOrder.create(hotelName,customerName, customerID, contactNumber, numOfItems, description);
            Calendar cal = Calendar.getInstance();
            deliveryOrder.setOrderTime(cal);
            deliveryOrder.setStatus("In the Shopping Mall");
            
            Query q = em.createQuery("SELECT * FROM hotel WHERE name =: hotelName");
            q.setParameter("hotelName",hotelName);
            hotel = (Hotel)q.getSingleResult();
            hotel.getConciergeOrders().add(deliveryOrder);
            em.persist(deliveryOrder);
            em.flush();
    }
    
    public boolean updateDeliveryOrder(String status, String ID) throws ExistException{
        deliveryOrder = new ConciergeOrder();
        deliveryOrder = em.find(ConciergeOrder.class, ID);
        if(deliveryOrder ==null) throw new ExistException("The order has not been found");
        deliveryOrder.setStatus(status);
        em.flush();
        return true;
    }
}
