/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.ConciergeOrder;
import entity.Contract;
import entity.Hotel;
import entity.ProductItem;
import entity.Shop;
import exception.ExistException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
    ProductItem item;
    //find the category of the shop stored in contract
    private List<ConciergeOrder> order;
  
    
    @Override
    public void addProductItem(Long ShopID,String category,String name,String description
            ,Integer quantityOnHand,BigDecimal unitPrice) throws ExistException{
        shop = new Shop();
        shop = em.find(Shop.class, ShopID);
        if(shop==null) throw new ExistException("The shop cannot be found");
        
        item = new ProductItem();
        item.createProductItem(category, name, description, quantityOnHand, unitPrice);
        shop.getProductItems().add(item);
        item.setShop(shop);
        em.persist(item);
        em.flush();      
    }
    
    @Override
    public void editProductItem(String category,String name,String description
            ,Integer quantityOnHand,BigDecimal unitPrice){
        Query q =em.createQuery("SELECT * FROM  productitem WHERE category = : type"
                + "AND name = : theName");
        q.setParameter("type", category);
        q.setParameter("theName", name);
        item = (ProductItem)q.getSingleResult();
        item.editProduct(description, quantityOnHand, unitPrice);
        em.flush();
    }
    
    @Override
    public void deleteProductItem(String category,String name){
        Query q =em.createQuery("SELECT * FROM  productitem WHERE category = : type"
                + "AND name = : theName");
        q.setParameter("type", category);
        q.setParameter("theName", name);
        item = (ProductItem)q.getSingleResult();
        
        shop = new Shop();
        shop = item.getShop();
        shop.getProductItems().remove(item);
        item.setShop(null);
        em.remove(item);
        em.flush();
    }
    
    @Override
    public void deliveryItem(String hotelName,String customerName, String customerID, 
            String contactNumber, Integer numOfItems, String description) {
            deliveryOrder = new ConciergeOrder();
            hotel = new Hotel();
            
            deliveryOrder.create(hotelName,customerName, customerID, contactNumber, numOfItems, description);
            Calendar cal = Calendar.getInstance();
            deliveryOrder.setOrderTime(cal);
            deliveryOrder.setStatus("In the Shopping Mall");
            
           
            hotel = em.find(Hotel.class, hotelName);
            hotel.getConciergeOrders().add(deliveryOrder);
            em.persist(deliveryOrder);
            em.flush();
    }
    
    @Override
    public List<ConciergeOrder> getListOfDeliveryOrders(){
         order = new ArrayList();
         String ejbql=("SELECT o FROM ConciergeOrder o");
         Query q = em.createQuery(ejbql);
         for(Object o: q.getResultList()){
             ConciergeOrder co = (ConciergeOrder)o;
             order.add(co);
         }
         em.flush();
         return order;
    }
    
     @Override
    public void deleteDeliveryOrder(Long ID,String hotelName) throws ExistException{
        System.err.println("ID "+ID+" hotelname"+hotelName);
        deliveryOrder = new ConciergeOrder();
        deliveryOrder = em.find(ConciergeOrder.class, ID);
        hotel = em.find(Hotel.class, hotelName);
       System.err.println("Sessionbean: ID "+deliveryOrder.getId()+" hotelname"+hotel.getName());
       
        if(deliveryOrder ==null) throw new ExistException("The order has not been found");
        if(!deliveryOrder.getStatus().equals("Received at Hotel")) throw new ExistException("Order is not delivered ");
        hotel.getConciergeOrders().remove(deliveryOrder);
     //   em.detach(deliveryOrder);
        em.remove(deliveryOrder);
        em.flush();
       
    }
  
    @Override
    public void updateDeliveryOrder(String status, Long ID) throws ExistException{
        deliveryOrder = new ConciergeOrder();
        deliveryOrder = em.find(ConciergeOrder.class, ID);
        if(deliveryOrder ==null) throw new ExistException("The order has not been found");
        deliveryOrder.setStatus(status);
        em.flush();
       
    }
    @Override
    public void editTheOrder(String customerName,String customerID,String customerContact,Integer numOfItems,Long orderID,String hotelName){
        System.err.println(" name "+customerName+" "+customerID+" orderid"+orderID+" customer Contact"+customerContact);
        deliveryOrder = new ConciergeOrder();
        deliveryOrder = em.find(ConciergeOrder.class, orderID);
        deliveryOrder.editOrder(customerName, customerID, customerContact, numOfItems,hotelName);
        em.flush();
    }
    
}
