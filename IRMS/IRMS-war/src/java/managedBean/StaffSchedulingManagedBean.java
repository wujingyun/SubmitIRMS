/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;


import ejb.StaffSchedulingRemote;
import entity.Hotel;
import entity.Staff;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.component.datatable.DataTable;


/**
 *
 * @author wangxiahao
 */
@ManagedBean
@ViewScoped
public class StaffSchedulingManagedBean implements Serializable {

     @EJB
     StaffSchedulingRemote ssr;
    
     private static List<Staff> staffList;
     private String staffRole;
     private String shift;
     private String floorLevel;
     private String day;
     private Map<String,String> roles = new HashMap<String, String>();  
     private Map<String,Map<String,String>> roles_Shifts= new HashMap<String, Map<String,String>>(); 
     private Map<String,String> shifts = new HashMap<String, String>();  
     private DataTable dataTable;
     private Staff staff;
     private List<Hotel> hotel;
     private Hotel hotelEntity;
     private String name;
    private List<String> hotelNames;
    private String hotelNameOnly;
    
    @PostConstruct
    public void init() {
        //this.staffList = ssr.getStaffList();    
        this.hotel =ssr.getHotel();
        hotelNames=new ArrayList<String>();
        for(int i=0; i<hotel.size(); i++){
            hotelNames.add(hotel.get(i).getName());
        }
       
    }

    public StaffSchedulingManagedBean() {
        roles.put("Receptionist", "Receptionist");
        roles.put("Housekeeping", "Housekeeping");
        
        Map<String,String> recepshift = new HashMap<String, String>(); 
        recepshift.put("Morning Shift", "Morning Shift");
        recepshift.put("Afternoon Shift","Afternoon Shift");
        recepshift.put("Night Shift","Night Shift");
        
        Map<String,String> houseshift = new HashMap<String, String>(); 
        houseshift.put("Ad-hoc", "Ad-hoc");
        houseshift.put("Morning Shift", "Morning Shift");
        houseshift.put("Afternoon Shift", "Afternoon Shift");
        
        roles_Shifts.put("Receptionist",recepshift);
        roles_Shifts.put("Housekeeping",houseshift);
    
    }
     public void handleRoleChange(String staffRole) { 
         System.err.println("staff.staffRole: " + staffRole);
        if(staffRole !=null && !staffRole.equals("")){  
            shifts = roles_Shifts.get(staffRole);
            this.setShift(shift);
        }
        else  
            shifts = new HashMap<String, String>();  
    }  
     
    public void saveScheduleInfo(ActionEvent event){
          try {         
              System.err.println("save the schedule order!"+staffRole);
              staff = (Staff) dataTable.getRowData();
              System.err.println("save the Staff ID!");
              System.err.println(" id"+staff.getId()+" "+staffRole+" "+shift);
              ssr.setShifts(this.getStaff().getId(),this.getStaff().getStaffRole(), this.getStaff().getShift(), this.getStaff().getFloorLevel(), this.getStaff().getWeek_day());
          //   String hName = staff.getHotel().getName();
            // System.err.println(hName);
             
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New schedule saved successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while saving the new schedule: " + ex.getMessage(), ""));
        }
    }
    
    public void editHotel(ActionEvent event){
          hotelEntity = (Hotel) dataTable.getRowData();
          System.err.println(hotelEntity.getName()+".........");
          this.setHotelEntity(hotelEntity);
          staffList =new ArrayList();
          staffList = ssr.getStaff(hotelEntity.getName());
         String url = "acmStaffScheduling.xhtml";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect(url);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New Hotel edited successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while redirecting page: " + ex.getMessage(), ""));
        }
    }
    public void editHotelBeta(ActionEvent event){
          hotelEntity = (Hotel) dataTable.getRowData();
          System.err.println(hotelEntity.getName()+".........");
          this.setHotelEntity(hotelEntity);
          staffList =new ArrayList();
          staffList = ssr.getStaff(hotelEntity.getName());
         
          String url = "acmViewStaffScheduling.xhtml";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect(url);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New Hotel edited successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while redirecting page: " + ex.getMessage(), ""));
        }
    }
    
    public void createStaff(ActionEvent event){
        System.err.println(name+"..."+hotelNameOnly);
        try {
        ssr.addStaff(name,hotelNameOnly);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New Staff added successfully", ""));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while adding New Staff: " + ex.getMessage(), ""));
        }
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(String floorLevel) {
        this.floorLevel = floorLevel;
    }

    public String getDay() {
        return day;       
    } 
    public void setDay(String day) {
        this.day = day;
    }


    public Map<String, String> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, String> roles) {
        this.roles = roles;
    }

    public Map<String, Map<String, String>> getRoles_Shifts() {
        return roles_Shifts;
    }

    public void setRoles_Shifts(Map<String, Map<String, String>> roles_Shifts) {
        this.roles_Shifts = roles_Shifts;
    }

    public Map<String, String> getShifts() {
        return shifts;
    }

    public void setShifts(Map<String, String> shifts) {
        this.shifts = shifts;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<Hotel> getHotel() {
        return hotel;
    }

    public void setHotel(List<Hotel> hotel) {
        this.hotel = hotel;
    }

    public Hotel getHotelEntity() {
        return hotelEntity;
    }

    public void setHotelEntity(Hotel hotelEntity) {
        this.hotelEntity = hotelEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHotelNames() {
        return hotelNames;
    }

    public void setHotelNames(List<String> hotelNames) {
        this.hotelNames = hotelNames;
    }

    public String getHotelNameOnly() {
        return hotelNameOnly;
    }

    public void setHotelNameOnly(String hotelNameOnly) {
        this.hotelNameOnly = hotelNameOnly;
    }
   
}
