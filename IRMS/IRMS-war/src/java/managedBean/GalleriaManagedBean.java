/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;
  
import java.io.Serializable;
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.List;  
import java.util.Map;
import javax.annotation.PostConstruct;  
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import util.dao.Galleria;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class GalleriaManagedBean implements Serializable {

    /**
     * Creates a new instance of fileUploadManagedBean
     */
    public GalleriaManagedBean() {
        Map<String, String> subrolesSuper = new HashMap<String, String>();
        subrolesSuper.put("Super Admin", "1");
    }
    private List<Galleria> images;  
   // List<TenancyMixData> tenanctMixDatas = new ArrayList<TenancyMixData>();
  
    @PostConstruct  
    public void init() {  
        images =  new ArrayList<Galleria>();
        
       
            images.add(new Galleria("galleriaDeluxe" + 1 + ".jpg","Deluxe Room Bathroom: Featuring a walk-in shower and Paiza amenities."));  
       images.add(new Galleria("galleriaDeluxe" + 2 + ".jpg","SkyPark Infinity Pool: Relax, 57 storys above the ground."));  
        images.add(new Galleria("galleriaDeluxe" + 3 + ".jpg","Banyan Tree Fitness Club: Work out at our cardio, yoga and aerobics studios on level 55."));  
         images.add(new Galleria("galleriaDeluxe" + 4 + ".jpg","Banyan Tree Fitness Club: Unwind in our Relaxation Lounge facing the ocean."));  
          images.add(new Galleria("galleriaDeluxe" + 5 + ".jpg","Deluxe Room with City View: A guarantted piece of Singapore's best city-skyline view."));  
        
       
    
    }

    public void setImages(List<Galleria> images) {
        this.images = images;
    }
       
    public List<Galleria> getImages() {  
        return images;  
    }  
  
   
    
}
