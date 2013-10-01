/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;


import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import exception.ExistException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@ViewScoped
public class SearchManagedBean {

  /*   @EJB
    private EmployeeSessionBean em;
    private String searchName;
    private List<EmployeeEntity> employees = new ArrayList<EmployeeEntity>();
    
    @EJB
    EmployeeSessionBean employee;
    
    /** Creates a new instance of SearchManagedBean */
  /*  public SearchManagedBean() {
    }
    
   public List<String> complete(String query) throws ExistException {  
        List<String> results = new ArrayList<String>();  
        
        List<EmployeeEntity> employeeList= employee.getAllEmployees();
        
        for (Object o:employeeList) {  
            EmployeeEntity emp = (EmployeeEntity)o;
            if (emp.getEmployeeName().startsWith(query)){
                results.add(emp.getEmployeeName());  
            }
        }  
        return results;
    }

    public EmployeeSessionBean getEm() {
        return em;
    }

    public void setEm(EmployeeSessionBean em) {
        this.em = em;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
    
    public void searchByName() throws IOException, ExistException{
        setEmployees(em.getEmployeeByName(searchName));
        System.out.println(searchName);
        System.out.println(employees.get(0).getEmployeeName());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("Employees", employees);
        FacesContext.getCurrentInstance().getExternalContext().redirect("SearchResult.xhtml");
    }

    public List<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeEntity> employees) {
        this.employees = employees;
    }
     
   /*
    public SearchResultManagedBean() {
    }

    public List<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeEntity> employees) {
        this.employees = employees;
    }

    public void initView(PhaseEvent event) {
        employees = (List<EmployeeEntity>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("Employees");
    }
    */
}
