package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.BorrowDAO;
import entity.Borrow;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAOImpl implements BorrowDAO {
    @Override
    public List<Borrow> findAll() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Borrow");
        List<Borrow> borrowList = new ArrayList<>();
        while (resultSet.next()) {
            borrowList.add(new Borrow(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4)));
        }
        return borrowList;
    }

    @Override
    public Borrow find(String pk) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Borrow WHERE borrow_id=?",pk);
        if (resultSet.next()) {
            return new Borrow(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4));
        }
        return null;
    }

    @Override
    public boolean save(Borrow entity) throws Exception {
        boolean result = CrudUtil.execute("INSERT INTO Borrow VALUES(?,?,?,?)", entity.getBorrow_id()
                , entity.getM_id(), entity.getIsbn(), entity.getDate());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Borrow entity) throws Exception {
        boolean result = CrudUtil.execute("UPDATE Borrow SET m_id=?,isbn=?,date=? WHERE borrow_id=?", entity.getM_id(), entity.getIsbn(), entity.getDate(), entity.getBorrow_id());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String pk) throws Exception {
        boolean result = CrudUtil.execute("DELETE FROM Borrow WHERE borrow_id=?", pk);
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public String getLastBorrowId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT borrow_id FROM Borrow ORDER BY borrow_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
