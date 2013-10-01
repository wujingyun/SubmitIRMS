/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;
import ejb.UserLogBeanRemote;//added for logging 
import ejb.AdminUserBeanRemote;//added for logging
import ejb.ContractBeanRemote;
import ejb.ManageCatalogBeanRemote;
import ejb.ManageMallSpaceBeanRemote;
import entity.ConciergeOrder;
import entity.Contract;
import entity.Mall;
import entity.Shop;
import entity.ShopOwner;
import entity.TenantRecordEntity;
import entity.Unit;
import entity.UserAccount;//added for logging
import entity.UserLog;//added for logging
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author wangxiahao
 */
@ManagedBean
@ViewScoped
public class ShoppingMallManagedBean implements Serializable {

    @EJB
    ContractBeanRemote cbr;
    @EJB
    ManageMallSpaceBeanRemote mmsbr;
    @EJB
    ManageCatalogBeanRemote mcbr;
    @EJB//added for logging 
    AdminUserBeanRemote aub;//added for logging 
    @EJB//added for logging 
    UserLogBeanRemote ulb;//added for logging 
    private UserLog ul;//added for logging 
    private UserAccount ua;//added for logging 
    private String ContractType;
    private String Landlord;
    private static String Tenant;
    private static String IdentityCard;
    private String TenantTradeName;
    private String NameOfShoppingCenter;
    private int FloorArea;
    private String Purpose;                  // category of the shop
    private String MinimumRent;
    private String RentRate;
    private static String TenantAddress;
    private String LandlordContact;
    private static String TenantContact;
    private String upfrontRentalDeposit;
    private static Date date;
    private Shop shop;
    private ShopOwner tenant;
    private Mall mall;
    private TenantRecordEntity tenantRecord;
    //  private Unit units;
    private List<String> units = new ArrayList();
    private List<String> selectedUnits;
    private Unit mallUnit;
    private static String mName = "IRMall";
    private List<TenantRecordEntity> tenantList;
    private List<String> listOfTenant;
    private Long selectedRecord;
    private static Date d;
    //  private Map<String,String> mallUnits;
    private List<Contract> contractList;
    private static Contract contractOne;
    private DataTable dataTable;
    private Contract contractRecord;
    private String yearsToRenew;
    private static TenantRecordEntity theOnlyTenant;
    private String hotelName;
    private String customerName;
    private String customerID;
    private String customerContact;
    private Integer numOfItems;
    private String OrderDescription;
    private List<ConciergeOrder> deliveryOrder;
    private ConciergeOrder co;
    private String orderStatus;
    
    public ShoppingMallManagedBean() {
    }

    @PostConstruct
    public void init() {
        this.tenantList = cbr.getExistingTenant();
        this.contractList = cbr.getContractList();
        this.deliveryOrder = mcbr.getListOfDeliveryOrders();
    }
    public void editTable (ActionEvent evnt){
       co = (ConciergeOrder)dataTable.getRowData();
       System.err.println("Concierge order retrieved"+co.getCustomerID());
       this.setCo(co);
        
    }
    public void createDeliveryOrder(ActionEvent event) {
        try {
            System.err.println("create the delivery order!");
            mcbr.deliveryItem(hotelName, customerName, customerID, customerContact, numOfItems, OrderDescription);
             this.deliveryOrder = mcbr.getListOfDeliveryOrders();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New delivery order created successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while creating the new delivery order: " + ex.getMessage(), ""));
        }
    }
    
    public void editOrder(ActionEvent evnt){
        try {
            System.err.println("edit the delivery order!"+this.getCo().getId());
            mcbr.editTheOrder(customerName,customerID, customerContact, numOfItems, getCo().getId(),hotelName);
             this.deliveryOrder = mcbr.getListOfDeliveryOrders();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New delivery order created successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while creating the new delivery order: " + ex.getMessage(), ""));
        }
    }
    
    public void updateStatusOrder(ActionEvent event){
            try {
            System.err.println("update the delivery order!"+this.getCo().getId());
            mcbr.updateDeliveryOrder(orderStatus, this.getCo().getId());
             this.deliveryOrder = mcbr.getListOfDeliveryOrders();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Status has been changed successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while changing the new status: " + ex.getMessage(), ""));
        }
        
    }
    
    public void deleteDeliveryOrder(ActionEvent event){
          try {
            System.err.println("delete the delivery order!"+this.getCo().getId());
            mcbr.deleteDeliveryOrder(this.getCo().getId(), this.getCo().getHotelName());
             this.deliveryOrder = mcbr.getListOfDeliveryOrders();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Status has been changed successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while deleting the new delivery order: " + ex.getMessage(), ""));
        }
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        ShoppingMallManagedBean.date = date;
    }

