/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.InternalRoomRequest;
import entity.RoomReservation;
import exception.ExistException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface HotelReservationBeanRemote {
    public List<RoomReservation> getHotelReservations(String hotelName) throws ExistException;
    public List<RoomReservation> getRoomReservations(String hotelName, Integer roomNumber) throws ExistException;
    public Collection<InternalRoomRequest> getInternalRoomRequests() throws ExistException;
    //public boolean checkOverbookLimit(String hotelName, Integer quantity, Date startDate, Date endDate) throws ExistException;
    public boolean checkAvailability(String hotelName, String roomType, Integer quantity, Date startDate, Date endDate) throws ExistException;
    public RoomReservation makeReservation(Long customerId, String hotelName, String roomType, Integer quantity, Date startDate, Date endDate, String remark) throws ExistException;
    public boolean allocateRoom(Long reservationId) throws ExistException;
    public boolean confirmReservation(Long reservationId) throws ExistException;
    public void editReservation(Long reservationId, String hotelName, String roomType, Integer quantity, Date startDate, Date endDate, String remark, String status, double total, String paymentStatus) throws ExistException;
    public void cancelReservation(Long reservationId) throws ExistException;
    //public void sendEmail(String customerEmail) throws ExistException;
}
 