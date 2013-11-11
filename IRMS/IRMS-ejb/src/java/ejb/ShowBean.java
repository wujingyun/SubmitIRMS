package ejb;

import entity.Customer;
import entity.EntShow;
import entity.ShowTicketTrans;
import entity.TicketCat;
import entity.TicketSeat;
import exception.ExistException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author WU JINGYUN
 */
@Stateless
public class ShowBean implements ShowBeanRemote {

    @PersistenceContext()
    EntityManager em;
    private Customer customer;
    private TicketSeat ticketSeat;
    private List<TicketSeat> ticketSeatList;
    private ShowTicketTrans showTicketTrans;
    private List<ShowTicketTrans> showTicketTransList;
    private List<TicketCat> ticketCatList;
    private TicketCat ticketCat;
    private EntShow entshow;
    private List<EntShow> showList;
    //purchase

    @Override
    public List<EntShow> getShowByName(String showName) throws ExistException {
        showList = new ArrayList();
        Query q = em.createQuery("SELECT  s FROM EntShow s where s.showName=?1");
        q.setParameter(1, showName);
        for (Object o : q.getResultList()) {
            EntShow t = (EntShow) o;
            showList.add(t);
        }
        em.flush();
        return showList;
    }
    
    
    @Override
    public String getShowNameById(Long showId) throws ExistException {
        showList = new ArrayList();
        Query q = em.createQuery("SELECT  distinct s.showName FROM EntShow s where s.showId=?1");
        q.setParameter(1, showId);
        String showName = (String) q.getSingleResult();
        return showName;
    }

    @Override
    public List<TicketSeat> getAllSeat(Long showid) throws ExistException {

        Query q = em.createQuery("SELECT  s FROM EntShow s where s.showId=?1");
        q.setParameter(1, showid);
        System.out.println(showid);
        entshow = (EntShow) q.getSingleResult();
        if (entshow == null) {
            throw new ExistException("No Show EXISTS.");
        }

        ticketCatList = entshow.getTicketCat();
        ticketCatList.size();
        ticketSeatList=new ArrayList();
        for (int i = 0; i < ticketCatList.size(); i++) {
            for (int j = 0; j < ticketCatList.get(i).getTicketSeat().size(); j++) {
                ticketSeat=ticketCatList.get(i).getTicketSeat().get(j);
                ticketSeatList.add(ticketSeat);
            }
           
            System.out.println("ShowBean--> getAllSeat" + i + ticketSeatList.size());
        }
        return ticketSeatList;

    }

    @Override
    public List<TicketCat> getCategoryInfo(Long showid) throws ExistException {

        Query q = em.createQuery("SELECT  s FROM EntShow s where s.showId=?1");
        q.setParameter(1, showid);
        System.out.println(showid);
        entshow = (EntShow) q.getSingleResult();
        if (entshow == null) {
            throw new ExistException("No Show EXISTS.");
        }
        ticketCatList = (List<TicketCat>) entshow.getTicketCat();
        ticketCatList.size();
        return ticketCatList;
    }

    @Override
    public double getTotalAmount(List<Long> selectedSeats) throws ExistException {
        double amount = 0;
         ticketSeatList = new ArrayList();
        for (int i = 0; i < selectedSeats.size(); i++) {
           ticketSeat = em.find(TicketSeat.class, selectedSeats.get(i));
            ticketSeatList.add(ticketSeat);
           // amount = amount + selectedSeats.get(i).getTicketCat().getCatPrice();
        }
         for (int i = 0; i < selectedSeats.size(); i++) {
amount = amount + ticketSeatList.get(i).getTicketCat().getCatPrice();
            
         }
        return amount;
    }

    @Override
    public void makeBooking(Long customerId, List<Long> selectedSeats) throws ExistException {


        Query q = em.createQuery("SELECT c FROM Customer c where c.Id=?1");
        q.setParameter(1, customerId);
        customer = (Customer) q.getSingleResult();

        //find seat according to list of seat id
        //put seats into a list
        ticketSeatList = new ArrayList();

        for (int i = 0; i < selectedSeats.size(); i++) {
            System.out.println("ShowBean--> makebooking3");
            System.out.println("ShowBean--> makebooking" + selectedSeats.get(i).toString());
            ticketSeat = em.find(TicketSeat.class, selectedSeats.get(i));
            ticketSeatList.add(ticketSeat);
        }

        showTicketTrans = new ShowTicketTrans();
        showTicketTrans.create(customer, ticketSeatList);

        showTicketTrans.setTicketSeat(ticketSeatList);
        em.persist(showTicketTrans);
        showTicketTransList = new ArrayList();
        showTicketTransList.add(showTicketTrans);

        customer.setShowTicketTrans(showTicketTransList);
        em.persist(customer);
        for (int i = 0; i < selectedSeats.size(); i++) {

            ticketSeatList.get(i).setSeatStatus(false);
            ticketSeatList.get(i).setShowTicketTrans(showTicketTrans);

            em.persist(ticketSeatList.get(i));

            em.flush();
            em.refresh(ticketSeatList.get(i));
            ticketCat = em.find(TicketCat.class, ticketSeatList.get(i).getSeatId());
            ticketCat.setAvailNum(ticketCat.getAvailNum() - 1);
        }
    }
}
