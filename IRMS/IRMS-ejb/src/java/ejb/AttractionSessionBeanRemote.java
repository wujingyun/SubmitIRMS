/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Attraction;
import entity.AttractionPass;
import entity.AttractionTicket;
import entity.Customer;
import exception.ExistException;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author WU JINGYUN
 */
@Remote
public interface AttractionSessionBeanRemote {

    public void createAttraction(String name, int maxQuota, String operatingHours, String location, String descriptions) throws ExistException;

    public void editAttraction(String name, int maxQuota, String operatingHours, String location, String descriptions) throws ExistException;

    public void deleteAttraction(String name) throws ExistException;

    public List<AttractionPass> getPass(String name);

    public void createPass(String attractionName, String name, double price, String type, String remarks) throws ExistException;

    public void createTicket(String attractionName, String name, double price, String type, String remarks) throws ExistException;

    public List<AttractionTicket> getTicket(String name);

    public List<Attraction> getAttractions();

    public void editTicket(String name, double price, String type, String remarks) throws ExistException;

    public void editPass(String name, double price, String type, String remarks) throws ExistException;

    public void deletePass(String attractionName, String name) throws ExistException;

    public void deleteTicket(String attractionName, String name) throws ExistException;
    
     public void buyPass(Long customerId,int quantity,  String attractionPassId) throws ExistException;
   public void purchaseTicket(Long customerId, int quantity, String ticketId,Calendar attendDate) throws ExistException;
}
