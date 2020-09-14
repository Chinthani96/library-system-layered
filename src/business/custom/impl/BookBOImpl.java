package business.custom.impl;

import business.custom.BookBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.BookDAO;
import entity.Book;
import util.BooksTM;

import java.util.ArrayList;
import java.util.List;

public class BookBOImpl implements BookBO {

    private static BookDAO bookDAO = DAOFactory.getInstance().getDAO(DAOType.BOOK);

    @Override
    public String generateNewBookId() throws Exception {
        String lastBookId = bookDAO.getLastBookId();
        if (lastBookId == null) {
            return "B001";
        }

        int lastNumber = Integer.parseInt(lastBookId.substring(1, 4));
        if (lastNumber < 10) {
            lastNumber++;
            return "B00" + lastNumber;
        } else if (lastNumber < 100) {
            lastNumber++;
            return "B0" + lastNumber;
        } else {
            lastNumber++;
            return "B" + lastNumber;
        }
    }

    public List<BooksTM> getAllBooks() throws Exception {
        List<Book> allBooks = bookDAO.findAll();
        List<BooksTM> books = new ArrayList<>();
        for (Book book : allBooks) {
            books.add(new BooksTM(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getEdition()));
        }
        return books;
    }

    @Override
    public boolean saveBook(BooksTM book) throws Exception {
        return bookDAO.save(new Book(book.getISBN(), book.getTitle(), book.getAuthor(), book.getEdition()));

    }

    @Override
    public boolean updateBook(BooksTM book) throws Exception {
        return bookDAO.update(new Book(book.getISBN(), book.getTitle(), book.getAuthor(), book.getEdition()));
    }

    @Override
    public boolean deleteBook(String isbn) throws Exception {
        return bookDAO.delete(isbn);
    }
}
