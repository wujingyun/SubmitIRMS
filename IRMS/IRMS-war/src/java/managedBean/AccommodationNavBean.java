/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Yang Zhennan
 */
@ManagedBean
@ViewScoped
public class AccommodationNavBean {
    
    private String pageName="listHotels.xhtml";

    /**
     * Creates a new instance of AccommodationNavBean
     */
    public AccommodationNavBean() {
    }
    public void doNav() {
        String str = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("link");
        //System.out.println(str);
        this.pageName = str;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
