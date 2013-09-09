/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.ComplaintEntry;
import entity.ComplaintRegister;
import exception.ExistException;
import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author Yang Zhennan
 */
@Remote
public interface ComplaintRegisterBeanRemote {
    public void addComplaintRegister(String hotelName) throws ExistException;
    public void removeComplaintRegister(String hotelName) throws ExistException;
    public void addComplaintEntry(String hotelName, String customerName, Integer roomNumber, String contact, String description, String status) throws ExistException;
    public void editComplaintEntry(String hotelName, String customerName, Integer roomNumber, String contact, String description, String status)throws ExistException;
    public void removeComplaintEntry(Long complaintId) throws ExistException;
    public Collection<ComplaintRegister> getComplaintRegisters();
    public Collection<ComplaintEntry> getComplaintEntries(String hotelName) throws ExistException;
}
