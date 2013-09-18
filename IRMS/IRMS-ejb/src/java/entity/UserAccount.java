/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Hanbin
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

    @ManyToOne(targetEntity=UserRole.class)  
 //   @JoinColumn(name="role_id", referencedColumnName="id") 
    public Collection<UserRole> userrole = new ArrayList<UserRole>();
 
     @OneToOne(cascade = {CascadeType.ALL})
    private UserContact contact; 

  
    public void create(String userName, String password, Collection userrole) {
        this.setUserName(userName);
        this.setPassword(password);
        this.setUserrole(userrole);
        
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }  public UserContact getContact() {
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


    public Collection<UserRole> getUserrole() {
        return userrole;
    }

    public void setUserrole(Collection<UserRole> userrole) {
        this.userrole = userrole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    
  
    
}
