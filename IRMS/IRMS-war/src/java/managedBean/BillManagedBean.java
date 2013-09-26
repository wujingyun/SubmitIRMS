/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.ManageTenantBeanRemote;
import entity.Shop;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author wangxiahao
 */
@ManagedBean
@RequestScoped
public class BillManagedBean implements Serializable {

    @EJB
    ManageTenantBeanRemote mtb;
    /**
     * Creates a new instance of BillManagedBean
     */
    List<Shop> shopList;
    private DataTable dataTable;
    private Shop shopEntity;
    private double rentRate;
    

    @PostConstruct
    public void init() {
        this.shopList = mtb.getShopList();
    }

    public BillManagedBean() {
    }

    public void editShopEntity(ActionEvent event) {
        shopEntity = (Shop) dataTable.getRowData();
        System.out.println("BIll managed bean! edit shop entity " + shopEntity.getOwner());
        this.setShopEntity(shopEntity);
        System.out.println("BIll managed bean! edit shop entity " + getShopEntity().getOwner());
        System.out.println("BIll managed bean! edit shop entity " + getShopEntity().getContract().getRentRate());


        rentRate = Double.parseDouble(getShopEntity().getContract().getRentRate());
        this.setRentRate(rentRate);
        System.out.println("bill in String  " + rentRate + " in double" + this.getRentRate());
        mtb.creatBill(rentRate, 0, getShopEntity().getShopID());
    }

    public void createBill( ) {
        try {
            System.err.println(rentRate + " " + getShopEntity().getShopID());
            
            mtb.creatBill(rentRate, 0, this.getShopEntity().getShopID());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New bill created successfully", ""));
        } catch (Exception ex) {
           
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while creating the new bill: " + ex.getMessage(), ""));
        }
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public Shop getShopEntity() {
        return shopEntity;
    }

    public void setShopEntity(Shop shopEntity) {
        this.shopEntity = shopEntity;
    }

    public double getRentRate() {
        return rentRate;
    }

    public void setRentRate(double rentRate) {
        this.rentRate = rentRate;
    }
    
}
