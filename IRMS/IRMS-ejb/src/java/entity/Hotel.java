/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import structure.OverbookLimit;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    private String address;
    private String telNumber;
    private String description;
    private Integer capacity;
    private double overbookRate;
    private ArrayList<OverbookLimit> overbookLimits=new ArrayList();
    @OneToMany(mappedBy ="hotel")
    private Collection<Room> rooms = new ArrayList();
    @OneToMany(mappedBy = "hotel")
    private Collection<RoomService> roomServices = new ArrayList();
    @OneToMany(mappedBy = "hotel")
    private Collection<MiniBarItem> miniBarItems = new ArrayList();
    @OneToOne
    private Logbook logbook;
    @OneToOne
    private ComplaintRegister complaintRegister;
    @OneToMany(mappedBy = "hotel")
    private Collection<ConciergeOrder> conciergeOrders = new ArrayList();
    @OneToMany(mappedBy = "hotel")
    private Collection<DiscountScheme> discountSchemes = new ArrayList();

    public Hotel() {
    }

    public void create(String name, String address, String telNumber, String description, Integer capacity, double overbookRate) {
        this.setName(name);
        this.setAddress(address);
        this.setTelNumber(telNumber);
        this.setDescription(description);
        this.setCapacity(capacity);
        this.setOverbookRate(overbookRate);
    }

    public Room findRoom(Integer roomNumber) {
        Room room;
        List<Room> allRooms = (List) this.getRooms();
        for (int i = 0; i < allRooms.size(); i++) {
            room = (Room) allRooms.get(i);
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    public double findRoomPrice(String roomType) {
        for (Object o : this.getRooms()) {
            Room r = (Room) o;
            if (roomType.equals(r.getType())) {
                return r.getRate();
            }
        }
        return -1;
    }

    public MiniBarItem findMiniBarItem(String name) {
        MiniBarItem item;
        List allItems = (List) this.getMiniBarItems();
        for (int i = 0; i < allItems.size(); i++) {
            item = (MiniBarItem) allItems.get(i);
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public DiscountScheme findDiscountScheme(String name) {
        DiscountScheme discountScheme;
        List allDiscountSchemes = (List) this.getDiscountSchemes();
        for (int i = 0; i < allDiscountSchemes.size(); i++) {
            discountScheme = (DiscountScheme) allDiscountSchemes.get(i);
            if (discountScheme.getName().equals(name))
                return discountScheme;
        }
        return null;
    }

    public RoomService findRoomService(String name) {
        RoomService roomService;
        List allRoomServices = (List) this.getRoomServices();
        for (int i = 0; i < allRoomServices.size(); i++) {
            roomService = (RoomService) allRoomServices.get(i);
            if (roomService.getName().equals(name)) {
                return roomService;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public double getOverbookRate() {
        return overbookRate;
    }

    public void setOverbookRate(double overbookRate) {
        this.overbookRate = overbookRate;
    }

    public ArrayList<OverbookLimit> getOverbookLimits() {
        return overbookLimits;
    }

    public void setOverbookLimits(ArrayList<OverbookLimit> overbookLimits) {
        this.overbookLimits = overbookLimits;
    }

    public Integer getOverbookLimit(Calendar date) {
        for (int i = 0; i < overbookLimits.size(); i++) {
            if (overbookLimits.get(i).getDate().equals(date)) {
                return overbookLimits.get(i).getLimit();
            }
        }
        return -1;
    }

    public void setOverbookLimit(Calendar date, Integer limit) {
        for (int i = 0; i < overbookLimits.size(); i++) {
            if (overbookLimits.get(i).getDate().equals(date)) {
                overbookLimits.get(i).setLimit(limit);
            }
        }
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public Collection<RoomService> getRoomServices() {
        return roomServices;
    }

    public void setRoomServices(Collection<RoomService> roomServices) {
        this.roomServices = roomServices;
    }

    public Collection<MiniBarItem> getMiniBarItems() {
        return miniBarItems;
    }

    public void setMiniBarItems(Collection<MiniBarItem> miniBarItems) {
        this.miniBarItems = miniBarItems;
    }

    public Logbook getLogbook() {
        return logbook;
    }

    public void setLogbook(Logbook logbook) {
        this.logbook = logbook;
    }

    public ComplaintRegister getComplaintRegister() {
        return complaintRegister;
    }

    public void setComplaintRegister(ComplaintRegister complaintRegister) {
        this.complaintRegister = complaintRegister;
    }

    public Collection<ConciergeOrder> getConciergeOrders() {
        return conciergeOrders;
    }

    public void setConciergeOrders(Collection<ConciergeOrder> conciergeOrders) {
        this.conciergeOrders = conciergeOrders;
    }

    public Collection<DiscountScheme> getDiscountSchemes() {
        return discountSchemes;
    }

    public void setDiscountSchemes(Collection<DiscountScheme> discountSchemes) {
        this.discountSchemes = discountSchemes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Hotel[ id=" + name + " ]";
    }
}
