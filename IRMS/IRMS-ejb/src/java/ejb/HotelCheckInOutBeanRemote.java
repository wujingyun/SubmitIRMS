/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.AccommodationBill;
import entity.DiscountScheme;
import exception.ExistException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface HotelCheckInOutBeanRemote {
    public List<AccommodationBill> getAccommodationBills(String hotelName) throws ExistException;
    public AccommodationBill createAccommodationBill(Long reservationId, String hotelName) throws ExistException;
    public void removeAccommodationBill(Long accommodationBillId) throws ExistException;
    public void addCallCharge(Long accommodationBillId, double callCharge) throws ExistException;
    public void editCallCharge(Long accommodationBillId, double newCharge) throws ExistException;
    public void addMiniBarItemCharge(Long accommodationBillId, String itemName, Integer quantity) throws ExistException;
    public boolean removeMiniBarItemCharge(Long accommodationBillId, String itemName) throws ExistException;
    public void addRoomServiceOrder(Long accommodationBillId, Long roomServiceOrderId) throws ExistException;
    public void removeRoomServiceOrder(Long accommodationBillId, Long roomServiceOrderId) throws ExistException;
    public void addIncidentalCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException;
    public boolean removeIncidentalCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException;
    public double addDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException;
    public void removeDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException;
    public double tallyBill(Long accommodationBillId) throws ExistException;
    public void updatePaymentStatus(Long accommodationBillId, String paymentStatus) throws ExistException;
    public void updateRoomAvailabilityStatus(Long accommodationBillId) throws ExistException;
    public double getRoomServiceTotal(Long accommodationBillId) throws ExistException;
    public double getIncidentalTotal(Long accommodationBillId) throws ExistException;
    public double getMiniBarTotal(Long accommodationBillId) throws ExistException;
    public List<String> getDiscountSchemes(String hotelName) throws ExistException;
    public void checkIn(Long customerId, Long reservationId) throws ExistException;
    public void checkOut(Long accommodationBillId) throws ExistException;
}
