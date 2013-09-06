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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long MallID;
    
    @OneToMany(mappedBy="mall")
    private Collection<Shop> shops = new
            ArrayList <Shop>();
    
    public Long getMallID() {
        return MallID;
    }

    public void setMallID(Long MallID) {
        this.MallID = MallID;
    }
    
     public Collection<Shop> getShops(){
        return shops;
    }
     public void setShops (Collection<Shop> shops){
          this.shops = shops;
     }
     
}
