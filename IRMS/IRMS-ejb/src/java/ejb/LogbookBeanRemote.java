/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.LogEntry;
import entity.Logbook;
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
public interface LogbookBeanRemote {
    public void addLogbook(String hotelName, String description) throws ExistException;
    public void removeLogbook(String hotelName) throws ExistException;
    //public void createLogEntry(String hotelName, LogEntry newEntry) throws ExistException;
    public Long addLogEntry(String hotelName,String description, String contactPerson, String contactNumber, String category, String status, Calendar dateTime) throws ExistException;
    public void editLogEntry(String hotelName, Long entryId, String description, String contactPerson, String contactNumber, String category, String status)throws ExistException;
    public void removeLogEntry(Long logEntryId) throws ExistException;
    public Collection<Logbook> getLogbooks(); 
    public List<LogEntry> getLogEntries(String hotelName) throws ExistException;
}
