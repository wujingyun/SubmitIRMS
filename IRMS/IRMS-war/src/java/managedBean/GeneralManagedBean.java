/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author wangxiahao
 */
@ManagedBean(name = "generalManagedBean")
@RequestScoped
public class GeneralManagedBean implements Serializable{

    /**
     * Creates a new instance of GeneralManagedBean
     */
    public GeneralManagedBean() {
    }
    
    public String goToAbout(){
        return "About?faces-redirect=true";
    }
    
    public  String getTest(){
        return "test";
    }
}
