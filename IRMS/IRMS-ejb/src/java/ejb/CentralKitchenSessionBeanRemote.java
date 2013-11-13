/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.FbContract;
import entity.FbOrderList;
import entity.FbQuotation;
import entity.Fbalacarte;
import entity.Fbpackage;
import exception.ExistException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface CentralKitchenSessionBeanRemote {

    public void createDish(String name, double price, String type, String description) throws ExistException; 

    public Collection<Fbalacarte> getDishList();

    public void editDish(String name, double price, String type, String description);

    public void deleteDish(String name) throws ExistException;

    public void createPackage(String packageName, List<String> selectedDishes);

    public Collection<Fbpackage> getPackageList();

    public void deletePackage(String packageName);

   

    public Collection<Fbalacarte> getDishListInPackage(String packageName) throws ExistException;

   

    public void editPackage(Fbpackage selectedPackage);

    public void editPackage(String packageName, List selectedDishes) throws ExistException;

    public void createOrderList(List<String> selectedPackageName, List<String> selectedDishName);

    public Collection<FbOrderList> getOrderLists();

   

    public void deleteOrderList(List<String> listOfOrderList);

    public void editOrderList(String orderId, List selectedPackageName, List selectedDishName);

    public void emailInitialPassward(String toEmailAdress, Long orderlistID);

    public void createQuotation(Long orderListID, Long ReservationID, int headCount);

    public Collection<FbQuotation> getQuotationList();

    public void editQuotation(Long orderListID, int headCount);

    public void deleteQuotation(Long orderListID);

    public void createContract(Long quotationID, Date dateSigned);

    public Collection<FbContract> getContractList();

    public void updateContractStatus(Long contractID, String status);

  
}
