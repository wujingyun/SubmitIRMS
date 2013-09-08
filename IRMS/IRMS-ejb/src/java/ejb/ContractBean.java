/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Contract;
import entity.Shop;
import entity.Unit;
import exception.ExistException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class ContractBean implements ContractBeanRemote {
    @PersistenceContext()
    EntityManager em;
    
    private Contract contractEntity;
    private Shop shopEntity;
    private Unit unitEntity;
    
    public ContractBean(){
    }
    
   public void createContract(String ContractType,String Landlord,String Tenant,
            String IdentityCard,String TenantTradeName,String UnitNo,
            String NameOfShoppingCenter,String FloorArea,String Purpose
            ,String MinimumRent,String RentRate,String TenantAddress,String LandlordContact
            ,String TenantContact,String upfrontRentalDeposit)throws ExistException{
            
            contractEntity = new  Contract();
            unitEntity = new Unit();
            Query query = em.createNativeQuery("SELECT * FROM unit WHERE unitno = :UnitNo");
                    
    }
   
   public void reNewContract(){
       
   }
   public void terminateContract(){
       
   }
   
   
    
}
