/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.FbBanquet;
import entity.FbBanquetReservation;
import entity.FbContract;
import entity.FbRequest;
import exception.ExistException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Remote;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface BanquetSessionBeanRemote {

  

    public Collection<FbRequest> getRequestList();

    public Collection<FbBanquetReservation> getReservationList();

    public Collection<FbBanquet> getBanquetList();

    public void createBanquet(String banquetHallName, String location, int capacity);

    public void editBanquet(String banquetHallName, String location, int capacity);

    public void deleteBanquet(String banquetHallName) throws ExistException;

   

    public void createRequest(String venue, String customerName, int capacity, Date bookingDate, int duration, String contact, String email);

  

    public void createReservation(String banquetName, Date dateReserved, Date dateEnded, String purpose, String contact, String customerName, String email) throws ExistException;

   
    public void deleteReservation(Long id);

    public void deleteRequest(Long id);

    public Collection<FbBanquetReservation> getOneBanquetReservationList(String banquetHallName);
}