    public void handleDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void save() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Tenant = request.getParameter("form1:tenant");

            IdentityCard = request.getParameter("form1:identityCard");
            TenantAddress = request.getParameter("form1:tenantAddress");
            TenantContact = request.getParameter("form1:tenantContact");

            System.out.println(Tenant);
            System.out.println(IdentityCard);
            System.out.println(TenantAddress);
            System.out.println(TenantContact);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New Tenant Information has been saved", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while creating the new contract: " + ex.getMessage(), ""));
        }
    }

    public void setAttributes(ActionEvent event) {


        System.out.println("found " + selectedRecord);
        tenantRecord = new TenantRecordEntity();
        try {
            for (Iterator it = tenantList.iterator(); it.hasNext();) {
                tenantRecord = (TenantRecordEntity) it.next();
                if (tenantRecord.getRecordID().equals(selectedRecord)) {
                    theOnlyTenant = tenantRecord;
                    System.out.println("Tenant " + tenantRecord.getTenant() + " Found!");
                    Tenant = theOnlyTenant.getTenant();
                    IdentityCard = theOnlyTenant.getRecordID().toString();
                    TenantAddress = theOnlyTenant.getTenantAddress();
                    TenantContact = theOnlyTenant.getTenantContact();
                    System.err.println(IdentityCard);
                }

            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Existing Tenant Information retrieved successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Tenant not found in the database", "!"));
        }


    }

    public void tenantSelected(ValueChangeEvent event) {
        System.err.println("tenantSelected");
        System.err.println(event.getOldValue());
        System.err.println(event.getNewValue());
    }

    public List<Contract> getContractList() {

        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    public void contractCreation(ActionEvent event) {

        try {
            System.err.println("create the contract!");
       HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       long userId = (Long)request.getSession().getAttribute("userId");
     
       ulb.addLog(userId, aub.getUserById(userId).getUserName(), "Create Contract");
       System.out.println("add user log====================="+userId+aub.getUserById(userId).getUserName());
            cbr.signContract(ContractType, Landlord, Tenant, IdentityCard,
                    TenantTradeName, getSelectedUnits(), NameOfShoppingCenter,
                    Purpose, MinimumRent, RentRate, TenantAddress, LandlordContact,
                    TenantContact, upfrontRentalDeposit, getDate(), yearsToRenew);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New Contract created successfully", "Your action is recorded in the system"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while creating the new contract: " + ex.getMessage(), ""));
        }

    }

    public void renewContract(ActionEvent event) {
        try {

            cbr.reNewContract(MinimumRent, RentRate,
                    upfrontRentalDeposit, getContractRecord(), yearsToRenew);
            this.contractList = cbr.getContractList();
             HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       long userId = (Long)request.getSession().getAttribute("userId");
       
       ulb.addLog(userId, aub.getUserById(userId).getUserName(), "Renew Contract");
       System.out.println("add user log=====================");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Contract has been renewed successfully", "Your action is recorded in the system"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while renewing the new contract: " + ex.getMessage(), ""));
        }
    }

    public void terminateContract(ActionEvent event) {
        try {

            cbr.terminateContract(getContractRecord().getId());
            this.contractList = cbr.getContractList();
              HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       long userId = (Long)request.getSession().getAttribute("userId");
       
       ulb.addLog(userId, aub.getUserById(userId).getUserName(), "Terminate Contract");
       System.out.println("add user log=====================");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Contract has been terminated successfully", "Your action is recorded in the system"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while terminating the new contract: " + ex.getMessage(), ""));
        }
    }

    public void editContractRecord(ActionEvent event) {

        contractRecord = (Contract) dataTable.getRowData();
        contractRecord.getUnits().size();

        this.setContractRecord(contractRecord);
        System.out.println("Here :" + contractRecord.getPurpose());
        System.out.println("Here " + contractRecord.getNameOfShoppingCenter());
        System.out.println("Here " + contractRecord.getMinimumRent());
        //return contractRecord;
    }

    public List<TenantRecordEntity> getTenantList() {
        System.out.println("Getting records for tenant ");
        return tenantList;
    }

    public void setTenantList(List<TenantRecordEntity> tenantList) {
        this.tenantList = tenantList;
    }

    public List<String> getUnits() {

        units = mmsbr.DisplayRepartitionMall(mName);

        return units;
    }

    public String getContractType() {
        return ContractType;
    }

    public void setContractType(String ContractType) {
        this.ContractType = ContractType;
    }

    public String getLandlord() {
        return Landlord;
    }

    public void setLandlord(String Landlord) {
        this.Landlord = Landlord;
    }

    public String getTenant() {
        return Tenant;
    }

    /*   public void setTenant(String Tenant) {
     this.Tenant = Tenant;
     }*/
    public String getNameOfShoppingCenter() {
        return NameOfShoppingCenter;
    }

    public void setNameOfShoppingCenter(String NameOfShoppingCenter) {
        this.NameOfShoppingCenter = NameOfShoppingCenter;
    }

    public int getFloorArea() {
        return FloorArea;
    }

    public void setFloorArea(int FloorArea) {
        this.FloorArea = FloorArea;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String Purpose) {
        this.Purpose = Purpose;
    }

    public String getMinimumRent() {
        return MinimumRent;
    }

    public void setMinimumRent(String MinimumRent) {
        this.MinimumRent = MinimumRent;
    }

    public String getRentRate() {
        return RentRate;
    }

    public void setRentRate(String RentRate) {
        this.RentRate = RentRate;
    }

    public String getTenantAddress() {
        return TenantAddress;
    }

    /*   public void setTenantAddress(String TenantAddress) {
     this.TenantAddress = TenantAddress;
     }*/
    public String getLandlordContact() {
        return LandlordContact;
    }

    public void setLandlordContact(String LandlordContact) {
        this.LandlordContact = LandlordContact;
    }

    public String getTenantContact() {
        return TenantContact;
    }

    /*   public void setTenantContact(String TenantContact) {
     this.TenantContact = TenantContact;
     }*/
    public String getUpfrontRentalDeposit() {
        return upfrontRentalDeposit;
    }

    public void setUpfrontRentalDeposit(String upfrontRentalDeposit) {
        this.upfrontRentalDeposit = upfrontRentalDeposit;
    }

    public String getIdentityCard() {
        return IdentityCard;
    }

    /* public void setIdentityCard(String IdentityCard) {
     this.IdentityCard = IdentityCard;
     }*/
    public String getTenantTradeName() {
        return TenantTradeName;
    }

    public void setTenantTradeName(String TenantTradeName) {
        this.TenantTradeName = TenantTradeName;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public ShopOwner getShopOwner() {
        return tenant;
    }

    public void setShopOwner(ShopOwner tenant) {
        this.tenant = tenant;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    public Mall getMall() {
        return mall;
    }

    public void setMall(Mall mall) {
        this.mall = mall;
    }

    public Unit getMallUnit() {
        return mallUnit;
    }

    public void setMallUnit(Unit mallUnit) {
        this.mallUnit = mallUnit;
    }

    public List<String> getSelectedUnits() {

        return selectedUnits;
    }

    public void setSelectedUnits(List<String> selectedUnits) {
        this.selectedUnits = selectedUnits;
    }

    public Long getSelectedRecord() {
        System.out.println("get record 1");


        return selectedRecord;
    }

    public void setSelectedRecord(Long selectedRecord) {
        System.out.println("set record ");
        this.selectedRecord = selectedRecord;
    }

    public Contract getContractOne() {
        return contractOne;
    }

    public void setContractOne(Contract contractOne) {
        this.contractOne = contractOne;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    /*  public HtmlInputHidden getDataItemId() {
     return dataItemId;
     }*/
    // Setters -----------------------------------------------------------------------------------
    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    /* public void setDataItemId(HtmlInputHidden dataItemId) {
     this.dataItemId = dataItemId;
     }*/
    public Contract getContractRecord() {
        return contractRecord;
    }

    public void setContractRecord(Contract contractRecord) {
        this.contractRecord = contractRecord;
    }

    public String getYearsToRenew() {
        return yearsToRenew;
    }

    public void setYearsToRenew(String yearsToRenew) {
        this.yearsToRenew = yearsToRenew;
    }

    public TenantRecordEntity getTenantRecord() {
        return tenantRecord;
    }

    public void setTenantRecord(TenantRecordEntity tenantRecord) {
        this.tenantRecord = tenantRecord;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public Integer getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(Integer numOfItems) {
        this.numOfItems = numOfItems;
    }

    public String getOrderDescription() {
        return OrderDescription;
    }

    public void setOrderDescription(String OrderDescription) {
        this.OrderDescription = OrderDescription;
    }

    public List<ConciergeOrder> getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(List<ConciergeOrder> deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public ConciergeOrder getCo() {
        return co;
    }

    public void setCo(ConciergeOrder co) {
        this.co = co;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    
}
