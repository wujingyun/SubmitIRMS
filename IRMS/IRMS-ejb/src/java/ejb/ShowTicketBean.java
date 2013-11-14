package ejb;

import entity.EntShow;
import entity.TicketCat;
import entity.TicketSeat;
import exception.ExistException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Jiao Shen
 */

@Stateless
public class ShowTicketBean implements ShowTicketBeanRemote {
    
    @PersistenceContext()
    EntityManager em;
    
    private TicketCat ticketCat;
    private TicketSeat ticketSeat;
    private EntShow entShow;
    //private List<TicketCat> currentCat;
    //private List<TicketSeat> currentSeat;
    
    
    @Override
    public TicketCat addCategory (Long showId, String catName, double catPrice, int totalNum, int availNum) throws ExistException{
        //ticketCat = em.find(TicketCat.class, catName);
        //if (ticketCat != null) {
            //throw new ExistException("CATEGORY ALREADY EXISTS.");
        //}
        entShow = em.find(EntShow.class, showId);
        if(entShow == null){
            throw new ExistException("SHOW DOES NOT EXIST");
        }        
        ticketCat = new TicketCat();
        //List <TicketCat> ticketCatList = new ArrayList();
        System.out.println("Session-Ticket-1======================================");
        ticketCat.createTicketCat(catName, catPrice, totalNum, availNum);
       
        System.out.println("Session-Ticket-2======================================");       
        //ticketCatList.add(ticketCat);
        //entShow.setTicketCats(ticketCatList);
        entShow.getTicketCats().add(ticketCat);
        ticketCat.setEntShow(entShow);
        em.persist(ticketCat);
        
        /*
        currentCat = entShow.getTicketCats();
        int catSize = currentCat.size();
        for (int i = 0; i<catSize; i++){
            for (int j = 0; j < currentCat.get(i).getTicketSeats().size(); j++){
                ticketSeat = currentCat.get(i).getTicketSeats().get(j);
                currentSeat.add(ticketSeat);
            }
        }
        int seatSize = currentSeat.size();
        */
        
        //create seats
        List <TicketSeat> ticketSeatList = new ArrayList();
        for (int i=0; i<totalNum; i++) {
             ticketSeat = new TicketSeat();
             System.out.println("Session-Ticket-3======================================");
             ticketSeat.createTicketSeat(true);
             System.out.println("Session-Ticket-4======================================");                      
             if(ticketSeat.getSeatId()<=270){
                 ticketSeat.setSeatNum((int)(ticketSeat.getSeatId()));
             }
             else{
                 ticketSeat.setSeatNum(((int)(ticketSeat.getSeatId()%270)));
             }
             ticketCat.getTicketSeats().add(ticketSeat);
             ticketSeat.setTicketCat(ticketCat);
             em.persist(ticketSeat); 
             em.flush();
             ticketSeatList.add(ticketSeat);
        }
        //ticketCat.setTicketSeats(ticketSeatList);
        
        //set seat number
        int size = ticketSeatList.size();
        for(int j=0; j<size; j++){
            int k = (int)ticketSeatList.get(j).getSeatId();
            if(k<=312)
                ticketSeatList.get(j).setSeatNum(k);
            else if(k%312!=0)
                ticketSeatList.get(j).setSeatNum(k%312);
            else
                ticketSeatList.get(j).setSeatNum(312);
        }
        
        em.persist(entShow);
        em.flush();
        return ticketCat;
    }
    
    @Override
    public void editCategory (Long catId, String catName, double catPrice, int totalNum, int availNum) throws ExistException{
        ticketCat = em.find(TicketCat.class, catId);
        if (ticketCat == null) {
            throw new ExistException("CATEGORY DOES NOT EXIST.");
        }
        ticketCat.setCatName(catName);
        ticketCat.setCatPrice(catPrice);
        ticketCat.setTotalNum(totalNum);
        ticketCat.setAvailNum(availNum);
        em.flush();
    }
    
    @Override
    public void deleteCategory (Long catId) throws ExistException{
        ticketCat = em.find(TicketCat.class, catId);
        if (ticketCat == null) {
            throw new ExistException("CATEGORY DOES NOT EXIST.");
        }
        em.remove(ticketCat);
        em.flush();
    }
    
    @Override
    public List<TicketCat> getTicketCats () {
        Query q = em.createQuery("SELECT t FROM TicketCat t");
        return q.getResultList();
    }
    
}
