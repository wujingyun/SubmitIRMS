/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.util.Calendar;

/**
 *
 * @author Yang Zhennan
 */
public class OverbookLimit {
    private Calendar date;
    private Integer limit;
    
    public OverbookLimit(Calendar date, Integer limit){
        this.date=date;
        this.limit=limit;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date){
        this.date=date;
    }
    public Integer getLimit() {
        return limit;
    }
    
    public void setLimit(Integer limit){
        this.limit=limit;
    }
}
