/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

/**
 *
 * @author nn
 */
@ManagedBean
@RequestScoped
public class TemplateManagedBean {

    /**
     * Creates a new instance of TemplateManagedBean
     */
    public TemplateManagedBean() {
    }
    
    public void beforePhaseListener(PhaseEvent event){
    FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
}
