/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.ManageMallSpaceBeanRemote;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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
   
    private int unitSpace;
    
    public MallSpaceManagedBean() {
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
            System.out.println("ManagedBean add new unit: "+unitNo+" unitSpace:"+unitSpace+" MallName:"+mallName);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  
                 "New Unit added successfully", ""));
        }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  
                    "An error has occurred while creating the new Unit: " + ex.getMessage(), ""));
        }
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

 
}
