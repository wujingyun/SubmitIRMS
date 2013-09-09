/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String telNumber;
    private String description;
    @OneToMany(mappedBy="hotel")
    private Collection<Room> rooms=new ArrayList();
    @OneToMany(mappedBy="hotel")
    private Collection<RoomService> roomServices=new ArrayList();
    @OneToMany(mappedBy="hotel")
    private Collection<MiniBarItem> miniBarItems=new ArrayList();
    @OneToOne
    private Logbook logbook;
    @OneToOne
    private ComplaintRegister complaintRegister;
    @OneToMany(mappedBy="hotel")
    private Collection<ConciergeOrder> conciergeOrders=new ArrayList();
    @OneToMany(mappedBy="hotel")
    private Collection<DiscountScheme> discountSchemes=new ArrayList();

    public Hotel() {
    }
    
    public void create(String name, String address, String telNumber){
        this.setName(name);
        this.setAddress(address);
        this.setTelNumber(telNumber);
    }
    
    public Room findRoom(Integer roomNumber){
        Room room;
        ArrayList allRooms=(ArrayList)this.getRooms();
        for(int i=0; i<allRooms.size();i++){
            room=(Room)allRooms.get(i);
            if(room.getRoomNumber()==roomNumber)
                return room;
        }
        return null;
    }
    public MiniBarItem findMiniBarItem(String name){
        MiniBarItem item;
        ArrayList allItems=(ArrayList)this.getMiniBarItems();
        for(int i=0; i<allItems.size();i++){
            item=(MiniBarItem)allItems.get(i);
            if(item.getName().equals(name))
                return item;
        }
        return null;
    }
    
    public DiscountScheme findDiscountScheme(String name){
        DiscountScheme discountScheme;
        ArrayList allDiscountSchemes=(ArrayList) this.getDiscountSchemes();
        for(int i=0; i<allDiscountSchemes.size(); i++){
            discountScheme=(DiscountScheme)allDiscountSchemes.get(i);
            if(discountScheme.getName().equals(name));
                return discountScheme;
        }
        return null;
    }
    
    public RoomService findRoomService(String name){
        RoomService roomService;
        ArrayList allRoomServices=(ArrayList) this.getRoomServices();
        for(int i=0; i<allRoomServices.size(); i++){
            roomService=(RoomService)allRoomServices.get(i);
            if(roomService.getName().equals(name))
                return roomService;
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
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Hotel[ id=" + id + " ]";
    }
    
}
