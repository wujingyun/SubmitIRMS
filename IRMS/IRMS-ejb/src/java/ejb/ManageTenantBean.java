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
public class ManageTenantBean implements ManageTenantBeanRemote {
    
         @PersistenceContext()
          EntityManager em;
       
        public void addNewTenant(){
        
        }
        
        public void editTenant(){
            
        }
        
        public void deleteTenant(){
            
        }

}
