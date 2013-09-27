/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Shop;
import entity.ShopBill;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface ManageTenantBeanRemote {
    public double calculateCommission(double revenue);
    public Shop viewBill(String ShopName,String shopOwner);
    public void creatBill(double RentalFee, double commission, Long ShopID) ;
    public void EditBillStatus(Long BillID);
   public List<Shop> getShopList();
    public Collection<ShopBill> sendBills(Long ShopID);
    public void EditShopInfo(Long BillID,String description,String operatinghours,String storeContact);
    public HashMap<String, Integer> viewTenancyMix();
     public String test ();
 
}
