/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.WebImgBean;
import entity.webImg;
import exception.ExistException;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class WebManagedBean implements Serializable {
   @EJB
   WebImgBean wi;
   private long firstimage;
   private long secondimage;
   private long thirdimage;
   private long forthimage;
   
    /**
     * Creates a new instance of WebManagedBean
     */
    public WebManagedBean() throws ExistException  {
         Map<String, String> images = new HashMap<String, String>();
         List <webImg> imgList = wi.getAllImage();
         for (Object o : imgList) {
            webImg eachimg = (webImg) o;
           String s = String.valueOf(eachimg.getId());
           images.put(s,s);
        }
    }
      public void setImage(ActionEvent event) throws ExistException {
        wi.setFirstImg(firstimage);
        wi.setSecondImg(secondimage);
        wi.setThirdImg(thirdimage);
        wi.setForthImg(forthimage);
        wi.setRestStatus(firstimage, secondimage, thirdimage, forthimage);
    }
    
      
        
        
    
}
