/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Shop;
import javax.ejb.Remote;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface ManageTenantBeanRemote {
    public double calculateCommission(double revenue);
    public Shop viewBill(String ShopName,String shopOwner);
    public void creatBill(double RentalFee, double commission, String ShopName,
            String shopOwner) ;
    public void EditBillStatus(String BillID);
}
