/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Customer;
import entity.FbCombo;
import entity.FbRDish;
import entity.FbReservation;
import entity.FbRestaurant;
import entity.FbTable;
import exception.ExistException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class RestaurantSessionBean implements RestaurantSessionBeanRemote {

    @PersistenceContext()
    EntityManager em;
    private FbReservation reservation;
    private FbTable table;
    private FbCombo mealSet;
    private FbRDish dish;
    private FbRestaurant restaurant;
    private Collection<FbReservation> reservationList;
    private Collection<FbTable> tableList;
    private Collection<FbCombo> mealSetList;
    private Collection<FbRDish> dishes;
    private Collection<FbRestaurant> restaurantList;
    private FbReservation fbReservation;
private Customer customer;
    @Override
    public void createRestaurant(String restaurantName, String contact) {
        restaurant = new FbRestaurant();
        restaurant.setRestaurantName(restaurantName);
        restaurant.setContact(contact);
        em.persist(restaurant);
    }

    @Override
    public void createRDish(String restaurantName, String name, String tye, double price) {
        dish = new FbRDish();
        restaurant = new FbRestaurant();
        restaurant = em.find(FbRestaurant.class, restaurantName);

        dish.setName(name);
        dish.setType(tye);
        dish.setPrice(price);
        dish.setInMealSet(false);
        restaurant.getDish().add(dish);
        em.persist(dish);
    }

    @Override
    public void editRDish(String name, String type, double price) {
        dish = new FbRDish();
        dish = em.find(FbRDish.class, name);

        dish.setType(type);
        dish.setPrice(price);
        em.flush();
    }

    @Override
    public void deleteRDish(String resturantName, String name) throws ExistException {
        dish = new FbRDish();
        dish = em.find(FbRDish.class, name);
        restaurant = new FbRestaurant();
        restaurant = em.find(FbRestaurant.class, resturantName);

        if (dish.isInMealSet() == true) {
            throw new ExistException("Dish is included in the Meal Set!");
        }
        restaurant.getDish().remove(dish);
        em.remove(dish);
        em.flush();
    }

    @Override
    public void createCombo(String restaurantName, String comboName, List selectedDish) {
        double packagePrice = 0;
        mealSet = new FbCombo();
        restaurant = new FbRestaurant();

        restaurant = em.find(FbRestaurant.class, restaurantName);
        mealSet.setName(comboName);

        mealSet.setRestaurant(restaurant);
        em.persist(mealSet);

        for (Iterator it = selectedDish.iterator(); it.hasNext();) {
            String dishName = (String) it.next();
            dish = new FbRDish();
            System.err.println("session bean RDISH NAME " + dishName);
            dish = em.find(FbRDish.class, dishName);
            System.err.println("session bean RDISH NAME after emfind" + dish.getName());
            dish.setInMealSet(true);
            mealSet.getDish().add(dish);
            packagePrice += dish.getPrice();
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String l = df.format(packagePrice);
        packagePrice = Double.parseDouble(l);
        mealSet.setPrice(packagePrice);
        restaurant.getCombo().add(mealSet);
        em.persist(mealSet);
        em.flush();
    }

    @Override
    public void deleteDishInPackage(String comboName, String dishName) {
        mealSet = new FbCombo();
        dish = new FbRDish();
        double packagePrice = 0;

        mealSet = em.find(FbCombo.class, comboName);
        dish = em.find(FbRDish.class, dishName);

        packagePrice = mealSet.getPrice() - dish.getPrice();
        DecimalFormat df = new DecimalFormat("#.00");
        String l = df.format(packagePrice);
        packagePrice = Double.parseDouble(l);
        mealSet.setPrice(packagePrice);

        mealSet.getDish().remove(dish);
        em.flush();
    }

    @Override
    public void editCombo(String comboName, List selectedDish) throws ExistException {
        double packagePrice = 0;
        mealSet = new FbCombo();
        mealSet = em.find(FbCombo.class, comboName);
        packagePrice = mealSet.getPrice();
        System.err.println("sessionbean edit combo Name: " + comboName);

        for (Iterator it = selectedDish.iterator(); it.hasNext();) {
            String dishName = (String) it.next();
            dish = new FbRDish();
            dish = em.find(FbRDish.class, dishName);
            if (mealSet.getDish().contains(dish)) {
                throw new ExistException("dish already Exist in the Combo!");
            }
            packagePrice += dish.getPrice();
            dish.setInMealSet(true);
            mealSet.getDish().add(dish);
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String l = df.format(packagePrice);
        packagePrice = Double.parseDouble(l);
        mealSet.setPrice(packagePrice);

        em.flush();
    }

    @Override
    public void deleteCombo(String resturantName, String comboName) {
        mealSet = new FbCombo();
        mealSet = em.find(FbCombo.class, comboName);
        restaurant = new FbRestaurant();
        restaurant = em.find(FbRestaurant.class, resturantName);

        restaurant.getCombo().remove(mealSet);
        //     mealSet.setRestaurant(null);
        mealSet.getDish().clear();
        em.remove(mealSet);
        em.flush();
    }

    @Override
    public void createFbTable(String restaurantName, int numOfTables, int tableCapacity, List<String> periods) {

        restaurant = new FbRestaurant();
        restaurant = em.find(FbRestaurant.class, restaurantName);
        for (int i = 0; i < numOfTables; i++) {
            table = new FbTable();
            table.createTable(tableCapacity);
            if (!periods.contains("Noon")) {
                table.setNoonStatus(true);
            }
            if (!periods.contains("Dinner")) {
                table.setDinnerStatus(true);
            }
            if (!periods.contains("Supper")) {
                table.setSupperStatus(true);
            }
            restaurant.getTables().add(table);
            table.setRestaurant(restaurant);
            em.persist(table);
        }
        em.flush();

    }
    /*  
     public void changeTableStatus(){
     String ejbql ="SELECT t FROM FbTable t";
     Query q = em.createQuery(ejbql);
     for (Object o : q.getResultList()) {
     FbTable ft =(FbTable)o;
     ft.setReservation(null);
     }
     }*/

    @Override
    public void editFbTable(Long tableNo, int tableCapacity, boolean noonTableStatus, boolean dinnerTableStatusm, boolean supperTableStatus) {
        table = new FbTable();
        table = em.find(FbTable.class, tableNo);
        table.setTableCapacity(tableCapacity);
        table.setNoonStatus(noonTableStatus);
        table.setDinnerStatus(dinnerTableStatusm);
        table.setSupperStatus(supperTableStatus);
        table.setInReservation(false);
        em.flush();
    }

    @Override
    public void deleteFbTable(String resturantName, Long tableNo) throws ExistException {
        table = new FbTable();
        table = em.find(FbTable.class, tableNo);
        if (!table.isInReservation()) {
        } else {
            throw new ExistException("There is a reservation on this table!");
        }
        restaurant = new FbRestaurant();
        restaurant = em.find(FbRestaurant.class, resturantName);
        restaurant.getTables().remove(table);

        em.remove(table);
        em.flush();
    }

    /*
     public void createRestaurantReservation(String restaurantName, String customerName,String customerContact, Calendar reservationDate, String slot,List<Long> tableNos) {
     reservation = new FbReservation();
     reservation.createReservation(customerName,customerContact, reservationDate, slot);        
     restaurant = new FbRestaurant ();
     restaurant =em.find(FbRestaurant.class,restaurantName);
     reservation.setRestaurant(restaurant);
     for (Long id : tableNos) {
     table = new FbTable();
     table = em.find(FbTable.class, id);
     reservation.getTables().add(table);
     table.setReservation(reservation);
     }
     restaurant.setReservation(reservation);
     em.persist(reservation);
     em.flush();
     }*/
    /*   
     public void deleteReservation(Long reservationID){
     reservation = new FbReservation();
     reservation = em.find(FbReservation.class, reservationID);
         
     for (Iterator it =reservation.getTables().iterator();it.hasNext();){
     table = new FbTable();
     table = (FbTable)it.next();
     table.setReservation(null);
     }
         
     reservation.setTables(null);
     reservation.getRestaurant().setReservation(null);
     reservation.setRestaurant(null);
     em.flush();
     }*/
    //------------------getting methods
    @Override
    public Collection<FbRDish> getDishFromOneRestaurant(String restaurantName) {
        dishes = new ArrayList<FbRDish>();
        restaurant = new FbRestaurant();
        restaurant = em.find(FbRestaurant.class, restaurantName);
        for (FbRDish d : restaurant.getDish()) {
            dishes.add(d);
        }
        return dishes;
    }

    @Override
    public Collection<FbRestaurant> getRestaurants() {
        restaurantList = new ArrayList<FbRestaurant>();
        String ejbql = "SELECT d FROM FbRestaurant d";
        Query q = em.createQuery(ejbql);
        for (Object o : q.getResultList()) {
            FbRestaurant fa = (FbRestaurant) o;

            fa.getCombo().size();
            fa.getDish().size();
            fa.getTables().size();
            fa.getReservation().size();
            restaurantList.add(fa);
        }
        return restaurantList;
    }

    @Override
    public Collection<FbCombo> getComboList(String restaurantName) {
        mealSetList = new ArrayList<FbCombo>();
        restaurant = new FbRestaurant();
        restaurant = em.find(FbRestaurant.class, restaurantName);

        for (FbCombo fa : restaurant.getCombo()) {
            fa.getDish().size();
            mealSetList.add(fa);
        }
        return mealSetList;
    }

    @Override
    public Collection<FbTable> getTableFromOneRestaurant(String restaurantName) {
        restaurant = new FbRestaurant();
        restaurant = em.find(FbRestaurant.class, restaurantName);
        tableList = new ArrayList<FbTable>();
        for (FbTable tb : restaurant.getTables()) {
            tableList.add(tb);
        }
        return tableList;
    }

    @Override
    public void getReservation(String fbName,String customerName, String contact, Date attendDate, String selectPeriod, String slot) throws ExistException {
        restaurant = new FbRestaurant();
        fbReservation = new FbReservation();
        System.out.println("RestaurantsessionBean--> getReservation");

        Query query = em.createQuery("SELECT  r FROM FbRestaurant r where r.restaurantName=?1");
        query.setParameter(1,fbName);
        restaurant=(FbRestaurant) query.getSingleResult();
        
        
        Query q = em.createQuery("SELECT  r FROM FbReservation r where r.reservationDate=?1 and r.restaurant.restaurantName=?2");
        q.setParameter(1, attendDate);
        q.setParameter(2, fbName);
        if (q.getResultList().size() != 0) {
            System.out.println("RestaurantsessionBean--> getReservation--> exist reservation date");
            fbReservation = (FbReservation) q.getSingleResult();
            if (selectPeriod.equalsIgnoreCase("Noon")) {
                if (fbReservation.getNoonNum() - 1 > 0) {
                    
                    fbReservation.setNoonNum(fbReservation.getNoonNum() - 1);
                }
                else throw new ExistException("No table available!");
            }
            if (selectPeriod.equalsIgnoreCase("Dinner")) {
                if (fbReservation.getDinnerNum() - 1 > 0) {
                    fbReservation.setDinnerNum(fbReservation.getDinnerNum() - 1);
                }
                else throw new ExistException("No table available!");
               
            }
            if (selectPeriod.equalsIgnoreCase("Supper")) {
                   if (fbReservation.getDinnerNum() - 1 > 0) {
                     fbReservation.setSupperNum(fbReservation.getSupperNum() - 1);
                }
                else throw new ExistException("No table available!");
            }
        } else {
    System.out.println("RestaurantsessionBean--> getReservation--> no exist reservation date");
    System.out.println("RestaurantsessionBean--> getReservation--> no exist reservation date"+fbName+customerName+contact+attendDate+slot);
    
            fbReservation.createReservation(customerName, contact, attendDate, slot);
            fbReservation.setRestaurant(restaurant);
            
            em.persist(fbReservation);
            restaurant.getReservation().add(fbReservation);
           
        em.flush();
            System.out.println("RestaurantsessionBean--> getReservation--> create reservation date");
        }
     

    }
}
