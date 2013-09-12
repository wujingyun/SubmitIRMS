package session.stateless;

import entity.Book;
import java.util.List;
import javax.ejb.Local;



@Local
public interface BookSessionBeanLocal
{
    void createBook(Book book);

    List<Book> readBooks();
}