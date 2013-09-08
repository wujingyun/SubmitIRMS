/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Contract;
import entity.Shop;
import entity.ShopOwner;
import entity.Unit;
import exception.ExistException;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
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
    private ShopOwner tenant;
    public ContractBean(){
    }
    
   public void signContract(String ContractType,String Landlord,String Tenant,
            String IdentityCard,String TenantTradeName,List UnitNo,
            String NameOfShoppingCenter,String FloorArea,String Purpose
            ,String MinimumRent,String RentRate,String TenantAddress,String LandlordContact
            ,String TenantContact,String upfrontRentalDeposit)throws ExistException{
            
            String randomPassword;
      //      Collection<Unit> shopUnit = new ArrayList<Unit>();
            unitEntity     = new Unit();
            contractEntity = new Contract();
            shopEntity = new Shop();
            tenant     = new ShopOwner();
            
        for (Iterator it = UnitNo.iterator(); it.hasNext();) {
            String singleUnit = (String)it.next();
            unitEntity = em.find(Unit.class, singleUnit);      
            if(unitEntity.isUnitAvailability()!=true)
                throw new ExistException("The unit has been taken！");
        } 
           
            contractEntity.createContract(ContractType, Landlord, Tenant, IdentityCard,
                    TenantTradeName, NameOfShoppingCenter, FloorArea,
                    Purpose, MinimumRent, RentRate, TenantAddress, 
                    LandlordContact, TenantContact, upfrontRentalDeposit);                        
            
            Calendar cal = Calendar.getInstance();
            contractEntity.setDateOfExecution(cal);
            Calendar futureCal = Calendar.getInstance();
            futureCal.add(Calendar.YEAR, 1);
            contractEntity.setDateOfExpiry(cal);
                      
            for (Iterator it = UnitNo.iterator(); it.hasNext();){
               String result = (String)it.next();
               unitEntity.setUnitNo(result);
               unitEntity.setUnitAvailability(false);
               unitEntity.setContract(contractEntity);
               contractEntity.getUnits().add(unitEntity);
           }
      
            shopEntity.createShop(TenantTradeName, Tenant, FloorArea);
         
            randomPassword = randomPassGeneration();
            tenant.createShopOwner(Tenant+IdentityCard, randomPassword, true, Tenant, 
                      IdentityCard, TenantContact);
            
            contractEntity.setShop(shopEntity);
            shopEntity.setContract(contractEntity);
            contractEntity.setShopTenant(tenant);
            tenant.setContract(contractEntity);
                           
            em.persist(contractEntity);
            em.persist(shopEntity);
            em.persist(tenant);
            em.flush();             
    }
   
   public void reNewContract(String IdentityCard,List UnitNo,String FloorArea,String Purpose
            ,String MinimumRent,String RentRate,String TenantAddress,String LandlordContact
            ,String TenantContact,String upfrontRentalDeposit,String TenantTradeName) {
            unitEntity     = new Unit();
            contractEntity = new Contract();
            shopEntity = new Shop();
            tenant     = new ShopOwner();
            
            Query q =em.createQuery("SELECT * FROM contract WHERE identitycard = :ic AND"
                    + "tenanttradename = :tradename");
            q.setParameter("ic",IdentityCard);
            q.setParameter("tradename", TenantTradeName);
            contractEntity= (Contract)q.getSingleResult();
            
            
            contractEntity.renewThisContract(IdentityCard,FloorArea, Purpose, MinimumRent,
                    RentRate, TenantAddress, LandlordContact, 
                    TenantContact, upfrontRentalDeposit,TenantTradeName);
            Calendar cal = Calendar.getInstance();
            contractEntity.setDateOfExecution(cal);
            Calendar futureCal = Calendar.getInstance();
            futureCal.add(Calendar.YEAR, 1);
            contractEntity.setDateOfExpiry(cal);
            
       
   }
   public void terminateContract(){
       
   }
   
   public String randomPassGeneration(){
        String uuid = UUID.randomUUID().toString();
        String pass = uuid.substring(0,8);
        System.out.println("uuid = " + pass); 
        return pass;
   }
   
    
}
