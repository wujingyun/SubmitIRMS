/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.ManageTenantBeanRemote;
import entity.Shop;
import entity.ShopBill;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.chart.PieChartModel;


/**
 *
 * @author wangxiahao
 */
@ManagedBean
@ViewScoped
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
    private static Collection<ShopBill> bills;
    private ShopBill billEntity;
    private String description;
    private String operatinghours;
    private String storeContact;
   // private HashMap<String, Integer> cache;
  //  private PieChartModel model;

    @PostConstruct
    public void init() {
        this.shopList = mtb.getShopList();
    //    this.cache = mtb.viewTenancyMix();
       
    }

    public BillManagedBean() {
     
    }

    public void createBill(ActionEvent event) {

        try {
            mtb.creatBill(rentRate, 0, getShopEntity().getShopID());
            this.setBills(bills);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New bill created successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while creating the new bill: " + ex.getMessage(), ""));
        }

    }

    public void editBill(ActionEvent event) {
        billEntity = (ShopBill) dataTable.getRowData();
        System.out.println("BIll managed bean! edit bill entity " + billEntity.getBillID());
        this.setBillEntity(billEntity);


        System.err.println("billEntity status" + billEntity.isBillStatus());
        mtb.EditBillStatus(billEntity.getBillID());
    }

    public void changeStatus(ActionEvent event) {
        try {
            System.err.println("change status");
            mtb.EditBillStatus(getBillEntity().getBillID());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "The bill has been paid successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "An error has occurred while processing the new bill: ", ""));
        }
    }

    public void editShopEntityBeta(ActionEvent event) {
        shopEntity = (Shop) dataTable.getRowData();
        System.out.println("BIll managed bean! edit shop entity " + shopEntity.getOwner());
        this.setShopEntity(shopEntity);
        System.out.println("BIll managed bean! edit shop entity " + getShopEntity().getOwner());
        System.out.println("BIll managed bean! edit shop entity " + getShopEntity().getContract().getRentRate());


        rentRate = Double.parseDouble(getShopEntity().getContract().getRentRate());
        this.setRentRate(rentRate);
        System.out.println("bill in String  " + rentRate + " in double" + this.getRentRate());

        bills = new ArrayList<ShopBill>();
        bills = mtb.sendBills(getShopEntity().getShopID());
        bills.size();
        System.err.println("List of bills " + bills.size());
        for (Iterator it = bills.iterator(); it.hasNext();) {
            billEntity = (ShopBill) it.next();
            System.out.println("bill id" + billEntity.getBillID());
        }

        this.setBills(bills);


        String url = "smpViewBills.xhtml";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect(url);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while redirecting page: " + ex.getMessage(), ""));
        }
    }

    public void editShopEntityAlpha(ActionEvent event) {
        shopEntity = (Shop) dataTable.getRowData();
        System.out.println("BIll managed bean! edit shop entity a " + shopEntity.getOwner());
        this.setShopEntity(shopEntity);
        rentRate = Double.parseDouble(getShopEntity().getContract().getRentRate());
        this.setRentRate(rentRate);
        System.out.println("BIll managed bean! edit shop entity a" + getShopEntity().getOwner());
        System.out.println("BIll managed bean! edit shop entity a" + getShopEntity().getContract().getRentRate());

    }

    public void saveShopInfo(ActionEvent event) {
        try {
            mtb.EditShopInfo(getShopEntity().getShopID(), description, operatinghours, storeContact);

            //save shop
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New information saved successfully", ""));
        } catch (Exception ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while saving the new information: " + ex.getMessage(), ""));
        }
    }
    
   /* public void createModel() {  
        
        this.cache = mtb.viewTenancyMix();
        model = new PieChartModel();  
        
        for (Iterator<Map.Entry<String, Integer>> it = getCache().entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = it.next();
            System.out.println("In managed bean key,val: " + entry.getKey() + "," + entry.getValue());
            Integer i = (Integer)entry.getValue();
            model.set((String)entry.getKey(), i.intValue());
        }
       
    }  */

    public void viewTenancyMix(ActionEvent event) {
        
        /*try{
            
         mtb.viewTenancyMix();
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
         "View Tenancy Mix successfully", ""));
         }catch (Exception ex) {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
         "An error has occurred while viewing the tenancy result: " + ex.getMessage(), ""));
         }*/
     /*    for (Iterator<Map.Entry<String, Integer>> it = getCache().entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = it.next();
            System.out.println("In managed bean key,val: " + entry.getKey() + "," + entry.getValue());       
        }
        this.createModel();*/
      
        
            String url = "smpTenancyMix.xhtml";
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            
            try {
                ec.redirect(url);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "An error has occurred while redirecting page: " + ex.getMessage(), ""));
            }    
            
            
    }

    public void createBill() {
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

    public Collection<ShopBill> getBills() {
        return bills;
    }

    public void setBills(Collection<ShopBill> bills) {
        this.bills = bills;
    }

    public ShopBill getBillEntity() {
        return billEntity;
    }

    public void setBillEntity(ShopBill billEntity) {
        this.billEntity = billEntity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperatinghours() {
        return operatinghours;
    }

    public void setOperatinghours(String operatinghours) {
        this.operatinghours = operatinghours;
    }

    public String getStoreContact() {
        return storeContact;
    }

    public void setStoreContact(String storeContact) {
        this.storeContact = storeContact;
    }

  /*  public HashMap<String, Integer> getCache() {
        return cache;
    }

    public void setCache(HashMap<String, Integer> cache) {
        this.cache = cache;
    }

    public PieChartModel getModel() {
        return model;
    }

    public void setModel(PieChartModel model) {
        this.model = model;
    }*/
}
