/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Shop;
import entity.ShopBill;
import java.util.Calendar;
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
   // double commissionFee;
    
    // check on jasper report not included in the first system release.
    public void generateQuarterlyReport(){
    
    }
    
    
    public double calculateCommission(double revenue){
        double commissionFee;
       commissionFee = commisionRate * revenue;
        return commissionFee;
    }
    //view only bills from one shop
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
    public void creatBill(double RentalFee, double commission, String ShopName,
            String shopOwner) {
        bill = new ShopBill();
        double figure = calculateCommission(commission);
        bill.createBill(RentalFee, figure);
        Calendar cal = Calendar.getInstance();
        bill.setDateIssued(cal);     

        shop = new Shop();
        Query q = em.createQuery("SELECT * FROM shop WHERE name = : storeName AND"
                + "owner = : shopOwner");
        q.setParameter("storeName", ShopName);
        q.setParameter("shopOwner", shopOwner);

        shop = (Shop) q.getSingleResult();
        shop.getBills().add(bill);
        em.persist(bill);
        em.flush();
    }

    public void EditBillStatus(String BillID) {
        bill = new ShopBill();
        bill = em.find(ShopBill.class, BillID);
        bill.setBillStatus(false);
        em.flush();
    }
}
