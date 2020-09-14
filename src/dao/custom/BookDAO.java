package dao.custom;

import dao.CrudDAO;
import entity.Book;

public interface BookDAO extends CrudDAO<Book,String> {
    String getLastBookId() throws Exception;
}
