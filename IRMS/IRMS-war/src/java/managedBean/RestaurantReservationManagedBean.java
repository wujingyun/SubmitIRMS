/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.EmailSessionBean;

import ejb.EmailSessionBean;
   
import ejb.RestaurantSessionBeanRemote;
import entity.FbCombo;
import entity.FbCombo;
import entity.FbRDish;
import entity.FbRDish;
import entity.FbRestaurant;
import entity.FbRestaurant;
import entity.FbTable;
import entity.FbTable;
import exception.ExistException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@ViewScoped
public class RestaurantReservationManagedBean implements Serializable {

    @EJB
    RestaurantSessionBeanRemote rsbr;
    @EJB
    EmailSessionBean emailSessionBean;
    private FbRestaurant restaurant;
    private FbRestaurant selectedRestaurant;
    private List<FbRestaurant> restaurantList;
    private List<FbRDish> dishList;
    private List<FbCombo> comboList;
    private List<FbTable> tableList;
    private FbTable selectedTable;
    private FbTable table;
    private int capacity;
    private int numOfTables;
    private FbCombo selectedCombos;
    private FbRDish dish;
    private FbRDish selectedDish;
    private FbCombo combo;
    private String fbRName;
    private List<String> period;
    //------------types of dish
    private List<FbRDish> appetizer = new ArrayList<FbRDish>();
    private List<String> selectedAppetizer = new ArrayList<String>();
    private List<FbRDish> mainDish = new ArrayList<FbRDish>();
    private List<String> selectedMainDish = new ArrayList<String>();
    private List<FbRDish> sideDish = new ArrayList<FbRDish>();
    private List<String> selectedSideDish = new ArrayList<String>();
    private List<FbRDish> veggie = new ArrayList<FbRDish>();
    private List<String> selectedVeggie = new ArrayList<String>();
    private List<FbRDish> soup = new ArrayList<FbRDish>();
    private List<String> selectedSoup = new ArrayList<String>();
    private List<FbRDish> dessert = new ArrayList<FbRDish>();
    private List<String> selectedDessert = new ArrayList<String>();
    private List<String> listOfallDishesInCombo = new ArrayList<String>();
    private String selectPeriod;
    private String contactInfo; 
    private Date attendDate; 
    private String timeSlot;
      private String customerName;
    
    @PostConstruct
    public void init() {
        this.restaurantList = (List<FbRestaurant>) rsbr.getRestaurants();
    }

    public RestaurantReservationManagedBean() {
        this.restaurant = new FbRestaurant();
        this.dish = new FbRDish();
        this.combo = new FbCombo();
        this.table = new FbTable();
    }

    public void reinitRest() {
        this.restaurant = new FbRestaurant();

    }

    public void reinitDish() {
        this.dish = new FbRDish();
    }

    public void handleRestaurantChange() {
        System.err.println("handle change " + fbRName);

        if (fbRName != null && !fbRName.equals("")) {
            dishList = (List<FbRDish>) rsbr.getDishFromOneRestaurant(fbRName);
        } else {
            System.out.println("empty restaurant name");
        }
    }
 
