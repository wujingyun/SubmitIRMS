/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.RoomService;
import entity.RoomServiceOrder;
import exception.ExistException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface RoomServiceBeanRemote {
    public void addRoomService(String hotelName, String name, String description, double price)throws ExistException;
    public void editRoomService(String hotelName, String name, String description, double price)throws ExistException;
    public void removeRoomService(String hotelName, String name) throws ExistException;
    public List<RoomService> getRoomServices(String hotelName) throws ExistException;
    public List<RoomServiceOrder> getRoomServiceOrders(String hotelName) throws ExistException;
    public void createRoomServiceOrder(Long accommodationBillId, List<RoomService> selectedServices, String hotelName) throws ExistException;
    public void addItemToOrder(Long serviceOrderId, String hotelName, String serviceName) throws ExistException;
    public void cancelRoomServiceOrder(Long serviceOrderId) throws ExistException;
}
