/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Attraction;
import entity.AttractionPass;
import entity.AttractionPassTrans;
import entity.AttractionTicket;
import entity.AttractionTicketTrans;
import entity.Customer;
import entity.Membership;
import entity.PointTrans;
import exception.ExistException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author WU JINGYUN
 */
@Stateless
public class AttractionSessionBean implements AttractionSessionBeanRemote {

    @PersistenceContext()
    EntityManager em;
    //@EJB
    //CustomerBean cb = new CustomerBean();
    private Customer purchaseCustomer;
    private Attraction attractions;
    private AttractionPass pass;
    private AttractionTicket ticket;
    private AttractionPassTrans attractionPassTrans;
    private List<AttractionPassTrans> attractionPassTransList;
    private AttractionTicketTrans attractionTicketTrans;
    private List<AttractionTicketTrans> attractionTicketTransList;
    private List<Attraction> attractionList;
    private List<AttractionPass> passList;
    private List<AttractionTicket> ticketList;
    private PointTrans pointTrans;
    private Membership member;

    @Override
    public List<AttractionPass> getPass(String name) {
        attractions = new Attraction();
        passList = new ArrayList();
        System.err.println("Atraction session bean..." + name);
        attractions = em.find(Attraction.class, name);
        for (AttractionPass a : attractions.getPass()) {
            passList.add(a);
        }
        return passList;
    }

    @Override
    public List<AttractionTicket> getTicket(String name) {
        attractions = new Attraction();
        ticketList = new ArrayList();
        System.err.println("Atraction session bean..." + name);
        attractions = em.find(Attraction.class, name);
        for (AttractionTicket a : attractions.getTicket()) {
            ticketList.add(a);
        }
        return ticketList;
    }

    @Override
    public void createPass(String attractionName, String name, double price, String type, String remarks) throws ExistException {
        pass = new AttractionPass();
        attractions = new Attraction();

        if (em.find(AttractionPass.class, name) != null) {
            throw new ExistException("Pass already exists！");
        }
        attractions = em.find(Attraction.class, attractionName);
        if (attractions == null) {
            throw new ExistException("Can't find the attraction");
        }

        pass.createPass(name, price, type, remarks);
        attractions.getPass().add(pass);
        em.persist(pass);

    }

    @Override
    public void createTicket(String attractionName, String name, double price, String type, String remarks) throws ExistException {
        ticket = new AttractionTicket();
        attractions = new Attraction();

        if (em.find(AttractionTicket.class, name) != null) {
            throw new ExistException("Ticket already exists！");
        }
        attractions = em.find(Attraction.class, attractionName);
        if (attractions == null) {
            throw new ExistException("Can't find the attraction");
        }

        ticket.createTicket(name, price, type, remarks);
        attractions.getTicket().add(ticket);
        em.persist(ticket);
    }

    @Override
    public void editTicket(String name, double price, String type, String remarks) throws ExistException {
        ticket = new AttractionTicket();
        ticket = em.find(AttractionTicket.class, name);
        if (ticket == null) {
            throw new ExistException("Can't find the ticket");
        }
        ticket.editTicket(price, type, remarks);
        em.flush();
    }

    @Override
    public void editPass(String name, double price, String type, String remarks) throws ExistException {
        pass = new AttractionPass();
        pass = em.find(AttractionPass.class, name);
        if (pass == null) {
            throw new ExistException("Can't find the pass");
        }
        pass.editPass(price, type, remarks);
        em.flush();
    }

    @Override
    public void deleteTicket(String attractionName, String name) throws ExistException {
        ticket = new AttractionTicket();
        attractions = new Attraction();

        attractions = em.find(Attraction.class, attractionName);
        if (attractions == null) {
            throw new ExistException("Can't find the attraction");
        }
        ticket = em.find(AttractionTicket.class, name);
        if (ticket == null) {
            throw new ExistException("Can't find the ticket");
        }
        attractions.getTicket().remove(ticket);
        em.remove(ticket);
        em.flush();
    }

    @Override
    public void deletePass(String attractionName, String name) throws ExistException {
        pass = new AttractionPass();
        attractions = new Attraction();

        attractions = em.find(Attraction.class, attractionName);
        if (attractions == null) {
            throw new ExistException("Can't find the attraction");
        }
        pass = em.find(AttractionPass.class, name);
        if (pass == null) {
            throw new ExistException("Can't find the ticket");
        }
        attractions.getPass().remove(pass);
        em.remove(pass);
        em.flush();
    }

