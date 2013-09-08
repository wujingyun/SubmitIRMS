/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class ManageCatalogBean implements ManageCatalogBeanRemote {

    @PersistenceContext()
    EntityManager em;
    public void addStoreInfo(){
        
    }
          
    public void editStoreInfo(){
        
    }
    
    public void deleteStoreInfo(){
        
    }
    
    public void deliveryItem(){
        
    }
}
