package ejb;

import entity.Customer;
import entity.EntShow;
import entity.Membership;
import entity.PointTrans;
import entity.ShowTicketTrans;
import entity.TicketCat;
import entity.TicketSeat;
import exception.ExistException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
      private Membership member;
    private TicketSeat ticketSeat;
    private List<TicketSeat> ticketSeatList;
    private ShowTicketTrans showTicketTrans;
    private List<ShowTicketTrans> showTicketTransList;
    private List<TicketCat> ticketCatList;
    private TicketCat ticketCat;
    private EntShow entshow;
    private List<EntShow> showList;
    private double amount;
    private PointTrans pointTrans;
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
        ticketSeatList = new ArrayList();
        for (int i = 0; i < ticketCatList.size(); i++) {
            for (int j = 0; j < ticketCatList.get(i).getTicketSeat().size(); j++) {
                ticketSeat = ticketCatList.get(i).getTicketSeat().get(j);
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
            System.out.println("ShowBean--> getTotalAmount-->" + selectedSeats.size());
            amount = amount + ticketSeatList.get(i).getTicketCat().getCatPrice();

            //System.out.println("ShowBean--> getTotalAmount-->"+ticketSeatList.get(i).getSeatId()+ticketSeatList.get(i).getTicketCat().getCatPrice());
        }
        return amount;
    }

    @Override
    public void makeBooking(Long customerId, List<Long> selectedSeats) throws ExistException {


        Query q = em.createQuery("SELECT c FROM Customer c where c.Id=?1");
        q.setParameter(1, customerId);
        customer = (Customer) q.getSingleResult();

        int Point;
        int rewardPlan = customer.getMembership().getLoyaltyPlan().getRewardPoint();

        //find seat according to list of seat id
        //put seats into a list
        ticketSeatList = new ArrayList();
        showTicketTrans = new ShowTicketTrans();
        pointTrans = new PointTrans();
        for (int i = 0; i < selectedSeats.size(); i++) {
            System.out.println("ShowBean--> makebooking3");
            System.out.println("ShowBean--> makebooking" + selectedSeats.get(i).toString());
            ticketSeat = em.find(TicketSeat.class, selectedSeats.get(i));
            ticketSeatList.add(ticketSeat);
            //  showTicketTrans.getTicketSeat().add(ticketSeat);
        }


        showTicketTrans.create(customer, ticketSeatList);
        showTicketTrans.setTicketSeat(ticketSeatList);

        showTicketTrans.setAmount(this.getTotalAmount(selectedSeats));
        em.persist(showTicketTrans);
        Point = (int) (this.getTotalAmount(selectedSeats) / 50);
        Calendar ca = Calendar.getInstance();

        pointTrans.setDate_of_pointTrans(ca);
        pointTrans.setPoint(Point);
        pointTrans.setShopId(Long.parseLong(String.valueOf("9999")));// shopid 9999 stands for show
        pointTrans.setType("Reward");
        pointTrans.setCustomer(customer);
        em.persist(pointTrans);

        customer.getPointTrans().add(pointTrans);
        customer.setPointTrans(customer.getPointTrans());
       
        int totalPoints = customer.getLoyaltyPointBalance();
        totalPoints += Point;
        customer.setLoyaltyPointBalance(totalPoints);
        
        double totalAmountSpend = customer.getTotalAmountSpend()+this.getTotalAmount(selectedSeats);
        customer.setTotalAmountSpend(totalAmountSpend);

        showTicketTransList = new ArrayList();
        showTicketTransList.add(showTicketTrans);
        customer.setShowTicketTrans(showTicketTransList);
        
        if(customer.getTotalAmountSpend()> 5000 &&  customer.getTotalAmountSpend()<10000 ){
                member =new Membership();
                 Query query =em.createQuery("SELECT DISTINCT m FROM Membership m WHERE m.membershipType =?1");
                 query.setParameter(1, "Gold");
                  member = (Membership) query.getSingleResult();
                  customer.setMembership(member);
                System.err.println("member type is "+member.getMembershipType());
            }else if(customer.getTotalAmountSpend()>5000 && customer.getTotalAmountSpend()<25000){
                  member =new Membership();
                 Query q2 =em.createQuery("SELECT DISTINCT m FROM Membership m WHERE m.membershipType =?1");
                 
                 q2.setParameter(1, "Diamond");
                  member = (Membership) q2.getSingleResult();
                  customer.setMembership(member);
                System.err.println("member type 2 is "+member.getMembershipType());
            }else if(customer.getTotalAmountSpend()> 25000){
                 member =new Membership();
                Query q3 =em.createQuery("SELECT DISTINCT m FROM Membership m WHERE m.membershipType =?1");
                q3.setParameter(1, "Platinum");
                 member = (Membership) q3.getSingleResult();
                 customer.setMembership(member);
                 System.err.println("member type 2 is "+member.getMembershipType());
            }else{
                //do nothing
            }
        
        em.persist(customer);
        for (int i = 0; i < selectedSeats.size(); i++) {

            ticketSeatList.get(i).setSeatStatus(false);
            ticketSeatList.get(i).setShowTicketTrans(showTicketTrans);
            showTicketTrans.setTicketSeat(ticketSeatList);
            em.persist(ticketSeatList.get(i));

            em.flush();
            em.refresh(ticketSeatList.get(i));
            ticketCat = em.find(TicketCat.class, ticketSeatList.get(i).getSeatId());
            ticketCat.setAvailNum(ticketCat.getAvailNum() - 1);
        }

    }
}
