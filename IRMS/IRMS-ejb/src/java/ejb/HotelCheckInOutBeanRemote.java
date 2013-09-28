/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import exception.ExistException;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface HotelCheckInOutBeanRemote {
    public void createAccommodationBill(Long reservationId) throws ExistException;
    public void removeAccommodationBill(Long accommodationBillId) throws ExistException;
    public void addCallCharge(Long accommodationBillId, double callCharge) throws ExistException;
    public void editCallCharge(Long accommodationBillId, double newCharge) throws ExistException;
    public void addMiniBarItemCharge(Long accommodationBillId, String itemName, Integer quantity) throws ExistException;
    public boolean removeMiniBarItemCharge(Long accommodationBillId, String itemName) throws ExistException;
    public void addRoomServiceOrder(Long accommodationBillId, Long roomServiceOrderId) throws ExistException;
    public void removeRoomServiceOrder(Long accommodationBillId, Long roomServiceOrderId) throws ExistException;
    public void addIncidentalCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException;
    public boolean removeIncidentalCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException;
    public void addDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException;
    public void removeDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException;
    public double tallyBill(Long accommodationBillId) throws ExistException;
    public void checkIn(Long customerId, Long reservationId) throws ExistException;
    public void checkOut(Long accommodationBillId) throws ExistException;
}
