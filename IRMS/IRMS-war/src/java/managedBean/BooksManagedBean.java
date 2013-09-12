package managedBean;

import entity.Book;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import session.stateless.BookSessionBeanLocal;



@ManagedBean
@ViewScoped

public class BooksManagedBean implements Serializable
{
    @EJB
    private BookSessionBeanLocal bookSessionBeanLocal;
    
    
    
    private List<Book> books;
    private Book newBook;
    
    
    
    public BooksManagedBean()
    {
        newBook = new Book();
    }
    
    
    
    @PostConstruct
    public void init()
    {
        books = bookSessionBeanLocal.readBooks();
    }
    
    
    
    public void createBook(ActionEvent event)
    {
        try
        {
            bookSessionBeanLocal.createBook(newBook);            
            newBook.setAuthor("XXX");
            books.add(newBook);
            newBook = new Book();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  "New book created successfully", ""));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  "An error has occurred while creating the new book: " + ex.getMessage(), ""));
        }
                
    }

    
    
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Book getNewBook() {
        return newBook;
    }

    public void setNewBook(Book newBook) {
        this.newBook = newBook;
    }
}