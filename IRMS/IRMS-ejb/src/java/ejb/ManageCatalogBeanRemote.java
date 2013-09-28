/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import exception.ExistException;
import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface ManageCatalogBeanRemote {
    
     public void addProductItem(Long ShopID,String category,String name,String description
            ,Integer quantityOnHand,BigDecimal unitPrice) throws ExistException;
      public void editProductItem(String category,String name,String description
            ,Integer quantityOnHand,BigDecimal unitPrice);
      public void deleteProductItem(String category,String name);
      public void deliveryItem(String hotelName,String customerName, String customerID, 
            String contactNumber, Integer numOfItems, String description);
      public void updateDeliveryOrder(String status, Long ID) throws ExistException;
}
