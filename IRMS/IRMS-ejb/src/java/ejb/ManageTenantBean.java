/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Contract;
import entity.Shop;
import entity.ShopBill;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class ManageTenantBean implements ManageTenantBeanRemote {
   
    @PersistenceContext()
    EntityManager em;
    ShopBill bill;
    Shop shop;
    double commisionRate = 0.3;
    Calendar dateIssued;
    List<Shop> shopList;
    Collection<ShopBill> billList;
    Contract contractEntity;
    HashMap<String, Integer> cache ;
    Integer num=0;
    // check on jasper report not included in the first system release.
    public void generateQuarterlyReport(){
    
    }
      @Override
      public HashMap<String, Integer> viewTenancyMix() {
        contractEntity = new Contract();
       cache = new HashMap<String, Integer>();
      
      //  List<String> categories = new ArrayList<String> ();
        shopList = new ArrayList <Shop>();
        shopList = this.getShopList();
        System.err.println("list size of shops "+shopList.size());
       
        for(Iterator it = shopList.iterator(); it.hasNext();){
                   shop = (Shop)it.next();
                   String s = shop.getContract().getPurpose();
                   if(cache.containsKey(s)){
                   cache.put(s, cache.get(s)+1);
                   }
                   else{
                       cache.put(s,1);
                   }
                   
        }
        for (Iterator<Map.Entry<String, Integer>> it = cache.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = it.next();
            System.out.println("key,val: " + entry.getKey() + "," + entry.getValue());
        }
        return cache;
    }
      @Override
      public String test (){
          return "Brand wangxiahao";
      }
    
   
    
    @Override
    public Collection<ShopBill> sendBills(Long ShopID){
        shop = new Shop();
        shop =em.find(Shop.class, ShopID);   
        shop.getBills().size();
        billList = new ArrayList();
        billList = shop.getBills();
          em.flush();
        return billList;
        
        
    }
    
    @Override
    public double calculateCommission(double revenue){
        double commissionFee;
       commissionFee = commisionRate * revenue;
        return commissionFee;
    }
    //view only bills from one shop
    @Override
    public Shop viewBill(String ShopName,String shopOwner){
        shop = new Shop();
        Query q = em.createQuery("SELECT * FROM shop WHERE name = : storeName AND"
                + "owner = : shopOwner");
        q.setParameter("storeName", ShopName);
        q.setParameter("shopOwner", shopOwner);

        shop = (Shop) q.getSingleResult();
        
        return shop;
    }
   
   
    // create one bill and relate it to one shop
    @Override
    public void creatBill(double RentalFee, double commission, Long ShopID) {
        bill = new ShopBill();
        double figure = calculateCommission(commission);
        System.err.println("bill generate "+RentalFee+" "+figure);
        bill.createBill(RentalFee, figure);
        Calendar cal = Calendar.getInstance();
        bill.setDateIssued(cal);     
        System.err.println(RentalFee+" "+ShopID);
        em.persist(bill);
        shop = new Shop();
        shop= em.find(Shop.class,ShopID);
         System.err.println(" shop found! "+shop.getShopID());

       
        shop.getBills().add(bill);
        
        em.flush();
    }
    
    @Override
    public List<Shop> getShopList(){
        shopList= new ArrayList();
         String ejbql ="SELECT s FROM Shop s";
         Query q = em.createQuery(ejbql);
         for(Object o: q.getResultList()){
             Shop s = (Shop)o;
             if(s.getContract()!=null){
             shopList.add(s);
             }
         }        
         em.flush();
         return shopList;
    }

    @Override
    public void EditBillStatus(Long BillID) {
        bill = new ShopBill();
        bill = em.find(ShopBill.class, BillID);
        bill.setBillStatus(false);
        em.flush();
    }
    
    @Override
    public void EditShopInfo(Long BillID,String description,String operatinghours,String storeContact){
        shop = new Shop();
        shop =em.find(Shop.class, BillID);
        System.err.println(description.length()+" "+" operatinghours "+operatinghours.length() +" storeContact"+storeContact.length());
        System.err.println(description==null?"null":"not null");
        if(description.length()!=0){
        shop.setDescription(description);
        }
         if(operatinghours.length()!=0){
         shop.setOperatinghours(operatinghours);
        }
         if(storeContact.length()!=0){
         shop.setStoreContact(storeContact);
        }
        
        em.flush();
    }
    
}
