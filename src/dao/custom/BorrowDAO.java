package dao.custom;

import dao.CrudDAO;
import entity.Borrow;

public interface BorrowDAO extends CrudDAO<Borrow,String> {
    String getLastBorrowId() throws Exception;
}
