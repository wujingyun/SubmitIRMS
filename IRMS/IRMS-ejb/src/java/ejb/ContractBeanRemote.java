/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Contract;
import entity.TenantRecordEntity;
import exception.ExistException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface ContractBeanRemote {
     public void signContract(String ContractType,String Landlord,String Tenant,
            String IdentityCard,String TenantTradeName,List UnitNo,
            String NameOfShoppingCenter,String Purpose
            ,String MinimumRent,String RentRate,String TenantAddress,String LandlordContact
            ,String TenantContact,String upfrontRentalDeposit,Date date,String yearsToRenew)throws ExistException;
      public void reNewContract(String MinimumRent,String RentRate,String upfrontRentalDeposit,Contract contractRecord,String yearsToRenew)throws ExistException;
      public void terminateContract(String IdentityCard,String TenantTradeName) throws ExistException;
        public List<TenantRecordEntity> getExistingTenant ();
        public List<Contract> getContractList();
}
