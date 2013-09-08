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
public class ManageMallSpaceBean implements ManageMallSpaceBeanRemote {

    @PersistenceContext()
    EntityManager em;
    
    public void viewTenancyMix(){
        
    }
    
    public void repartitionMall(){
        
    }
    
    public void addNewUnit(){
        
    }
    
    public void deleteUnit(){
        
    }
          

}
