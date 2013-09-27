/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Hotel;
import entity.LogEntry;
import entity.Logbook;
import exception.ExistException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Yang Zhennan
 */
@Stateless
public class LogbookBean implements LogbookBeanRemote {

    @PersistenceContext()
    EntityManager em;
    Hotel hotel;
    Logbook logbook;
    LogEntry logEntry;

    public LogbookBean() {
    }

    @Override
    public void addLogbook(String hotelName, String description) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        logbook = hotel.getLogbook();
        if (logbook != null) {
            throw new ExistException("LOGBOOK ALREADY EXISTS.");
        }
        logbook = new Logbook();
        logbook.create(description);
        logbook.setHotel(hotel);
        hotel.setLogbook(logbook);
        em.persist(logbook);
    }

    @Override
    public void removeLogbook(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        logbook = hotel.getLogbook();
        if (logbook == null) {
            throw new ExistException("LOGBOOK NOT EXIST.");
        }
        hotel.setLogbook(null);
        em.remove(logbook);
        em.flush();
    }
    /*
    @Override
    public void createLogEntry(String hotelName, LogEntry newEntry) throws ExistException{
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        logbook = hotel.getLogbook();
        if (logbook == null) {
            throw new ExistException("LOGBOOK NOT EXIST.");
        }
        newEntry.setLogbook(logbook);
        logbook.getLogEntries().add(newEntry);
        //logEntry=newEntry;
        em.persist(newEntry);
    }
    */ 
    @Override
    public Long addLogEntry(String hotelName,String description, String contactPerson, String contactNumber, String category, String status, Calendar dateTime) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        logbook = hotel.getLogbook();
        if (logbook == null) {
            throw new ExistException("LOGBOOK NOT EXIST.");
        }
        logEntry = new LogEntry();
        logEntry.create(description, contactPerson, contactNumber, category, category);
        logEntry.setDateTime(dateTime);
        logEntry.setLogbook(logbook);
        logbook.getLogEntries().add(logEntry);
        //logbook.setLogEntries(logbook.getLogEntries());
        em.persist(logEntry);
        return logEntry.getId();
    }

    @Override
    public void editLogEntry(String hotelName, Long entryId, String description, String contactPerson, String contactNumber, String category, String status) throws ExistException {
        logEntry = em.find(LogEntry.class, entryId);
        if (logEntry == null) {
            throw new ExistException("LOGENTRY NOT EXIST.");
        }
        logEntry.setDescription(description);
        logEntry.setContactPerson(contactPerson);
        logEntry.setContactNumber(contactNumber);
        logEntry.setCategory(category);
        logEntry.setStatus(status);
        logEntry.setDateTime(Calendar.getInstance());
        em.flush();
    }

    @Override
    public void removeLogEntry(Long entryId) throws ExistException {
        logEntry = em.find(LogEntry.class, entryId);
        if (logEntry == null) {
            throw new ExistException("LOGENTRY NOT EXIST.");
        }
        logbook=logEntry.getLogbook();
        logbook.getLogEntries().remove(logEntry);
        logbook.setLogEntries(logbook.getLogEntries());
        em.remove(logEntry);
        em.flush();
    }

    @Override
    public Collection<Logbook> getLogbooks() {
        Query q = em.createQuery("SELECT l FROM Logbook l");
        Collection logbooks = new ArrayList();
        for (Object o : q.getResultList()) {
            Logbook l = (Logbook) o;
            logbooks.add(l);
        }
        return logbooks;
    }

    @Override
    public List<LogEntry> getLogEntries(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        //System.out.println(hotel.getName());
        logbook = hotel.getLogbook();
        if (logbook == null) {
            throw new ExistException("LOGBOOK NOT EXIST.");
        }
        logbook.getLogEntries().size();
        return (List)logbook.getLogEntries();
    }
}
