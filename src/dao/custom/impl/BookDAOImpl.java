package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.BookDAO;
import entity.Book;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public List<Book> findAll() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Book");
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            books.add(new Book(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
        }
        return books;
    }

    @Override
    public Book find(String pk) throws Exception {
        ResultSet resulSet = CrudUtil.execute("SELECT * FROM Book WHERE isbn=?", pk);
        if (resulSet.next()) {
            return new Book(resulSet.getString(1),resulSet.getString(2),resulSet.getString(3),resulSet.getString(4));
        }
        return null;
    }

    @Override
    public boolean save(Book entity) throws Exception {
        boolean result = CrudUtil.execute("INSERT INTO Book VALUES(?,?,?,?)", entity.getIsbn(), entity.getTitle(), entity.getAuthor(), entity.getEdition());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Book entity) throws Exception {
        boolean result = CrudUtil.execute("UPDATE Book SET title=?,author=?,edition=? WHERE isbn=?", entity.getTitle(), entity.getAuthor(), entity.getEdition(), entity.getIsbn());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String pk) throws Exception {
        boolean result = CrudUtil.execute("DELETE FROM Book WHERE isbn=?", pk);
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public String getLastBookId() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT isbn FROM Book ORDER BY isbn DESC LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
