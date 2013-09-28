/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.ComplaintEntry;
import entity.ComplaintRegister;
import entity.Hotel;
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
public class ComplaintRegisterBean implements ComplaintRegisterBeanRemote {

    @PersistenceContext()
    EntityManager em;
    Hotel hotel;
    ComplaintRegister complaintRegister;
    ComplaintEntry complaintEntry;

    public ComplaintRegisterBean() {
    }

    @Override
    public void addComplaintRegister(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        complaintRegister = hotel.getComplaintRegister();
        if (complaintRegister != null) {
            throw new ExistException("COMPLAINT REGISTER ALREADY EXISTS.");
        }
        complaintRegister = new ComplaintRegister();
        complaintRegister.create();
        complaintRegister.setHotel(hotel);
        hotel.setComplaintRegister(complaintRegister);
        em.persist(complaintRegister);
    }

    @Override
    public void removeComplaintRegister(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        complaintRegister = hotel.getComplaintRegister();
        if (complaintRegister == null) {
            throw new ExistException("COMPLAINT REGISTER NOT EXIST.");
        }
        hotel.setComplaintRegister(null);
        em.remove(complaintRegister);
        em.flush();
    }

    @Override
    public Long addComplaintEntry(String hotelName, String customerName, Integer roomNumber, String contact, String description, String status, Calendar dateTime) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        complaintRegister = hotel.getComplaintRegister();
        if (complaintRegister == null) {
            throw new ExistException("COMPLAINT REGISTER NOT EXIST.");
        }
        complaintEntry = new ComplaintEntry();
        complaintEntry.create(customerName, roomNumber, contact, description, status);
        complaintEntry.setDateTime(dateTime);
        complaintEntry.setComplaintRegister(complaintRegister);
        complaintRegister.getComplaintEntries().add(complaintEntry);
        //complaintRegister.setComplaintEntries(complaintRegister.getComplaintEntries());
        em.persist(complaintEntry);
        return complaintEntry.getId();
    }

    @Override
    public void editComplaintEntry(Long entryId, String customerName, Integer roomNumber, String contact, String description, String status) throws ExistException {
        complaintEntry = em.find(ComplaintEntry.class, entryId);
        if (complaintEntry == null) {
            throw new ExistException("LOGENTRY NOT EXIST.");
        }
        complaintEntry = new ComplaintEntry();
        complaintEntry.setCustomerName(customerName);
        complaintEntry.setRoomNumber(roomNumber);
        complaintEntry.setContact(contact);
        complaintEntry.setDescription(description);
        complaintEntry.setStatus(status);
        complaintEntry.setDateTime(Calendar.getInstance());
        em.flush();
    }

    @Override
    public void removeComplaintEntry(Long complaintId) throws ExistException {
        complaintEntry = em.find(ComplaintEntry.class, complaintId);
        if (complaintEntry == null) {
            throw new ExistException("COMPLAINT ENTRY NOT EXIST.");
        }
        complaintRegister=complaintEntry.getComplaintRegister();
        complaintRegister.getComplaintEntries().remove(complaintEntry);
        //complaintRegister.setComplaintEntries(complaintRegister.getComplaintEntries());
        em.remove(complaintEntry);
        em.flush();
    }

    @Override
    public Collection<ComplaintRegister> getComplaintRegisters() {
        Query q = em.createQuery("SELECT c FROM ComplaintRegister c");
        Collection complaintRegisters = new ArrayList();
        for (Object o : q.getResultList()) {
            ComplaintRegister c = (ComplaintRegister) o;
            complaintRegisters.add(c);
        }
        return complaintRegisters;
    }

    @Override
    public List<ComplaintEntry> getComplaintEntries(String hotelName) throws ExistException {
        hotel = em.find(Hotel.class, hotelName);
        if (hotel == null) {
            throw new ExistException("HOTEL NOT EXIST.");
        }
        complaintRegister = hotel.getComplaintRegister();
        if (complaintRegister == null) {
            throw new ExistException("COMPLAINT REGISTER NOT EXIST.");
        }
        complaintRegister.getComplaintEntries().size();
        return (List)complaintRegister.getComplaintEntries();
    }
}
