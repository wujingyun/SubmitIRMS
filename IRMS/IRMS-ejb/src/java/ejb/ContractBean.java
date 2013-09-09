/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Contract;
import entity.Shop;
import entity.ShopBill;
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
    private ShopBill shopBill;
    
    public ContractBean(){
    }
    
   
   public void signContract(String ContractType,String Landlord,String Tenant,
            String IdentityCard,String TenantTradeName,List UnitNo,
            String NameOfShoppingCenter,String Purpose
            ,String MinimumRent,String RentRate,String TenantAddress,String LandlordContact
            ,String TenantContact,String upfrontRentalDeposit)throws ExistException{
            
            String randomPassword;
      //      Collection<Unit> shopUnit = new ArrayList<Unit>();
            unitEntity     = new Unit();
            contractEntity = new Contract();
            shopEntity = new Shop();
            tenant     = new ShopOwner();
            
        if(UnitAvailabilityCheck(UnitNo) ==false)
            throw new ExistException("The unit has been taken！");
           
            contractEntity.createContract(ContractType, Landlord, Tenant, IdentityCard,
                    TenantTradeName, NameOfShoppingCenter,
                    Purpose, MinimumRent, RentRate, TenantAddress, 
                    LandlordContact, TenantContact, upfrontRentalDeposit);                        
            
            Calendar cal = Calendar.getInstance();
            contractEntity.setDateOfExecution(cal);
            Calendar futureCal = Calendar.getInstance();
            futureCal.add(Calendar.YEAR, 1);
            contractEntity.setDateOfExpiry(futureCal);
            
            unitEntity     = new Unit();
            int totalArea;
                totalArea = 0;
                
            for (Iterator it = UnitNo.iterator(); it.hasNext();){
               String result = (String)it.next();
               unitEntity = em.find(Unit.class, result);  
               unitEntity.setUnitAvailability(false);
               totalArea +=unitEntity.getUnitSpace();
               unitEntity.setContract(contractEntity);
               contractEntity.getUnits().add(unitEntity);
               em.refresh(unitEntity);
           }
            
            contractEntity.setFloorArea(totalArea);
            shopEntity.createShop(TenantTradeName, Tenant, totalArea);
         
            randomPassword = randomPassGeneration();
            tenant.createShopOwner(Tenant+IdentityCard, randomPassword, Tenant, 
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
                       
            contractEntity.renewThisContract(IdentityCard,Purpose
            ,MinimumRent,RentRate, TenantAddress, LandlordContact
            ,TenantContact, upfrontRentalDeposit,TenantTradeName);
                       
            Calendar cal = Calendar.getInstance();
            contractEntity.setDateOfExecution(cal);
            Calendar futureCal = contractEntity.getDateOfExpiry();
            futureCal.add(Calendar.YEAR, 1);
            contractEntity.setDateOfExpiry(futureCal);
            
            unitEntity     = new Unit();
            int totalArea;
                totalArea = 0;
         
            for(Iterator it = contractEntity.getUnits().iterator();it.hasNext();){
                unitEntity = (Unit)it.next();
                unitEntity.setUnitAvailability(true);
                contractEntity.getUnits().remove(unitEntity);
                em.flush();
            }    
                
                
            for (Iterator it = UnitNo.iterator(); it.hasNext();){
               String result = (String)it.next();
               unitEntity = em.find(Unit.class, result);  
               unitEntity.setUnitAvailability(false);
               totalArea +=unitEntity.getUnitSpace();
               unitEntity.setContract(contractEntity);
               contractEntity.getUnits().add(unitEntity);
               em.refresh(unitEntity);
               em.flush();
           }
             contractEntity.setFloorArea(totalArea);
             contractEntity.getShop().renewShop(totalArea);
             em.persist(contractEntity);
             em.persist(shopEntity);
             em.flush();
       //     shopEntity.updateShop(TenantTradeName, Tenant, FloorArea);
       
   }
   public void terminateContract(String IdentityCard,String TenantTradeName) throws ExistException{
       contractEntity = new Contract();
       shopBill = new ShopBill();
       shopEntity = new Shop();
       tenant = new ShopOwner();
       Query q =em.createQuery("SELECT * FROM contract WHERE identitycard = :ic AND"
                    + "tenanttradename = :tradename");
            q.setParameter("ic",IdentityCard);
            q.setParameter("tradename", TenantTradeName);
            contractEntity= (Contract)q.getSingleResult();
            
            
            for(Iterator it = contractEntity.getShop().getBills().iterator();it.hasNext();){
                shopBill =(ShopBill)it.next();
                if(shopBill.isBillStatus()==true) throw new ExistException("the shopBill is"
                        + "not settled");
            }
            
             for(Iterator it = contractEntity.getUnits().iterator();it.hasNext();){
                unitEntity = (Unit)it.next();
                unitEntity.setUnitAvailability(true);
                contractEntity.getUnits().remove(unitEntity);
                em.flush();
            }
             shopEntity =contractEntity.getShop();
             em.remove(shopEntity);
             contractEntity.setShop(null);
       //      shopEntity.setContract(null);
             
             tenant =contractEntity.getShopTenant();
             contractEntity.setTenant(null);
             tenant.setContract(null);
             em.remove(contractEntity);
             em.flush();             
   }
   
   
   public boolean UnitAvailabilityCheck(List UnitNo){
        unitEntity     = new Unit();
        for (Iterator it = UnitNo.iterator(); it.hasNext();) {
            String singleUnit = (String)it.next();
            unitEntity = em.find(Unit.class, singleUnit);      
            if(unitEntity.isUnitAvailability()!=true)
                return false;
            
        } 
        return true;
   } 
   public String randomPassGeneration(){
        String uuid = UUID.randomUUID().toString();
        String pass = uuid.substring(0,8);
        System.out.println("uuid = " + pass); 
        return pass;
   }
   
    
}
