/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Yang Zhennan
 */
@Entity
public class Logbook implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy="logbook")
    private Hotel hotel;
    private String description;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateCreated;
    @OneToMany(mappedBy="logbook")
    private Collection<LogEntry> logEntries=new ArrayList();

    public Logbook() {
    }
    
    public void create(String description){
        this.setDescription(description);
        this.setDateCreated(Calendar.getInstance());
    }
    
    public LogEntry findEntry(Long entryId){
        LogEntry entry;
        ArrayList entries=(ArrayList) this.getLogEntries();
        for(int i=0; i<entries.size(); i++){
            entry=(LogEntry) entries.get(i);
            if(entry.getId()==entryId)
                return entry; 
        }
        return null;
    }
    
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Collection<LogEntry> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(Collection<LogEntry> logEntries) {
        this.logEntries = logEntries;
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
        if (!(object instanceof Logbook)) {
            return false;
        }
        Logbook other = (Logbook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Logbook[ id=" + id + " ]";
    }
    
}
