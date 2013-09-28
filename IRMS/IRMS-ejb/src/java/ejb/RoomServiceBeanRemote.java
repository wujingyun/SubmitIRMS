/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.RoomService;
import exception.ExistException;
import java.util.Collection;
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
    public Collection<RoomService> getRoomServices(String hotelName) throws ExistException;
    public void createRoomServiceOrder(Long accommodationBillId) throws ExistException;
    public void addItemToOrder(Long serviceOrderId, String hotelName, String serviceName) throws ExistException;
    public void cancelRoomServiceOrder(Long serviceOrderId) throws ExistException;
}
