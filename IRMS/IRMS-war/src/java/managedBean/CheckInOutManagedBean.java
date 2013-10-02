/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.HotelCheckInOutBeanRemote;
import ejb.HotelRoomBeanRemote;
import entity.AccommodationBill;
import entity.DiscountScheme;
import entity.MiniBarItem;
import entity.Room;
import entity.RoomServiceOrder;
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
import structure.IncidentalCharge;
import structure.MiniBarConsumption;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@ViewScoped
public class CheckInOutManagedBean implements Serializable {

    @EJB
    private HotelCheckInOutBeanRemote checkBean;
    @EJB
    private HotelRoomBeanRemote hotelBean;
    private double overseasCallCharge;
    private List<IncidentalCharge> charges;
    private IncidentalCharge newCharge = new IncidentalCharge();
    private List<String> itemNames;
    private List<MiniBarItem> items;
    private MiniBarItem newItem = new MiniBarItem();
    private String hotelName = "Holiday Inn";
    private AccommodationBill newBill = new AccommodationBill();
    private AccommodationBill selectedBill;
    private List<AccommodationBill> bills;
    private double accommodationTotal;
    private double roomServicesTotal;
    private double minibarTotal;
    private double overseasCallTotal;
    private double incidentalTotal;
    private double totalTotal;
    private String selectedScheme;
    private List<String> schemes;
    private double discountRate;

    /**
     * Creates a new instance of CheckInOutManagedBean
     */
    public CheckInOutManagedBean() {
    }

    @PostConstruct
    public void init() throws ExistException {
        this.bills=checkBean.getAccommodationBills(hotelName);
        this.items = new ArrayList<MiniBarItem>();
        this.charges = new ArrayList<IncidentalCharge>();
        this.setSchemes(checkBean.getDiscountSchemes(hotelName));
        this.setItemNames(hotelBean.getMiniBarItemsNames(hotelName));
        this.newItem.setName("Coke");
    }

    public String reinit() {
        this.newBill = new AccommodationBill();
        return null;
    }

    public String reinitItem() {
        this.newItem = new MiniBarItem();
        return null;
    }

    public String reinitCharge() {
        this.newCharge = new IncidentalCharge();
        return null;
    }

    public void addItem(ActionEvent event) throws ExistException {
        //System.out.println("newItem Name:"+newItem.getName());
        //System.out.println("hotelName:"+hotelName);
        newItem.setPrice(hotelBean.findMiniBarItemPrice(hotelName, newItem.getName()));
        checkBean.addMiniBarItemCharge(selectedBill.getId(), newItem.getName(), newItem.getQuantity());
        items.add(newItem);
    }

    public void addOverseasCallCharge(ActionEvent event) throws ExistException {
        //System.out.println("BIll ID: "+selectedBill.getId());
        //System.out.println("BIll call charge: "+selectedBill.getOverseasCallCharge());
        double temp = this.getOverseasCallCharge();
        //System.out.println("temp="+temp);
        checkBean.addCallCharge(selectedBill.getId(), temp);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Overseas call Charge Added!",""));
        //System.out.println("OverseasCallCharge:"+selectedBill.getOverseasCallCharge());
    }

    public void addCharge(ActionEvent event) throws ExistException {
        //System.out.println("newItem Name:"+newCharge.getChargeName());
        //System.out.println("hotelName:"+hotelName);
        checkBean.addIncidentalCharge(selectedBill.getId(), newCharge.getChargeName(), newCharge.getPrice(), newCharge.getDescription());
        charges.add(newCharge);
    }

    public void createBill(ActionEvent event) throws ExistException {
        //if(newService.getName()==null) System.out.println("service name is null");
        //System.out.println(newService.getName()+" "+newService.getDescription()+ " "+ newService.getPrice());
        newBill = checkBean.createAccommodationBill(newBill.getRoomReservationId(), hotelName);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Accommodation Bill has been created!",""));
        bills.add(newBill);
    }
    
