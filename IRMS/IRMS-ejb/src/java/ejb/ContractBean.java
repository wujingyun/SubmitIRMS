/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Contract;
import entity.Shop;
import entity.ShopBill;
import entity.ShopOwner;
import entity.TenantRecordEntity;
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
    private TenantRecordEntity tenantRecord;
    public ContractBean(){
    }
    
   //finished
    @Override
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
            tenantRecord = new TenantRecordEntity();
        if(UnitAvailabilityCheck(UnitNo) ==false)
            throw new ExistException("The unit has been taken！");
           
            contractEntity.createContract(ContractType, Landlord,
                    TenantTradeName, NameOfShoppingCenter,
                    Purpose, MinimumRent, RentRate, 
                    LandlordContact, upfrontRentalDeposit);   
            contractEntity.createTenantInfo(Tenant, IdentityCard, TenantAddress, TenantContact);
            tenantRecord.createTenantInfo(Tenant, IdentityCard, TenantAddress, TenantContact);
            em.persist(tenantRecord);
            System.out.println("Contract signing : contractEntity 1: "+contractEntity.getPurpose());
            Calendar cal = Calendar.getInstance();
            contractEntity.setDateOfExecution(cal);
            Calendar futureCal = Calendar.getInstance();
            futureCal.add(Calendar.YEAR, 1);
            contractEntity.setDateOfExpiry(futureCal);
            em.persist(contractEntity);
            System.out.println("Contract signing : contractEntity 2: "+contractEntity.getId());
            
            int totalArea;
                totalArea = 0;
                
            for (Iterator it = UnitNo.iterator(); it.hasNext();){
               String result = (String)it.next();
               unitEntity     = new Unit();
               unitEntity = em.find(Unit.class, result);
               System.out.println("Contract signing : unitEntity: "+unitEntity.getUnitNo()+" Space: "+unitEntity.getUnitSpace());  
               System.out.println("Contract signing : contractEntity: "+contractEntity.getPurpose());
               contractEntity.getUnits().add(unitEntity);
               unitEntity.setContract(contractEntity);
               unitEntity.setUnitAvailability(false);             
               totalArea +=unitEntity.getUnitSpace();
             
           }
            System.out.println("Contract signing : size: "+contractEntity.getUnits().size()); 
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
            System.out.println("Contract signing : contractEntity 2: "+contractEntity.getId());
            em.persist(shopEntity);
            em.persist(tenant);          
            em.flush();             
    }
    
   //
   @Override
   public void reNewContract(String IdentityCard,List UnitNo,String FloorArea,String Purpose
            ,String MinimumRent,String RentRate,String TenantAddress,String LandlordContact
            ,String TenantContact,String upfrontRentalDeposit,String TenantTradeName) throws ExistException{
            unitEntity     = new Unit();
            contractEntity = new Contract();
            shopEntity = new Shop();
            tenant     = new ShopOwner();
            System.out.println("SessionBean Mallspace :renewContract : ");
            String ejbql ="SELECT c FROM Contract c WHERE c.IdentityCard =?1 AND c.TenantTradeName =?2";  
            Query q = em.createQuery(ejbql);
            q.setParameter(1,IdentityCard);
            q.setParameter(2, TenantTradeName);
            contractEntity= (Contract)q.getSingleResult();
            
            if(contractEntity ==null)throw new ExistException("The contract does not exist!");  
            
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
                unitEntity.setContract(null);
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
            // em.persist(contractEntity);
            // em.persist(shopEntity);
             em.flush();
       //     shopEntity.updateShop(TenantTradeName, Tenant, FloorArea);
       
   }
    @Override
   public void terminateContract(String IdentityCard,String TenantTradeName) throws ExistException{
       contractEntity = new Contract();
       shopBill = new ShopBill();
       shopEntity = new Shop();
       tenant = new ShopOwner();
         System.out.println("SessionBean Mallspace :terminateContract : ");
            String ejbql ="SELECT c FROM Contract c WHERE c.IdentityCard =?1 AND c.TenantTradeName =?2";  
            Query q = em.createQuery(ejbql);
            q.setParameter(1,IdentityCard);
            q.setParameter(2, TenantTradeName);
            contractEntity= (Contract)q.getSingleResult();
            
            if(contractEntity ==null)throw new ExistException("The contract does not exist!"); 
            
            
            for(Iterator it = contractEntity.getShop().getBills().iterator();it.hasNext();){
                shopBill =(ShopBill)it.next();
                if(shopBill.isBillStatus()==true) throw new ExistException("the shopBill is"
                        + "not settled");
            }
            
             for(Iterator it = contractEntity.getUnits().iterator();it.hasNext();){
                unitEntity = (Unit)it.next();
                contractEntity.getUnits().remove(unitEntity);
                unitEntity.setUnitAvailability(true);
                unitEntity.setContract(null);
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
