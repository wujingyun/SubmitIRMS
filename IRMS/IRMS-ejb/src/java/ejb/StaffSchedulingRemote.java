/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Hotel;
import entity.Staff;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author wangxiahao
 */
@Remote
public interface StaffSchedulingRemote {
     public List<Staff> getStaffList();
      public void  setShifts(Long staffID,String staffRole,String shift,String floorLevel,String day);
      public List<Hotel> getHotel();
       public void addStaff(String name, String hotelName);
       public List<Staff> getStaff(String hotelName);
       
}
