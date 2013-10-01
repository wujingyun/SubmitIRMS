/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.ComplaintEntry;
import entity.ComplaintRegister;
import exception.ExistException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface ComplaintRegisterBeanRemote {
    
    public void addComplaintRegister(String hotelName) throws ExistException;
    public void removeComplaintRegister(String hotelName) throws ExistException;
    public Long addComplaintEntry(String hotelName, String customerName, Integer roomNumber, String contact, String description, String status, Calendar dateTime) throws ExistException;
    public void editComplaintEntry(Long entryId, String customerName, Integer roomNumber, String contact, String description, String status)throws ExistException;
    public void removeComplaintEntry(Long complaintId) throws ExistException;
    public Collection<ComplaintRegister> getComplaintRegisters();
    public List<ComplaintEntry> getComplaintEntries(String hotelName) throws ExistException;
}
