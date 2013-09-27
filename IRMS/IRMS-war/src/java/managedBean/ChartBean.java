package managedBean;



import ejb.ManageTenantBeanRemote;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
@SessionScoped
public class ChartBean implements Serializable{

 @EJB
 ManageTenantBeanRemote mtb;
  private PieChartModel pieModel;
//  private HashMap<String, Integer> cache;

    public ChartBean() {
        
        createPieModel();
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
    //     cache= new HashMap<String, Integer>();
    //     cache = mtb.viewTenancyMix();
       /*for (Iterator<Map.Entry<String, Integer>> it = cache.entrySet().iterator(); it.hasNext();) {
             Map.Entry entry = it.next();
            System.out.println("In managed bean key,val: " + entry.getKey() + "," + entry.getValue());
     //       Integer i = (Integer)entry.getValue();
        //    pieModel.set((String)entry.getKey(), i.intValue());
        }*/
        String s = mtb.test();
       // this.setPieModel(pieModel);
        pieModel.set("Brand 1", 540);
        pieModel.set("Brand 2", 325);
        pieModel.set("Brand 3", 702);
        pieModel.set("Brand 4", 421);
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

 /*   public void setCache(HashMap<String, Integer> cache) {
        this.cache = cache;
    }

    public HashMap<String, Integer> getCache() {
        return cache;
    }*/
    
    
}
