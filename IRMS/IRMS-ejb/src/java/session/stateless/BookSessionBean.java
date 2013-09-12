package session.stateless;

import entity.Book;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



@Stateless

public class BookSessionBean implements BookSessionBeanLocal
{
    @PersistenceContext
    private EntityManager em;

    
    
    @Override
    public void createBook(Book book)
    {
        em.persist(book);        
    }

    
    
    @Override
    public List<Book> readBooks()
    {
        Query query = em.createQuery("SELECT b FROM Book b");
        
        
        
        return query.getResultList();
    }
}