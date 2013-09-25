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
import java.util.Collection;
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

    @Override
    public Collection<RoomReservation> getHotelReservations(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        ArrayList<Room> rooms = (ArrayList) hotel.getRooms();
        Collection<RoomReservation> hotelReservations = new ArrayList();
        for (int i = 0; i < rooms.size(); i++) {
            hotelReservations.addAll(rooms.get(i).getRoomReservations());
        }
        return hotelReservations;
    }

    @Override
    public Collection<RoomReservation> getRoomReservations(String hotelName, Integer roomNumber) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        room = hotel.findRoom(roomNumber);
        if (hotel == null) {
            throw new ExistException("ROOM NOT EXIST.");
        }
        return room.getRoomReservations();
    }

    @Override
    public Collection<InternalRoomRequest> getInternalRoomRequests() throws ExistException {
        Query q = em.createQuery("SELECT r FROM InternalRoomRequest r");
        Collection<InternalRoomRequest> requests = new ArrayList();
        for (Object o : q.getResultList()) {
            InternalRoomRequest r = (InternalRoomRequest) o;
            requests.add(r);
        }
        return requests;
    }

    @Override
    public boolean checkOverbookLimit(String hotelName, Integer quantity, Calendar startDate, Calendar endDate) throws ExistException {
        //check if reservation exceeds overbook limit over the period
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        for (int i = 0; i <= (Integer) (endDate.get(Calendar.DAY_OF_YEAR) - startDate.get(Calendar.DAY_OF_YEAR)); i++) {
            Integer countReserved = new Integer(0);
            Integer countConfirmed = new Integer(0);
            Query q1 = em.createQuery("SELECT r FROM RoomReservation WHERE r.hotelName=:hotelname AND r.startDate<=:startDate");
            Query q2 = em.createQuery("SELECT r FROM RoomReservation WHERE r.hotelName=:hotelName AND r.type='Confirmed' AND r.startDate<=:startDate");
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

    @Override
    public boolean checkAvailability(String hotelName, String roomType, Integer quantity, Calendar startDate, Calendar endDate) throws ExistException {
        //check if there is a right type of room available during the period
        Integer countGuaranteed = new Integer(0);
        Query q1 = em.createQuery("SELECT COUNT(roomNumber) FROM Room WHERE r.hotelName=:hotelName AND r.type=:roomType AND r.availabilityStatus='available'");
        Integer numOfRooms = (Integer) q1.getSingleResult();
        Query q2 = em.createQuery("SELECT r FROM RoomReservation WHERE r.hotelName=:hotelName AND r.roomType=:roomType AND r.type='Guaranteed' AND (r.startDate<=:endDate OR r.endDate>=:startDate)");
        for (Object o : q2.getResultList()) {
            RoomReservation r = (RoomReservation) o;
            countGuaranteed += r.getQuantity();
        }
        if (numOfRooms <= (countGuaranteed + quantity)) {
            return false;
        }
        return true;
    }

    @Override
    public void makeReservation(Long customerId, String hotelName, String roomType, Integer quantity, Calendar startDate, Calendar endDate, String remark) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        customer = em.find(Customer.class, customerId);
        if (customer == null) {
            throw new ExistException("CUSTOMER NOT EXIST.");
        }
        roomReservation = new RoomReservation();
        roomReservation.create(hotelName, roomType, quantity, startDate, endDate, remark);
        roomReservation.setCustomer(customer);
        double total = hotel.findRoomPrice(roomType) * quantity;
        roomReservation.setTotal(total);
        roomReservation.setSource("Internal");
        customer.getRoomReservations().add(roomReservation);
        customer.setRoomReservations(customer.getRoomReservations());
        em.persist(roomReservation);
    }

    @Override
    public boolean allocateRoom(Long reservationId) throws ExistException {
        roomReservation = em.find(RoomReservation.class, reservationId);
        if (roomReservation == null) {
            throw new ExistException("ROOM RESERVATION NOT EXIST.");
        }
        String hotelName = roomReservation.getHotelName();
        String roomType = roomReservation.getRoomType();
        Integer quantity = roomReservation.getQuantity();
        Calendar startDate = roomReservation.getStartDate();
        Calendar endDate = roomReservation.getEndDate();
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
            ArrayList<RoomReservation> guaranteedReservations = (ArrayList) this.getHotelReservations(hotelName);
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
                ArrayList<Room> reservedRooms = (ArrayList) guaranteedReservations.get(i).getRooms();
                for (int j = 0; j < reservedRooms.size(); j++) {
                    reservedRooms.get(j).setRoomReservations(null);
                }
                guaranteedReservations.get(i).setRooms(null);
                em.flush();
            }
            //reallocate confirmed reservations
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
        }
        return false;
    }

    @Override
    public boolean confirmReservation(Long reservationId) throws ExistException {
        roomReservation = em.find(RoomReservation.class, reservationId);
        if (roomReservation == null) {
            throw new ExistException("ROOM RESERVATION NOT EXIST.");
        }
        customer=em.find(Customer.class, roomReservation.getCustomer().getCustomerId());
        if (roomReservation.getRoomAllocationStatus().equals("Allocated") && roomReservation.getPaymentStatus().equals("Full")) {
            roomReservation.setType("Guaranteed");
            //this.sendEmail(customer.getEmail());
            return true;
        } 
        else if (roomReservation.getRoomAllocationStatus().equals("Allocated") && roomReservation.getPaymentStatus().equals("Partial")) {
            roomReservation.setType("Confirmed");
            //this.sendEmail(customer.getEmail());
            return true;
        }
        System.out.println("Reservation cannot be confirmed. Please check room allocation or payment status.");
        return false;
    }

    @Override
    public void editReservation(Long staffId, Long reservationId, String hotelName, String roomType, Integer quantity, Calendar startDate, Calendar endDate) throws ExistException {
        staff = em.find(Staff.class, staffId);
        if (staff == null) {
            throw new ExistException("STAFF NOT EXIST.");
        }
        roomReservation = em.find(RoomReservation.class, reservationId);
        if (roomReservation == null) {
            throw new ExistException("ROOM RESERVATION NOT EXIST.");
        }
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        staff.getInternalReservations().add(roomReservation);
        staff.setInternalReservations(staff.getInternalReservations());
        roomReservation.setStaff(staff);
        roomReservation.setHotelName(hotelName);
        roomReservation.setRoomType(roomType);
        roomReservation.setQuantity(quantity);
        roomReservation.setStartDate(startDate);
        roomReservation.setEndDate(endDate);
        roomReservation.setSource("Internal");
        em.flush();
    }

    @Override
    public void cancelReservation(Long reservationId) throws ExistException {
        roomReservation = em.find(RoomReservation.class, reservationId);
        if (roomReservation == null) {
            throw new ExistException("ROOM RESERVATION NOT EXIST.");
        }
        roomReservation.setType("Cancelled");
        em.flush();
    }
    /*
    @Override
    public void sendEmail(String customerEmail) throws ExistException {
    }
    */
}