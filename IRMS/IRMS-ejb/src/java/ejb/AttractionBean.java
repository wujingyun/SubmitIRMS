/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Attraction;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author WU JINGYUN
 */
@Stateless
public class AttractionBean implements AttractionBeanRemote {
   @PersistenceContext
    EntityManager em;
    Attraction arraction;
    public AttractionBean() {
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

//    @Override
//    public Attraction createAttraction() {
//        return null;
//    }

}
