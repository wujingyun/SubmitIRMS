/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.FbBanquet;
import entity.FbBanquetReservation;
import entity.FbContract;
import entity.FbOrderList;
import entity.FbQuotation;
import entity.FbRequest;
import entity.Fbalacarte;
import entity.Fbpackage;
import exception.ExistException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class BanquetSessionBean implements BanquetSessionBeanRemote {

    @PersistenceContext()
    EntityManager em;
    private FbBanquet banquet;
    private FbBanquetReservation reservation;
    private FbRequest request;
    private FbQuotation quotation;
    private FbOrderList orderList;
    private FbContract contract;
    private Collection<FbBanquetReservation> reservationList;
    private Collection<FbRequest> requestList;
    private Collection<FbBanquet> banquetList;
    private Collection<FbContract> contractList;

    @Override
    public void createReservation(String banquetName, Date dateReserved, Date dateEnded, String purpose, String contact, String customerName, String email)
            throws ExistException {

        reservation = new FbBanquetReservation();
        banquet = new FbBanquet();
        reservationList = new ArrayList<FbBanquetReservation>();
        
        banquet = em.find(FbBanquet.class, banquetName);
        System.err.println("session bean: createReservation==========1");
        for (FbBanquetReservation fr : banquet.getReservation()) {
            if (fr == null) {
                break;
            }
           
            Date d1 = fr.getDateReserved();
            Date d2 = fr.getDateEnded();
           System.err.println(d1 +"  "+d2); 
            if (!(dateReserved.after(d2) || dateEnded.before(d1))) {
                 
                throw new ExistException("A reservation has already existed during this period!");
        
            } 
            
            System.err.println("BanquetSessionBean: createReservation: reservation does not conflict");    
        }
     System.err.println("session bean: createReservation==========2");
        reservation.createReservation(dateReserved, dateEnded, purpose, contact, customerName, email);
        
        banquet.getReservation().add(reservation);
        reservation.setBanquet(banquet);
        em.persist(reservation);
        em.flush();
    }

    @Override
    public void deleteReservation(Long id) {
      
        banquet = new FbBanquet();
        reservation = new FbBanquetReservation();
        reservation = em.find(FbBanquetReservation.class, id);
        banquet = reservation.getBanquet();
        banquet.getReservation().remove(reservation);
        reservation.setBanquet(null);
        em.remove(reservation);
        em.flush();
    }

    @Override
    public void createRequest(String venue, String customerName, int capacity, Date bookingDate, int duration, String contact, String email) {
        request = new FbRequest();
  //      reservation = new FbBanquetReservation();
  //      em.persist(reservation);
        request.createRequest(venue, customerName, capacity, bookingDate, duration, contact, email);
        request.setReservation(reservation);
        em.persist(request);
    }

    @Override
    public void deleteRequest(Long id) {
        request = new FbRequest();

        request = em.find(FbRequest.class, id);
        em.remove(request);
    }

    @Override
    public void createBanquet(String banquetHallName, String location, int capacity) {
        banquet = new FbBanquet();
        banquet.createBanquet(banquetHallName, location, capacity);
        System.err.println("session:  createBanquet " + banquet.getBanquetHallName());
        em.persist(banquet);
    }

    @Override
    public void editBanquet(String banquetHallName, String location, int capacity) {
        banquet = new FbBanquet();
        banquet = em.find(FbBanquet.class, banquetHallName);
        banquet.setLocation(location);
        banquet.setCapacity(capacity);
        em.flush();
    }

    @Override
    public void deleteBanquet(String banquetHallName) throws ExistException {
        banquet = new FbBanquet();

        banquet = em.find(FbBanquet.class, banquetHallName);
        if (!banquet.getReservation().isEmpty()) {
            throw new ExistException("Reservations for the hall still exist!");
        }
        em.remove(banquet);
    }

   

  

  

   

//--------------------------get List section-------------------------------------------

    @Override
    public Collection<FbRequest> getRequestList() {
        requestList = new ArrayList<FbRequest>();
        String ejbql = "SELECT r FROM FbRequest r";
        Query q = em.createQuery(ejbql);
        for (Object o : q.getResultList()) {
            FbRequest fr = (FbRequest) o;
            requestList.add(fr);
        }
        em.flush();
        return requestList;


    }

    @Override
    public Collection<FbBanquetReservation> getReservationList() {
        reservationList = new ArrayList<FbBanquetReservation>();
        String ejbql = "SELECT b FROM FbBanquetReservation b";
        Query q = em.createQuery(ejbql);
        for (Object o : q.getResultList()) {
            FbBanquetReservation fb = (FbBanquetReservation) o;
            reservationList.add(fb);

        }
        em.flush();
        return reservationList;
    }

    @Override
    public Collection<FbBanquet> getBanquetList() {
        banquetList = new ArrayList<FbBanquet>();
        String ejbql = "SELECT b FROM FbBanquet b";
        Query q = em.createQuery(ejbql);
        for (Object o : q.getResultList()) {
            FbBanquet fb = (FbBanquet) o;
            banquetList.add(fb);

        }
        em.flush();
        return banquetList;
    }
    
    @Override
    public Collection<FbBanquetReservation> getOneBanquetReservationList(String banquetHallName) {
        
         reservationList = new ArrayList<FbBanquetReservation>();
         reservation = new FbBanquetReservation();
         banquet = new FbBanquet();
         banquet = em.find(FbBanquet.class, banquetHallName);
            
      for(Iterator it = banquet.getReservation().iterator();it.hasNext();){
          FbBanquetReservation fbr = (FbBanquetReservation)it.next();
          reservationList.add(fbr);
      }
        em.flush();
        return reservationList;
    }

   
}
