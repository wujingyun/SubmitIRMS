/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Customer;
import entity.Hotel;
import entity.InternalRoomRequest;
import entity.Room;
import entity.RoomReservation;
import entity.Staff;
import exception.ExistException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Yang Zhennan
 */
@Stateless
public class HotelReservationBean implements HotelReservationBeanRemote {

    @PersistenceContext()
    EntityManager em;
    Hotel hotel;
    Room room;
    Customer customer;
    Staff staff;
    RoomReservation roomReservation;
    String st = new String();
    private final static Integer[] roomNumbers;
    private final static Integer[] roomNumbers2;

    static {
        roomNumbers = new Integer[10];
        roomNumbers[0] = 101;
        roomNumbers[1] = 102;
        roomNumbers[2] = 103;
        roomNumbers[3] = 201;
        roomNumbers[4] = 301;
        roomNumbers[5] = 401;
        roomNumbers[6] = 501;
        roomNumbers[7] = 601;
        roomNumbers[8] = 701;
        roomNumbers[9] = 801;
    }

    static {
        roomNumbers2 = new Integer[10];
        roomNumbers2[0] = 104;
        roomNumbers2[1] = 105;
        roomNumbers2[2] = 106;
        roomNumbers2[3] = 202;
        roomNumbers2[4] = 302;
        roomNumbers2[5] = 402;
        roomNumbers2[6] = 502;
        roomNumbers2[7] = 602;
        roomNumbers2[8] = 702;
        roomNumbers2[9] = 802;
    }

    @Override
    public List<RoomReservation> getHotelReservations(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }

