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
    public void addCallCharge(Long accommodationBillId, double callRate, double callTime) throws ExistException;
    public void removeCallCharge(Long accommodationBillId) throws ExistException;
    public void addMiniBarItemCharge(Long accommodationBillId, String itemName, Integer quantity) throws ExistException;
    public void removeMiniBarItemCharge(Long accommodationBillId, String itemName) throws ExistException;
    public void addRoomServiceOrder(Long accommodationBillId, Long roomServiceOrderId) throws ExistException;
    public void removeRoomServiceOrder(Long accommodationBillId, Long roomRerviceOrderId) throws ExistException;
    public void addOtherCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException;
    public void removeOtherCharge(Long accommodationBillId, String name, double charge, String description) throws ExistException;
    public void addDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException;
    public void removeDiscountScheme(Long accommodationBillId, String schemeName) throws ExistException;
    public double tallyBill(Long accommodationBillId) throws ExistException;
    public void checkIn(Long customerId, Long reservationId) throws ExistException;
    public void checkOut(Long accommodationBillId) throws ExistException;
}