         public void reservatFbTable(ActionEvent actionEvent) throws ExistException {
        try {
                   
                    System.out.println("BookTicket----->set Calendar"+attendDate);
                     System.out.println("reservatFbTable:  "+fbRName+customerName+contactInfo+attendDate+selectPeriod+timeSlot);
            rsbr.getReservation(fbRName,customerName, contactInfo, attendDate,selectPeriod, timeSlot);
            emailSessionBean.emailRestaurantResConfirm(contactInfo, customerName, fbRName, attendDate, selectPeriod);
        
            } 
            catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fail to reserve","No available table"));
                  return;
            }

       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Restaurant reserved","Please check your email"));
                
        }
   
    public List<FbRestaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<FbRestaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public FbRestaurant getRestaurant() {
        return restaurant;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
    }

    public void setRestaurant(FbRestaurant restaurant) {
        this.restaurant = restaurant;
    }

    public FbRestaurant getSelectedRestaurant() {
        return selectedRestaurant;
    }

    public void setSelectedRestaurant(FbRestaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
    }

    public FbRDish getDish() {
        return dish;
    }

    public void setDish(FbRDish dish) {
        this.dish = dish;
    }

    public String getFbRName() {
        return fbRName;
    }

    public void setFbRName(String fbRName) {
        this.fbRName = fbRName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<FbRDish> getDishList() {
        return dishList;
    }

    public void setDishList(List<FbRDish> dishList) {
        this.dishList = dishList;
    }

    public FbRDish getSelectedDish() {
        return selectedDish;
    }

    public void setSelectedDish(FbRDish selectedDish) {
        this.selectedDish = selectedDish;
    }

    public List<FbCombo> getComboList() {
        return comboList;
    }

    public void setComboList(List<FbCombo> comboList) {
        this.comboList = comboList;
    }

    public FbCombo getCombo() {
        return combo;
    }

    public void setCombo(FbCombo combo) {
        this.combo = combo;
    }

    public List<FbRDish> getAppetizer() {
        return appetizer;
    }

    public void setAppetizer(List<FbRDish> appetizer) {
        this.appetizer = appetizer;
    }

    public List<String> getSelectedAppetizer() {
        return selectedAppetizer;
    }

    public void setSelectedAppetizer(List<String> selectedAppetizer) {
        this.selectedAppetizer = selectedAppetizer;
    }

    public List<FbRDish> getMainDish() {
        return mainDish;
    }

    public void setMainDish(List<FbRDish> mainDish) {
        this.mainDish = mainDish;
    }

    public List<String> getSelectedMainDish() {
        return selectedMainDish;
    }

    public void setSelectedMainDish(List<String> selectedMainDish) {
        this.selectedMainDish = selectedMainDish;
    }

    public List<FbRDish> getSideDish() {
        return sideDish;
    }

    public void setSideDish(List<FbRDish> sideDish) {
        this.sideDish = sideDish;
    }

    public List<String> getSelectedSideDish() {
        return selectedSideDish;
    }

    public void setSelectedSideDish(List<String> selectedSideDish) {
        this.selectedSideDish = selectedSideDish;
    }

    public List<FbRDish> getVeggie() {
        return veggie;
    }

    public void setVeggie(List<FbRDish> veggie) {
        this.veggie = veggie;
    }

    public List<FbRDish> getSoup() {
        return soup;
    }

    public void setSoup(List<FbRDish> soup) {
        this.soup = soup;
    }

    public List<String> getSelectedSoup() {
        return selectedSoup;
    }

    public void setSelectedSoup(List<String> selectedSoup) {
        this.selectedSoup = selectedSoup;
    }

    public List<FbRDish> getDessert() {
        return dessert;
    }

    public void setDessert(List<FbRDish> dessert) {
        this.dessert = dessert;
    }

    public List<String> getSelectedDessert() {
        return selectedDessert;
    }

    public void setSelectedDessert(List<String> selectedDessert) {
        this.selectedDessert = selectedDessert;
    }

    public List<String> getSelectedVeggie() {
        return selectedVeggie;
    }

    public void setSelectedVeggie(List<String> selectedVeggie) {
        this.selectedVeggie = selectedVeggie;
    }

    public FbCombo getSelectedCombos() {
        return selectedCombos;
    }

    public void setSelectedCombos(FbCombo selectedCombos) {
        this.selectedCombos = selectedCombos;
    }

    public List<String> getListOfallDishesInCombo() {
        return listOfallDishesInCombo;
    }

    public void setListOfallDishesInCombo(List<String> listOfallDishesInCombo) {
        this.listOfallDishesInCombo = listOfallDishesInCombo;
    }

    public List<FbTable> getTableList() {
        return tableList;
    }

    public void setTableList(List<FbTable> tableList) {
        this.tableList = tableList;
    }

    public FbTable getTable() {
        return table;
    }

    public void setTable(FbTable table) {
        this.table = table;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public FbTable getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(FbTable selectedTable) {
        this.selectedTable = selectedTable;
    }

    public List<String> getPeriod() {
        return period;
    }

    public void setPeriod(List<String> period) {
        this.period = period;
    }

    public String getSelectPeriod() {
        return selectPeriod;
    }

    public void setSelectPeriod(String selectPeriod) {
        this.selectPeriod = selectPeriod;
    }

   
    
}
