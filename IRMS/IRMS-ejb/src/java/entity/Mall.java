/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author wangxiahao
 */
@Entity
public class Mall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MallID;
    private String mallName;
    private int mallSize;
   
    
    @OneToMany(mappedBy="mall")
    private Collection<Unit> units = new
            ArrayList <Unit>();
    
    public Mall(){}
    
    public void createMall(String mallName ){
        this.mallName=mallName;
        mallSize= 20000;
    }
    
    public Long getMallID() {
        return MallID;
    }

    public void setMallID(Long MallID) {
        this.MallID = MallID;
    }
    
     public Collection<Unit> getUnits(){
        return units;
    }
     public void setUnits (Collection<Unit> units){
          this.units = units;
     }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public int getMallSize() {
        return mallSize;
    }

    public void setMallSize(int mallSize) {
        this.mallSize = mallSize;
    }
     
}
