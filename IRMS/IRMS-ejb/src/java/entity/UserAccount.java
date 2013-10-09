/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author WU JINGYUN
 */
@Entity
public class UserAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Length(min = 2, max = 32)
    private String userName;
    @NotNull
    @Length(min = 6, max = 32)
    private String password;
    private int logginAttemp;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar last_attemp;

    public void setLast_attemp(Calendar last_attemp) {
        this.last_attemp = last_attemp;
    }

    public Calendar getLast_attemp() {
        return last_attemp;
    }

    public void setLogginAttemp(int logginAttemp) {
        this.logginAttemp = logginAttemp;
    }

    public int getLogginAttemp() {
        return logginAttemp;
    }
//    @ManyToOne(targetEntity=UserRole.class)  
    //   @JoinColumn(name="role_id", referencedColumnName="id") 
    //public Collection<UserRole> userrole = new ArrayList<UserRole>();
    private long userrole;
    @OneToOne(cascade = {CascadeType.ALL})
    private UserContact contact;
    @OneToMany(mappedBy = "useraccount")
    private Collection<UserLog> UserLog = new ArrayList();

    public Collection<UserLog> getUserLog() {
        return UserLog;
    }

    public void setUserLog(Collection<UserLog> UserLog) {
        this.UserLog = UserLog;
    }
    private String division;
    private Boolean active;

    public void create(String userName, String password, long userrole, String division, Boolean active) {
        this.setUserName(userName);
        this.setPassword(password);
        this.setDivision(division);
        this.setActive(active);
        this.setUserrole(userrole);
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public UserContact getContact() {
        return contact;
    }

    public void setContact(UserContact contact) {
        this.contact = contact;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public long getUserrole() {
        return userrole;
    }

    public void setUserrole(long userrole) {
        this.userrole = userrole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
