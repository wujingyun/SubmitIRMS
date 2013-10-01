/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.util.Date;

/**
 *
 * @author Yang Zhennan
 */
public class OverbookLimit {
    private Date date;
    private Integer limit;
    
    public OverbookLimit(Date date, Integer limit){
        this.date=date;
        this.limit=limit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date){
        this.date=date;
    }
    public Integer getLimit() {
        return limit;
    }
    
    public void setLimit(Integer limit){
        this.limit=limit;
    }
}