    @Override
    public void createAttraction(String name, int maxQuota, String operatingHours, String location, String descriptions) throws ExistException {
        attractions = new Attraction();
        System.err.println("Atraction session bean..." + name + " Op hours" + operatingHours);
        if (em.find(Attraction.class, name) != null) {
            throw new ExistException("Attraction already exists！");
        }
        attractions.createAttraction(name, maxQuota, operatingHours, location, descriptions);
        em.persist(attractions);

    }

    @Override
    public List<Attraction> getAttractions() {
        attractionList = new ArrayList();
        String ejbql = "SELECT a FROM Attraction a";
        Query q = em.createQuery(ejbql);
        for (Object o : q.getResultList()) {
            Attraction a = (Attraction) o;
            attractionList.add(a);
        }
        em.flush();
        return attractionList;
    }

    @Override
    public void editAttraction(String name, int maxQuota, String operatingHours, String location, String descriptions) throws ExistException {
        attractions = new Attraction();
        attractions = em.find(Attraction.class, name);
        if (attractions == null) {
            throw new ExistException("Can't find the attraction");
        }
        attractions.editAttraction(maxQuota, operatingHours, location, descriptions);
        em.flush();
    }

    @Override
    public void deleteAttraction(String name) throws ExistException {
        attractions = new Attraction();
        attractions = em.find(Attraction.class, name);
        if (attractions == null) {
            throw new ExistException("Can't find the attraction");
        }
        em.remove(attractions);
        em.flush();
    }

    @Override
    public void buyPass(Long customerId, int quantity, String attractionPassId) throws ExistException {
        attractionPassTrans = new AttractionPassTrans();
        pass = new AttractionPass();
        purchaseCustomer = new Customer();
        attractionPassTransList = new ArrayList();


        pass = em.find(AttractionPass.class, attractionPassId);
        if (pass == null) {
            throw new ExistException("Can't find the pass");
        }
        Calendar purchaseDate = Calendar.getInstance();
        Query query = em.createQuery("SELECT c from Customer c where c.Id=?1");
        query.setParameter(1, customerId);

        purchaseCustomer = (Customer) query.getSingleResult();
        int Point;
        int rewardPlan = purchaseCustomer.getMembership().getLoyaltyPlan().getRewardPoint();
        pointTrans = new PointTrans();


        attractionPassTrans.createAttractionPassTrans(purchaseCustomer, quantity, purchaseDate, pass);
        attractionPassTrans.setAmount(pass.getPrice() * quantity);
        attractionPassTransList.add(attractionPassTrans);
        Point = (int) ((pass.getPrice() * quantity) / 50);
        Calendar ca = Calendar.getInstance();
        if (Point != 0) {
            pointTrans.setDate_of_pointTrans(ca);
            pointTrans.setPoint(Point);
            pointTrans.setShopId(Long.parseLong(String.valueOf("8888")));// shopid 9999 stands for Attraction pass
            pointTrans.setType("Reward");
            pointTrans.setCustomer(purchaseCustomer);
            em.persist(pointTrans);


            int totalPoints = purchaseCustomer.getLoyaltyPointBalance();
            totalPoints += Point;
            purchaseCustomer.setLoyaltyPointBalance(totalPoints);


            purchaseCustomer.getPointTrans().add(pointTrans);
            purchaseCustomer.setPointTrans(purchaseCustomer.getPointTrans());
        }
        
        
        double totalAmountSpend = purchaseCustomer.getTotalAmountSpend() + pass.getPrice() * quantity;
        purchaseCustomer.setTotalAmountSpend(totalAmountSpend);


        purchaseCustomer.setAttractionPassTrans(attractionPassTransList);

        if (purchaseCustomer.getTotalAmountSpend() > 5000 && purchaseCustomer.getTotalAmountSpend() < 10000) {
            member = new Membership();
            Query query1 = em.createQuery("SELECT DISTINCT m FROM Membership m WHERE m.membershipType =?1");
            query.setParameter(1, "Gold");
            member = (Membership) query1.getSingleResult();
            purchaseCustomer.setMembership(member);
            System.err.println("member type is " + member.getMembershipType());
        } else if (purchaseCustomer.getTotalAmountSpend() > 5000 && purchaseCustomer.getTotalAmountSpend() < 25000) {
            member = new Membership();
            Query q2 = em.createQuery("SELECT DISTINCT m FROM Membership m WHERE m.membershipType =?1");

            q2.setParameter(1, "Diamond");
            member = (Membership) q2.getSingleResult();
            purchaseCustomer.setMembership(member);
            System.err.println("member type 2 is " + member.getMembershipType());
        } else if (purchaseCustomer.getTotalAmountSpend() > 25000) {
            member = new Membership();
            Query q3 = em.createQuery("SELECT DISTINCT m FROM Membership m WHERE m.membershipType =?1");
            q3.setParameter(1, "Platinum");
            member = (Membership) q3.getSingleResult();
            purchaseCustomer.setMembership(member);
            System.err.println("member type 2 is " + member.getMembershipType());
        } else {
            //do nothing
        }


        em.persist(purchaseCustomer);
        em.persist(attractionPassTrans);
        em.flush();

    }

