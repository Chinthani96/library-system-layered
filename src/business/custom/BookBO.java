package business.custom;

import business.SuperBO;
import util.BooksTM;

import java.util.List;

public interface BookBO extends SuperBO {
    String generateNewBookId() throws Exception;
    List<BooksTM> getAllBooks() throws Exception;
    boolean saveBook(BooksTM book) throws Exception;
    boolean updateBook(BooksTM book) throws Exception;
    boolean deleteBook(String isbn)throws Exception;
}
