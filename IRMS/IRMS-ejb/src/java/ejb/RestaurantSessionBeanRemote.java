/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.FbCombo;
import entity.FbRDish;
import entity.FbRestaurant;
import entity.FbTable;
import exception.ExistException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface RestaurantSessionBeanRemote {

    public Collection<FbRestaurant> getRestaurants();

    public void createRestaurant(String restaurantName, String contact);

    public Collection<FbRDish> getDishFromOneRestaurant(String restaurantName);

    public void editRDish(String name, String type, double price);

    public void deleteRDish(String resturantName, String name) throws ExistException;

    public void createRDish(String restaurantName, String name, String tye, double price);

    public Collection<FbCombo> getComboList(String restaurantName);

    public void createCombo(String restaurantName, String comboName, List selectedDish);

    public void deleteDishInPackage(String comboName, String dishName);

    public void deleteCombo(String resturantName, String comboName);

    public void editCombo(String comboName, List selectedDish) throws ExistException;

  

    public Collection<FbTable> getTableFromOneRestaurant(String restaurantName);

    public void createFbTable(String restaurantName, int numOfTables, int tableCapacity, List<String> periods);

   

    public void deleteFbTable(String resturantName, Long tableNo) throws ExistException;

   

    public void editFbTable(Long tableNo, int tableCapacity, boolean noonTableStatus, boolean dinnerTableStatusm, boolean supperTableStatus);

    public void getReservation(String fbName,String customerName, String contact, Date attendDate, String selectPeriod, String slot) throws ExistException;
   
    
    
}
