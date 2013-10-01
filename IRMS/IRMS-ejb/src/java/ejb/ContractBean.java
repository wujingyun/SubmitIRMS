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
import java.util.Date;
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
    private List<TenantRecordEntity> tenantList;
    private List<Contract> contractList;
    public ContractBean(){
    }
   
    @Override
    public List<TenantRecordEntity> getExistingTenant (){
        tenantList= new ArrayList();
        String ejbql ="SELECT DISTINCT t FROM TenantRecordEntity t GROUP BY t.IdentityCard";
        Query q = em.createQuery(ejbql);
        for(Object o: q.getResultList()){
            TenantRecordEntity t =(TenantRecordEntity)o;
            tenantList.add(t);       
        }
        em.flush();
       return tenantList;
      
   }
    
    @Override
    public List<Contract> getContractList(){
            String ejbql ="SELECT c FROM Contract c";  
            Query q = em.createQuery(ejbql);
            contractList =new ArrayList();
            Calendar cal = Calendar.getInstance();
            for(Object o: q.getResultList()){
                Contract c = (Contract)o;
                
                 if(c.getDateOfExpiry().getTime().before(cal.getTime())){
                     
                    c.setContractExpiry(true);
                    System.err.println("status contract expiry"+c.isContractExpiry()+"");
                    System.err.println(c.getDateOfExpiry().getTime());
                    System.err.println(cal.getTime());
                    
                }else{
                     c.setContractExpiry(false);
                 }
                 
                if(c.isContractStatus()!=false){                    
                    contractList.add(c);
                }
               
            }  
        for (Contract e : contractList) {
            e.getUnits().size();
        }
            em.flush();
         
            return contractList;
    }
    
    
   //
    @Override
   public void signContract(String ContractType,String Landlord,String Tenant,
            String IdentityCard,String TenantTradeName,List UnitNo,
            String NameOfShoppingCenter,String Purpose
            ,String MinimumRent,String RentRate,String TenantAddress,String LandlordContact
            ,String TenantContact,String upfrontRentalDeposit,Date date,String yearsToRenew)throws ExistException{
            
            String randomPassword;
      //      Collection<Unit> shopUnit = new ArrayList<Unit>();
            unitEntity     = new Unit();
            contractEntity = new Contract();
            shopEntity = new Shop();
            tenant     = new ShopOwner();
            tenantRecord = new TenantRecordEntity();
            System.out.println("sign contract years: "+ yearsToRenew);
        if(Tenant==null) throw new ExistException("No tenant information was provided！");
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
        //    System.out.println("get date1 :"+date1.getTime());
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            System.out.println("Contract signing :today is "+cal);
            contractEntity.setDateOfExecution(cal);
            Calendar futureCal = Calendar.getInstance();
            futureCal.setTime(date);
            int year = Integer.parseInt(yearsToRenew);
            System.out.println("Contract year  "+year);
            futureCal.add(Calendar.YEAR, year);
            contractEntity.setDateOfExpiry(futureCal);
            
            em.persist(contractEntity);
            System.out.println("Contract signing : contractEntity 2: "+contractEntity.getTenant());
            
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
            System.out.println("Contract signing : contractEntity 2: "+contractEntity.getTenant());
            em.persist(shopEntity);
            em.persist(tenant);          
            em.flush();             
    }
    
   //
   @Override
   public void reNewContract(String MinimumRent,String RentRate,String upfrontRentalDeposit,Contract contractRecord,String yearsToRenew)throws ExistException{
            System.out.println("year "+yearsToRenew);
            System.out.println("mini rent"+MinimumRent);
            System.out.println("rent rate"+RentRate);
            System.out.println("upfront rental deposit"+upfrontRentalDeposit);
            System.out.println("why not tenant!");
            System.out.println("contract name "+contractRecord.getTenant());
            
            contractEntity= new Contract();
            contractEntity = contractRecord;
            if(contractEntity ==null)throw new ExistException("The contract does not exist!");  
            
            contractEntity.renewContract(contractRecord.getContractType(), contractRecord.getLandlord(),
                    contractRecord.TenantTradeName(),contractRecord.getNameOfShoppingCenter(), 
                    contractRecord.getPurpose(), MinimumRent, RentRate, contractRecord.getLandlordContact(), 
                    upfrontRentalDeposit,contractRecord.getUnits());
             int year = Integer.parseInt(yearsToRenew);          
            System.out.println("year "+year);
            Calendar cal = Calendar.getInstance();
            contractEntity.setDateOfExecution(cal);
            Calendar futureCal = contractEntity.getDateOfExpiry();
            futureCal.add(Calendar.YEAR, year);
            contractEntity.setDateOfExpiry(futureCal);
            
       //     unitEntity     = new Unit();
       /*     int totalArea;
                totalArea = 0;
         
            for(Iterator it = contractEntity.getUnits().iterator();it.hasNext();){
                unitEntity = (Unit)it.next();
                unitEntity.setUnitAvailability(true);
                contractEntity.getUnits().remove(unitEntity);
                unitEntity.setContract(null);
                em.flush();
            }    */
             
                
         /*   for (Iterator it = UnitNo.iterator(); it.hasNext();){
               String result = (String)it.next();
               unitEntity = em.find(Unit.class, result);  
               unitEntity.setUnitAvailability(false);
               totalArea +=unitEntity.getUnitSpace();
               unitEntity.setContract(contractEntity);
               contractEntity.getUnits().add(unitEntity);
               em.refresh(unitEntity);
               em.flush();
           }*/
       //      contractEntity.setFloorArea(totalArea);
      //       contractEntity.getShop().renewShop(totalArea);
               em.persist(contractEntity);
            // em.persist(shopEntity);
               em.flush();
       //     shopEntity.updateShop(TenantTradeName, Tenant, FloorArea);
       
   }
    @Override
   public void terminateContract(Long ContractID) throws ExistException{
       contractEntity = new Contract();
       shopBill = new ShopBill();
       shopEntity = new Shop();
       tenant = new ShopOwner();
         System.out.println("SessionBean Mallspace :terminateContract : "+ContractID);
           
            contractEntity= em.find(Contract.class, ContractID);
            System.out.println("contract"+contractEntity.getId()+" found");
            if(contractEntity ==null)throw new ExistException("The contract does not exist!"); 
            
              System.out.println("SessionBean Mallspace :terminateContract ck bills: ");
            for(Iterator it = contractEntity.getShop().getBills().iterator();it.hasNext();){
                 System.out.println("checking on bills");
                shopBill =(ShopBill)it.next();
                if(shopBill.isBillStatus()==true || shopBill==null) throw new ExistException("The shopBill is"
                        + " not settled");
            }
             System.out.println("SessionBean Mallspace :terminateContract removing units: ");
             for(Iterator it = contractEntity.getUnits().iterator();it.hasNext();){
               
                unitEntity = (Unit)it.next();
                System.err.println("units"+unitEntity.getUnitNo());
               
                unitEntity.setUnitAvailability(true);
                unitEntity.setContract(null);
                System.err.println("units status "+unitEntity.isUnitAvailability());
                contractEntity.setUnits(null);
                em.flush();
            }
             System.err.println("finished setting units, start getting to status");
             contractEntity.setContractStatus(false);
             shopEntity =contractEntity.getShop();
           //  em.remove(shopEntity);
             contractEntity.setShop(null);
             shopEntity.setContract(null);
             
             tenant =contractEntity.getShopTenant();
             contractEntity.setTenant(null);
             tenant.setContract(null);
           
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
