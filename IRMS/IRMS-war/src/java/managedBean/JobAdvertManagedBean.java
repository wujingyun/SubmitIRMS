/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean 
@ViewScoped 

public class JobAdvertManagedBean implements Serializable { 
    
    private String description; 
    
    public void initView(PhaseEvent event)
    
    { String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"); 
    
    if(id != null) 
    
    { 
        
        if(id.equals("001")) 
            
            description = "001 - Christmas Party"; 
        
        else if(id.equals("002")) 
            
            description = "002 - New Year Countdown"; 
        
        else if(id.equals("003")) 
            
            description = "003 - Special Event 1"; 
        
        else description = "Invalid Event";
    } 
    
    else { 
        description = "Invalid Request"; } 
    } 
}