        Query q = em.createQuery("SELECT r FROM RoomReservation r WHERE r.hotelName=:hotelName AND r.status!='Cancelled'");
        q.setParameter("hotelName", hotelName);
        return q.getResultList();
    }

    @Override
    public List<RoomReservation> getRoomReservations(String hotelName, Integer roomNumber) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        room = hotel.findRoom(roomNumber);
        if (hotel == null) {
            throw new ExistException("ROOM NOT EXIST.");
        }
        return (List) room.getRoomReservations();
    }

    @Override
    public List<InternalRoomRequest> getInternalRoomRequests(String hotelName) throws ExistException {
        Query q = em.createQuery("SELECT r FROM InternalRoomRequest r WHERE r.hotelName=:hotelName");
        q.setParameter("hotelName", hotelName);
        /*
         Collection<InternalRoomRequest> requests = new ArrayList();
         for (Object o : q.getResultList()) {
         InternalRoomRequest r = (InternalRoomRequest) o;
         requests.add(r);
         }
         */
        return (List) q.getResultList();
    }
    /*
     @Override
     public boolean checkOverbookLimit(String hotelName, Integer quantity, Date startDate, Date endDate) throws ExistException {
     //check if reservation exceeds overbook limit over the period
     hotel = em.find(Hotel.class, hotelName);
     if (hotel == null) {
     throw new ExistException("HOTEL NOT EXIST.");
     }
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(endDate);
     for (int i = 0; i <= (Integer) (endDate.get(Calendar.DAY_OF_YEAR) - startDate.get(Calendar.DAY_OF_YEAR)); i++) {
     Integer countReserved = new Integer(0);
     Integer countConfirmed = new Integer(0);
     Query q1 = em.createQuery("SELECT r FROM RoomReservation r WHERE r.hotelName=:hotelName AND r.startDate<=:startDate");
     Query q2 = em.createQuery("SELECT r FROM RoomReservation r WHERE r.hotelName=:hotelName AND r.type='Confirmed' AND r.startDate<=:startDate");
     for (Object o : q1.getResultList()) {
     RoomReservation r = (RoomReservation) o;
     countReserved += r.getQuantity();
     }
     for (Object o : q2.getResultList()) {
     RoomReservation r = (RoomReservation) o;
     countConfirmed += r.getQuantity();
     }
     if (countReserved + quantity == hotel.getCapacity()) {
     Integer overbookLimit = hotel.getCapacity() + countConfirmed * (int) hotel.getOverbookRate();
     hotel.setOverbookLimit(startDate, overbookLimit);
     }
     if ((countReserved + quantity) > hotel.getOverbookLimit(startDate)) {
     return false;
     }
     startDate.add(Calendar.DAY_OF_YEAR, 1);
     }
     return true;
     }
     */
    /*
     @Override
     public boolean checkAvailability(String hotelName, String roomType, Integer quantity, Date startDate, Date endDate) throws ExistException {
     //check if there is a right type of room available during the period
     Integer countGuaranteed = new Integer(0);
     Query q1 = em.createQuery("SELECT COUNT(r.roomNumber) FROM Room r WHERE r.hotel.name=:hotelName AND r.type=:roomType AND r.availabilityStatus='available'");
     q1.setParameter("hotelName", hotelName);
     q1.setParameter("roomType", roomType);
     Long numOfRooms = (Long) q1.getSingleResult();
     Query q2 = em.createQuery("SELECT r FROM RoomReservation r WHERE r.hotelName=:hotelName AND r.roomType=:roomType AND r.status='Guaranteed' AND (r.startDate<=:endDate OR r.endDate>=:startDate)");
     q2.setParameter("hotelName", hotelName);
     q2.setParameter("endDate", endDate);
     q2.setParameter("startDate", startDate);
     q2.setParameter("roomType", roomType);
     for (Object o : q2.getResultList()) {
     RoomReservation r = (RoomReservation) o;
     countGuaranteed += r.getQuantity();
     }
     if (numOfRooms <= (countGuaranteed + quantity)) {
     return false;
     }
     return true;
     }
     */

    @Override
    public RoomReservation makeReservation(Long customerId, String hotelName, String roomType, Integer quantity, Date startDate, Date endDate, String remark, Calendar dateReserved) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        customer = em.find(Customer.class, customerId);
        if (customer == null) {
            throw new ExistException("CUSTOMER NOT EXIST.");
        }
        //System.out.println(hotelName + customerId + roomType);
        roomReservation = new RoomReservation();
        roomReservation.setCustomerId(customerId);
        roomReservation.setDateReserved(dateReserved);
        roomReservation.create(hotelName, roomType, quantity, startDate, endDate, remark);
        roomReservation.setCustomer(customer);
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        Integer dates = (Integer) (end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR));
        double total = hotel.findRoomPrice(roomType) * quantity*dates;
        List<Room> rms = new ArrayList<Room>();
        if (roomType.equals("Standard")) {
            st = "";
            for (int i = 0; i < quantity; i++) {
                Integer ind = (int) (Math.random() * 10);
                Integer t = roomNumbers[ind];
                st = st + t + " ";
                Room r = em.find(Room.class, t);
                r.setAvailabilityStatus("Reserved");
                rms.add(r);
            }
            roomReservation.setRooms(rms);
        }
        if (roomType.equals("Suite")) {
            st = "";
            for (int i = 0; i < quantity; i++) {
                Integer index = (int) (Math.random() * 10);
                Integer temp = roomNumbers2[index];
                st = st + temp + " ";
                Room rm = em.find(Room.class, temp);
                rm.setAvailabilityStatus("Reserved");
                rms.add(rm);
            }
            roomReservation.setRooms(rms);
        }
        roomReservation.setRoomString(st);
        roomReservation.setTotal(total);
        roomReservation.setSource("Internal");
        customer.getRoomReservations().add(roomReservation);
        customer.setRoomReservations(customer.getRoomReservations());
        em.persist(roomReservation);

        /*
         //allocate room
        
         System.err.println("Before allocateRoom");
        
         if(this.allocateRoom(roomReservation.getId())){
         System.out.println(roomReservation.getRooms().size());
         }*/
        return roomReservation;
    }
    /*
     @Override
     public boolean allocateRoom(Long reservationId) throws ExistException {
     roomReservation = em.find(RoomReservation.class, reservationId);
     if (roomReservation == null) {
     throw new ExistException("ROOM RESERVATION NOT EXIST.");
     }
     String hotelName = roomReservation.getHotelName();
     String roomType = roomReservation.getRoomType();
     Integer quantity = roomReservation.getQuantity();
     Date startDate = roomReservation.getStartDate();
     Date endDate = roomReservation.getEndDate();
     if (this.checkAvailability(hotelName, roomType, quantity, startDate, endDate) == true) {
     //get list of available rooms with the reqeusted type
     hotel = em.find(Hotel.class, hotelName);
     if (hotel == null) {
     throw new ExistException("HOTEL NOT EXIST.");
     }
     ArrayList<Room> availableRooms = new ArrayList<Room>();
     boolean flag = true;
     for (Object o : hotel.getRooms()) {
     Room r = (Room) o;
     if (r.getType().equals(roomType)) {
     ArrayList<RoomReservation> reservations = (ArrayList) room.getRoomReservations();
     for (int i = 0; i < reservations.size(); i++) {
     if (reservations.get(i).getStartDate().before(endDate) || reservations.get(i).getEndDate().after(startDate)) {
     flag = false;
     break;
     }
     }
     if (flag == true) {
     availableRooms.add(r);
     }
     }
     }
     //sort available rooms according to room number
     for (int i = 1; i < availableRooms.size(); i++) {
     for (int j = 0; j < availableRooms.size() - i; j++) {
     if (availableRooms.get(j).getRoomNumber() > availableRooms.get(j + 1).getRoomNumber()) {
     Room temp = availableRooms.get(j);
     availableRooms.set(j, availableRooms.get(j + 1));
     availableRooms.set(j + 1, temp);
     }
     }
     }
     //allocate the first x(x=quantity) rooms to this reservation
     for (int i = 0; i < quantity; i++) {
     Room r = availableRooms.get(i);
     r.getRoomReservations().add(roomReservation);
     r.setRoomReservations(r.getRoomReservations());
     roomReservation.getRooms().add(r);
     roomReservation.setRooms(roomReservation.getRooms());
     em.flush();
     }
     return true;
     } else {
     List<RoomReservation> guaranteedReservations = (List) this.getHotelReservations(hotelName);
     //sort confirmed reservation according to the time when reservation is confirmed;
     for (int i = 1; i < guaranteedReservations.size(); i++) {
     for (int j = 0; j < guaranteedReservations.size() - i; j++) {
     if (guaranteedReservations.get(j).getDateReserved().after(guaranteedReservations.get(j + 1).getDateReserved())) {
     RoomReservation r = guaranteedReservations.get(j);
     guaranteedReservations.set(j, guaranteedReservations.get(j + 1));
     guaranteedReservations.set(j + 1, r);
     }
     }
     }
     //sort confirmed reservation according to startDate;
     for (int i = 1; i < guaranteedReservations.size(); i++) {
     for (int j = 0; j < guaranteedReservations.size() - i; j++) {
     if (guaranteedReservations.get(j).getStartDate().after(guaranteedReservations.get(j + 1).getStartDate())) {
     RoomReservation r = guaranteedReservations.get(j);
     guaranteedReservations.set(j, guaranteedReservations.get(j + 1));
     guaranteedReservations.set(j + 1, r);
     }
     }
     }
     //clear all confirmed reservations for all rooms
     for (int i = 0; i < guaranteedReservations.size(); i++) {
     List<Room> reservedRooms = (List) guaranteedReservations.get(i).getRooms();
     for (int j = 0; j < reservedRooms.size(); j++) {
     reservedRooms.get(j).setRoomReservations(null);
     }
     guaranteedReservations.get(i).setRooms(null);
     em.flush();
     }
     //reallocate confirmed reservations
     System.err.println("guaranteedReservations.size(): " + guaranteedReservations.size());
     for (int i = 0; i < guaranteedReservations.size(); i++) {
     this.allocateRoom(guaranteedReservations.get(i).getId());
     }
     //allocate room to this reservation;
     if (this.checkAvailability(hotelName, roomType, quantity, startDate, endDate) == false) {
     System.out.println("No more room available for this reservation!");
     return false;
     } else {
     this.allocateRoom(reservationId);
     }
            
     return false;
     }
     }
     */

    @Override
    public boolean confirmReservation(Long reservationId) throws ExistException {
        roomReservation = em.find(RoomReservation.class, reservationId);
        if (roomReservation == null) {
            throw new ExistException("ROOM RESERVATION NOT EXIST.");
        }
        customer = em.find(Customer.class, roomReservation.getCustomer().getCustomerId());
        if (roomReservation.getRoomAllocationStatus().equals("Allocated") && roomReservation.getPaymentStatus().equals("Full")) {
            roomReservation.setStatus("Guaranteed");
            //this.sendEmail(customer.getEmail());
            return true;
        } else if (roomReservation.getRoomAllocationStatus().equals("Allocated") && roomReservation.getPaymentStatus().equals("Partial")) {
            roomReservation.setStatus("Confirmed");
            //this.sendEmail(customer.getEmail());
            return true;
        }
        System.out.println("Reservation cannot be confirmed. Please check room allocation or payment status.");
        return false;
    }

    @Override
    public void editReservation(Long reservationId, String hotelName, String roomType, Integer quantity, Date startDate, Date endDate, String remark, String status, double total, String paymentStatus) throws ExistException {
        roomReservation = em.find(RoomReservation.class, reservationId);
        if (roomReservation == null) {
            throw new ExistException("ROOM RESERVATION NOT EXIST.");
        }
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        //staff.getInternalReservations().add(roomReservation);
        //staff.setInternalReservations(staff.getInternalReservations());
        //roomReservation.setStaff(staff);
        roomReservation.setHotelName(hotelName);
        roomReservation.setRoomType(roomType);
        roomReservation.setQuantity(quantity);
        roomReservation.setStartDate(startDate);
        roomReservation.setEndDate(endDate);
        roomReservation.setRemark(remark);
        roomReservation.setStatus(status);
        roomReservation.setTotal(total);
        roomReservation.setPaymentStatus(paymentStatus);
        //roomReservation.setSource("Internal");
        em.flush();
    }

    @Override
    public void cancelReservation(Long reservationId) throws ExistException {
        roomReservation = em.find(RoomReservation.class, reservationId);
        if (roomReservation == null) {
            throw new ExistException("ROOM RESERVATION NOT EXIST.");
        }
        //System.out.println(roomReservation.getRooms().size());
        Integer size = roomReservation.getRooms().size();
        for (int i = 0; i < size; i++) {
            roomReservation.getRooms().get(i).setAvailabilityStatus("Available");
        }
        roomReservation.setRooms(null);
        roomReservation.setStatus("Cancelled");
        em.flush();
    }
    /*
     @Override
     public void sendEmail(String customerEmail) throws ExistException {
     }
     */
}