    public void onEdit(RowEditEvent event) throws ExistException {
        if(selectedBill==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a bill", ""));
            }
        //System.out.println(hotelName + " + " + selectedRoom.getRoomNumber());
        this.checkBean.updatePaymentStatus(selectedBill.getId(),selectedBill.getPaymentStatus());
        if(selectedBill.getPaymentStatus().equals("Full")){
            this.checkBean.updateRoomAvailabilityStatus(selectedBill.getId());
        }
        
        FacesMessage msg = new FacesMessage("Hotel: " + hotelName+" Accommodation Bill:"+((AccommodationBill) event.getObject()).getId()+" Edited", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled editing bill: " +((AccommodationBill) event.getObject()).getId()+" ", "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void caculateTotal(ActionEvent event) throws ExistException{
        //System.out.println("selected bill id: "+selectedBill.getId());
        this.totalTotal=checkBean.tallyBill(selectedBill.getId());
        this.totalTotal=this.totalTotal*(1-this.discountRate);
        //System.out.println("Total: "+total);
        this.setAccommodationTotal(selectedBill.getRoomReservation().getTotal());
        this.setOverseasCallTotal(selectedBill.getOverseasCallCharge());
        this.setIncidentalTotal(checkBean.getIncidentalTotal(selectedBill.getId()));
        this.setMinibarTotal(checkBean.getMiniBarTotal(selectedBill.getId()));
        this.setRoomServicesTotal(checkBean.getRoomServiceTotal(selectedBill.getId()));
        this.setTotalTotal(totalTotal);
        
    }
    
    public void checkOut() throws ExistException{
        
        if(selectedBill==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select a bill", ""));
            }
        //System.out.println(hotelName + " + " + selectedRoom.getRoomNumber());
        this.checkBean.updatePaymentStatus(selectedBill.getId(),"Full");
        System.out.println("payment status:" + selectedBill.getPaymentStatus());
        this.checkBean.updateRoomAvailabilityStatus(selectedBill.getId());
        System.out.println("availability status:" + selectedBill.getRoomReservation().getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Check Out Completed!", ""));
    }
    
    public void applyDiscount() throws ExistException{
        if(selectedScheme.equals("test"))
            this.discountRate=0.1;
        if(selectedScheme.equals("GSS"))
            this.discountRate=0.2;
        
        this.totalTotal*=(1-this.discountRate);
    }

    public void handleHotelChange() throws ExistException {
        if (hotelName != null && !hotelName.equals("")) {
            bills = checkBean.getAccommodationBills(hotelName);
        } else {
            System.out.println("empty hotel name");
        }
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public AccommodationBill getNewBill() {
        return newBill;
    }

    public void setNewBill(AccommodationBill newBill) {
        this.newBill = newBill;
    }

    public AccommodationBill getSelectedBill() {
        return selectedBill;
    }

    public void setSelectedBill(AccommodationBill selectedBill) {
        this.selectedBill = selectedBill;
    }

    public List<AccommodationBill> getBills() {
        return bills;
    }

    public void setBills(List<AccommodationBill> bills) {
        this.bills = bills;
    }

    public HotelCheckInOutBeanRemote getCheckBean() {
        return checkBean;
    }

    public void setCheckBean(HotelCheckInOutBeanRemote checkBean) {
        this.checkBean = checkBean;
    }

    public List<MiniBarItem> getItems() {
        return items;
    }

    public void setItems(List<MiniBarItem> items) {
        this.items = items;
    }

    public MiniBarItem getNewItem() {
        return newItem;
    }

    public void setNewItem(MiniBarItem newItem) {
        this.newItem = newItem;
    }

    public List<String> getItemNames() {
        return itemNames;
    }

    public void setItemNames(List<String> itemNames) {
        this.itemNames = itemNames;
    }

    public double getOverseasCallCharge() {
        return overseasCallCharge;
    }

    public void setOverseasCallCharge(double overseasCallCharge) {
        this.overseasCallCharge = overseasCallCharge;
    }

    public IncidentalCharge getNewCharge() {
        return newCharge;
    }

    public void setNewCharge(IncidentalCharge newCharge) {
        this.newCharge = newCharge;
    }

    public List<IncidentalCharge> getCharges() {
        return charges;
    }

    public void setCharges(List<IncidentalCharge> charges) {
        this.charges = charges;
    }

    public double getAccommodationTotal() {
        return accommodationTotal;
    }

    public void setAccommodationTotal(double accommodationTotal) {
        this.accommodationTotal = accommodationTotal;
    }

    public double getRoomServicesTotal() {
        return roomServicesTotal;
    }

    public void setRoomServicesTotal(double roomServicesTotal) {
        this.roomServicesTotal = roomServicesTotal;
    }

    public double getMinibarTotal() {
        return minibarTotal;
    }

    public void setMinibarTotal(double minibarTotal) {
        this.minibarTotal = minibarTotal;
    }

    public double getOverseasCallTotal() {
        return overseasCallTotal;
    }

    public void setOverseasCallTotal(double overseasCallTotal) {
        this.overseasCallTotal = overseasCallTotal;
    }

    public double getIncidentalTotal() {
        return incidentalTotal;
    }

    public void setIncidentalTotal(double incidentalTotal) {
        this.incidentalTotal = incidentalTotal;
    }

    public double getTotalTotal() {
        return totalTotal;
    }

    public void setTotalTotal(double totalTotal) {
        this.totalTotal = totalTotal;
    }

    public List<String> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<String> schemes) {
        this.schemes = schemes;
    }

    public String getSelectedScheme() {
        return selectedScheme;
    }

    public void setSelectedScheme(String selectedScheme) {
        this.selectedScheme = selectedScheme;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    
}
