/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author wangxiahao
 */
@ApplicationException(rollback=true)
public class MaxQuotaException extends Exception{
    private int exception =0;
    private static final int INSUFFICIENT_SPACE =1;
    public MaxQuotaException(){}
    
    public MaxQuotaException(int exception){
        setException(exception);
    }
    
    public int getException(){
        return exception;
    }
    
    public final void setException(int exception){
        this.exception =exception;
    }
    
    public static int getINSUFFICIENT_SPACE(){
        return INSUFFICIENT_SPACE;
    }
}
