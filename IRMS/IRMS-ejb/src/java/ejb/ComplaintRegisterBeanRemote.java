/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.LogEntry;
import entity.Logbook;
import exception.ExistException;
import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface ComplaintRegisterBeanRemote {
    public void addComplaintRegister(String hotelName, String description) throws ExistException;
    public void removeComplaintRegister(String hotelName) throws ExistException;
    public void addComplaintEntry(String hotelName, String description, String contactPerson, String contactNumber, String category) throws ExistException;
    public void editComplaintEntry(String hotelName, Long entryId, String description, String contactPerson, String contactNumber, String category, String status)throws ExistException;
    public void removeComplaintEntry(Long logEntryId) throws ExistException;
    public Collection<Logbook> getComplaintRegisters();
    public Collection<LogEntry> getLogEntries(String hotelName) throws ExistException;
}
