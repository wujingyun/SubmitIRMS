/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.ManageMallSpaceBeanRemote;
import entity.Unit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
public class MallSpaceManagedBean implements Serializable{

    @EJB
    ManageMallSpaceBeanRemote mmsbr;
    
    private static String mallName="IRMall";
    private int mallSize;
    private String newMall;
    
    private String unitNo;
    private List<String> units = new ArrayList();
    private List<String> selectedUnits;
    private int unitSpace;
    private static String mName="IRMall";
     private List<Unit> unitList;
      private DataTable dataTable;
    public MallSpaceManagedBean() {
    }
    
     @PostConstruct
    public void init() {
      
        this.unitList=mmsbr.getUnitList();
     //    commisionRate =mtb.sendCommisionRate();
    }
    
    public  void createMall(ActionEvent event){
      try{
     
        mmsbr.createMall(newMall);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  
                 "New Mall created successfully", ""));
      }catch(Exception ex){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  
                    "An error has occurred while creating the new Mall: " + ex.getMessage(), ""));
        }
    
    }
    
    
    public  void createUnit(ActionEvent event){
        try{
           
            mmsbr.addNewUnit(unitNo, unitSpace, mallName);
              this.unitList=mmsbr.getUnitList();
            System.out.println("ManagedBean add new unit: "+unitNo+" unitSpace:"+unitSpace+" MallName:"+mallName);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  
                 "New Unit added successfully", ""));
        }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  
                    "An error has occurred while creating the new Unit: Same Unit Number identified! or Unit Space is too small" + "" + ex.getMessage(), ""));
        }
    }
    
    public void deleteUnit(ActionEvent event){//not finished
        try{       
            mmsbr.deleteUnit(getSelectedUnits());
            this.unitList=mmsbr.getUnitList();
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  
                 "Unit deleted successfully", ""));
        }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  
                    "An error has occurred while deleting the new Unit: " + ex.getMessage(), ""));
        }
    }
    
    public List<String> getUnits(){
            
        
         units = mmsbr.DisplayRepartitionMall(mName);
         return units;
     } 
    
    public List<String> getSelectedUnits() {
        return selectedUnits;
    }

    public void setSelectedUnits(List<String> selectedUnits) {
        this.selectedUnits = selectedUnits;
    }
 
    public String getNewMall() {
        return newMall;
    }

    public void setNewMall(String newMall) {
        this.newMall = newMall;
    }
    


    public int getMallSize() {
        return mallSize;
    }

    public void setMallSize(int mallSize) {
        this.mallSize = mallSize;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public int getUnitSpace() {
        return unitSpace;
    }

    public void setUnitSpace(int unitSpace) {
        this.unitSpace = unitSpace;
    }

    public List<Unit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<Unit> unitList) {
        this.unitList = unitList;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }
    
 
}
