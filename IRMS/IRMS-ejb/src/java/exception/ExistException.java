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
@ApplicationException(rollback = true)
public class ExistException extends Exception{
    public ExistException(){}
    
    public ExistException(String message){
        super(message);
    }
}
