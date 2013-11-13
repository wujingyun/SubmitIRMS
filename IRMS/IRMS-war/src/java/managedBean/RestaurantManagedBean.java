/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

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
public class RestaurantManagedBean implements Serializable {

    @EJB
    RestaurantSessionBeanRemote rsbr;
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

    @PostConstruct
    public void init() {
        this.restaurantList = (List<FbRestaurant>) rsbr.getRestaurants();
    }

    public RestaurantManagedBean() {
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
    public void handleRestaurantTableChange() {
        System.err.println("handle change " + fbRName);

        if (fbRName != null && !fbRName.equals("")) {
            this.tableList = (List<FbTable>) rsbr.getTableFromOneRestaurant(fbRName);
        } else {
            System.out.println("empty restaurant name");
        }
    }

    public void handleRestaurantComboChange() {
        System.err.println("handle change " + fbRName);

        appetizer = new ArrayList<FbRDish>();
        mainDish = new ArrayList<FbRDish>();
        sideDish = new ArrayList<FbRDish>();
        veggie = new ArrayList<FbRDish>();
        soup = new ArrayList<FbRDish>();
        dessert = new ArrayList<FbRDish>();
        if (fbRName != null && !fbRName.equals("")) {
            dishList = (List<FbRDish>) rsbr.getDishFromOneRestaurant(fbRName);

            for (FbRDish a : dishList) {
                if (a.getType().equals("Appetizers")) {
                    appetizer.add(a);
                } else if (a.getType().equals("Main Dish")) {
                    mainDish.add(a);
                } else if (a.getType().equals("Side Dish")) {
                    sideDish.add(a);
                } else if (a.getType().equals("Veggie")) {
                    veggie.add(a);
                } else if (a.getType().equals("Soup")) {
                    soup.add(a);
                } else if (a.getType().equals("Dessert")) {
                    dessert.add(a);
                } else {
                    System.out.println("Nothing here! Welcome to the weirdest part of the program!");
                }
            }

            this.comboList = (List<FbCombo>) rsbr.getComboList(fbRName);
        } else {
            System.out.println("empty attraction name");
        }
    }

    public void deleteDishInPackage(ActionEvent event) {
        try {
            System.err.println("Restaurant  managedBean: delete Dish in package");
            selectedCombos.getDish().remove(selectedDish);
            rsbr.deleteDishInPackage(selectedCombos.getName(), selectedDish.getName());
            this.comboList = (List<FbCombo>) rsbr.getComboList(selectedCombos.getRestaurant().getRestaurantName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A dish Record has been deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the dish record: " + ex.getMessage(), ""));
        }
    }

    public void createRestaurant(ActionEvent event) {
        try {
            System.err.println("restaurant  managedBean: create Restaurant");
            rsbr.createRestaurant(this.getRestaurant().getRestaurantName(), this.getRestaurant().getContact());
            this.restaurantList = (List<FbRestaurant>) rsbr.getRestaurants();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A restaurant record has been added successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while adding the restaurant record: " + ex.getMessage(), ""));
        }
    }

    public void createDish(ActionEvent event) {
        try {
            System.err.println("Restaurant managedBean: create Dish");

            rsbr.createRDish(fbRName, dish.getName(), dish.getType(), dish.getPrice());
            this.dishList.add(dish);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A dish Record has been added successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while adding the dish record: " + ex.getMessage(), ""));
        }
    }

    public void onRowDishEdit(RowEditEvent event) throws ExistException {
        if (selectedDish == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a dish ", ""));
        }
        rsbr.editRDish(selectedDish.getName(), selectedDish.getType(), selectedDish.getPrice());

        FacesMessage msg = new FacesMessage("Dish: " + ((FbRDish) event.getObject()).getName() + " Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDishCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing dish: " + ((FbRDish) event.getObject()).getName() + " ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
     public void onRowTableEdit(RowEditEvent event) throws ExistException {
        if (selectedTable == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a table", ""));
        }
        System.err.println("table no "+selectedTable.getTableNo()+ " noon status"+selectedTable.isNoonStatus()+" dinner Status"+selectedTable.isDinnerStatus()+" dinner"+selectedTable.isSupperStatus());
        rsbr.editFbTable(selectedTable.getTableNo(), selectedTable.getTableCapacity(), selectedTable.isNoonStatus(), selectedTable.isDinnerStatus(), selectedTable.isSupperStatus());
        
        FacesMessage msg = new FacesMessage("Table: " + ((FbTable) event.getObject()).getTableNo() + " Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
     public void onRowTableCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing table: " + ((FbTable) event.getObject()).getTableNo() + " ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
   

    public void deleteDish(ActionEvent event) {
        try {
            System.err.println("Restaurant  managedBean: delete Dish");
            rsbr.deleteRDish(fbRName, selectedDish.getName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A dish Record has been deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the dish record: " + ex.getMessage(), ""));
        }
    }

    public void createCombo(ActionEvent event) {
        try {
            System.err.println("Restaurant managedBean: create Combo");
            listOfallDishesInCombo = new ArrayList<String>();
            listOfallDishesInCombo.addAll(selectedAppetizer);
            listOfallDishesInCombo.addAll(selectedMainDish);
            listOfallDishesInCombo.addAll(selectedSideDish);
            listOfallDishesInCombo.addAll(selectedVeggie);

            listOfallDishesInCombo.addAll(selectedSoup);
            listOfallDishesInCombo.addAll(selectedDessert);
            for (String dishName : listOfallDishesInCombo) {
                System.err.println("dishName: " + dishName);
            }
            System.err.println("Restaurant managedBean: create Combo: r name" + this.getFbRName() + "combo name" + this.getCombo().getName());

            rsbr.createCombo(this.getFbRName(), this.getCombo().getName(), listOfallDishesInCombo);
            this.comboList = (List<FbCombo>) rsbr.getComboList(this.getFbRName());
            this.combo = new FbCombo();
            this.reinitListOfDishAndPackage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A combo Record has been added successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while adding the combo record: " + ex.getMessage(), ""));
        }
    }
    
    public void editCombo(ActionEvent event){
         try {
            System.err.println("Restaurant managedBean: edit Combo");
            listOfallDishesInCombo = new ArrayList<String>();
            listOfallDishesInCombo.addAll(selectedAppetizer);
            listOfallDishesInCombo.addAll(selectedMainDish);
            listOfallDishesInCombo.addAll(selectedSideDish);
            listOfallDishesInCombo.addAll(selectedVeggie);

            listOfallDishesInCombo.addAll(selectedSoup);
            listOfallDishesInCombo.addAll(selectedDessert);
            for (String dishName : listOfallDishesInCombo) {
                System.err.println("dishName: " + dishName);
            }
            System.err.println("Restaurant managedBean: create Combo: r name" + this.getFbRName() + "combo name" + this.getSelectedCombos().getName());

           rsbr.editCombo(this.getSelectedCombos().getName(), listOfallDishesInCombo);
           
            this.comboList = (List<FbCombo>) rsbr.getComboList(this.getFbRName());
            this.combo = new FbCombo();
            this.reinitListOfDishAndPackage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A combo Record has been edited successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while editing the combo record: " + ex.getMessage(), ""));
        }
    }
    
    public void deleteCombo(ActionEvent event){
         try {
            System.err.println("Restaurant managedBean: delete Combo"+this.getFbRName()+this.selectedCombos.getName());
          rsbr.deleteCombo(this.getFbRName(), this.selectedCombos.getName());
                  
       //     this.comboList = (List<FbCombo>) rsbr.getComboList(this.getFbRName());
    //        this.selectedCombos = new FbCombo();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A combo Record has been deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the combo record: " + ex.getMessage(), ""));
        }
    }
    
     public void createTable(ActionEvent event) {
        try {
            System.err.println("Restaurant managedBean: create Table");
          
            rsbr.createFbTable(fbRName, this.getNumOfTables(), this.getCapacity(),this.getPeriod());
            this.tableList = (List<FbTable>) rsbr.getTableFromOneRestaurant(fbRName);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A dish Record has been added successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while adding the dish record: " + ex.getMessage(), ""));
        }
    }
     
    public void deleteTable(ActionEvent event){
         try {
            System.err.println("Restaurant  managedBean: delete table");
            rsbr.deleteFbTable(fbRName, selectedTable.getTableNo());
            
            this.tableList = (List<FbTable>) rsbr.getTableFromOneRestaurant(fbRName);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A tableRecord has been deleted successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the table record: " + ex.getMessage(), ""));
        }
    }

    public void reinitListOfDishAndPackage() {

        selectedAppetizer = new ArrayList<String>();

        selectedMainDish = new ArrayList<String>();

        selectedSideDish = new ArrayList<String>();

        selectedVeggie = new ArrayList<String>();

        selectedSoup = new ArrayList<String>();

        selectedDessert = new ArrayList<String>();

    }

    public List<FbRestaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<FbRestaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public FbRestaurant getRestaurant() {
        return restaurant;
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
