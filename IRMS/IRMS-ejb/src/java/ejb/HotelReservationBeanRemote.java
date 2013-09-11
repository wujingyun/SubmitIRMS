/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.InternalRoomRequest;
import entity.RoomReservation;
import exception.ExistException;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface HotelReservationBeanRemote {
    public Collection<RoomReservation> getHotelReservations(String hotelName) throws ExistException;
    public Collection<RoomReservation> getRoomReservations(String hotelName, Integer roomNumber) throws ExistException;
    public Collection<InternalRoomRequest> getInternalRoomRequests() throws ExistException;
    public boolean checkOverbookLimit(String hotelName, Integer quantity, Calendar startDate, Calendar endDate) throws ExistException;
    public boolean checkAvailability(String hotelName, String roomType, Integer quantity, Calendar startDate, Calendar endDate) throws ExistException;
    public void makeReservation(Long customerId, String hotelName, String roomType, Integer quantity, Calendar startDate, Calendar endDate, String remark) throws ExistException;
    public boolean allocateRoom(Long reservationId) throws ExistException;
    public boolean confirmReservation(Long reservationId) throws ExistException;
    public void editReservation(Long staffId, Long reservationId, String hotelName, String roomType, Integer quantity, Calendar startDate, Calendar endDate) throws ExistException;
    public void cancelReservation(Long reservationId) throws ExistException;
    //public void sendEmail(String customerEmail) throws ExistException;
}
 