    @Override
    public void purchaseTicket(Long customerId, int quantity, String ticketId,Calendar attendDate) throws ExistException {

        System.out.println("purchaseTicket");
        attractionTicketTrans = new AttractionTicketTrans();
        ticket = new AttractionTicket(); 
        purchaseCustomer = new Customer();
        attractionTicketTransList = new ArrayList();
        System.out.println("purchaseTicket2");
        ticket = em.find(AttractionTicket.class, ticketId);
        if (ticket == null) {
            throw new ExistException("Can't find the ticket");
        }
        
        Calendar purchaseDate = Calendar.getInstance();
        System.out.println(purchaseDate);
        Query query = em.createQuery("SELECT c from Customer c where c.Id=?1");
        query.setParameter(1, customerId);

        purchaseCustomer = (Customer) query.getSingleResult();
        System.out.println(purchaseCustomer.getUserName() + quantity + purchaseDate + ticket.getTicketType());

        int Point;
        int rewardPlan = purchaseCustomer.getMembership().getLoyaltyPlan().getRewardPoint();
        pointTrans = new PointTrans();
        System.out.println("purchaseTicket--> new pointTrans");


        Point = (int) ((ticket.getTicketPrice() * quantity) / 50);
        Calendar ca = Calendar.getInstance();
        if (Point != 0) {
            pointTrans.setDate_of_pointTrans(ca);
            pointTrans.setPoint(Point);
            pointTrans.setShopId(Long.parseLong(String.valueOf("8888")));// shopid 9999 stands for Attraction pass
            pointTrans.setType("Reward");
            pointTrans.setCustomer(purchaseCustomer);
            em.persist(pointTrans);
            System.out.println("purchaseTicket--> persist pointTrans");

            int totalPoints = purchaseCustomer.getLoyaltyPointBalance();
            totalPoints += Point;
            purchaseCustomer.setLoyaltyPointBalance(totalPoints);
            System.out.println("purchaseTicket--> set point totalPoints");

            purchaseCustomer.getPointTrans().add(pointTrans);
            purchaseCustomer.setPointTrans(purchaseCustomer.getPointTrans());

            System.out.println("purchaseTicket--> set customer setPointTrans");
        }


        double totalAmountSpend = purchaseCustomer.getTotalAmountSpend() + ticket.getTicketPrice() * quantity;
        purchaseCustomer.setTotalAmountSpend(totalAmountSpend);

        System.out.println("purchaseTicket--> set customer totalAmountSpend");

        attractionTicketTrans.createAttractionTicketTrans(purchaseCustomer, quantity, purchaseDate,attendDate , ticket);
        attractionTicketTrans.setAmount(ticket.getTicketPrice() * quantity);
        attractionTicketTransList.add(attractionTicketTrans);
        System.out.println(attractionTicketTransList.size());
        purchaseCustomer.setAttractionTicketTrans(attractionTicketTransList);


        if (purchaseCustomer.getTotalAmountSpend() > 5000 && purchaseCustomer.getTotalAmountSpend() < 10000) {
            member = new Membership();
            Query query1 = em.createQuery("SELECT DISTINCT m FROM Membership m WHERE m.membershipType =?1");
            query.setParameter(1, "Gold");
            member = (Membership) query1.getSingleResult();
            purchaseCustomer.setMembership(member);
            System.err.println("member type is " + member.getMembershipType());
        } else if (purchaseCustomer.getTotalAmountSpend() > 5000 && purchaseCustomer.getTotalAmountSpend() < 25000) {
            member = new Membership();
            Query q2 = em.createQuery("SELECT DISTINCT m FROM Membership m WHERE m.membershipType =?1");

            q2.setParameter(1, "Diamond");
            member = (Membership) q2.getSingleResult();
            purchaseCustomer.setMembership(member);
            System.err.println("member type 2 is " + member.getMembershipType());
        } else if (purchaseCustomer.getTotalAmountSpend() > 25000) {
            member = new Membership();
            Query q3 = em.createQuery("SELECT DISTINCT m FROM Membership m WHERE m.membershipType =?1");
            q3.setParameter(1, "Platinum");
            member = (Membership) q3.getSingleResult();
            purchaseCustomer.setMembership(member);
            System.err.println("member type 2 is " + member.getMembershipType());
        } else {
            //do nothing
        }




        em.persist(purchaseCustomer);
        em.persist(attractionTicketTrans);
        em.flush();

    }
}
