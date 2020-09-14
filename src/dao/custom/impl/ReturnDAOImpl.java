package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ReturnDAO;
import entity.Extras;
import entity.Member;
import entity.Return;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReturnDAOImpl implements ReturnDAO {
    @Override
    public List<Return> findAll() throws Exception {
        List<Return> returns = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM `Return`");
        while (resultSet.next()) {
            returns.add(new Return(resultSet.getString(1),resultSet.getDate(2)));
        }
        return returns;
    }

    @Override
    public Return find(String pk) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM `Return` WHERE borrow_id=?", pk);
        if (resultSet.next()) {
            return new Return(resultSet.getString(1),resultSet.getDate(2));
        }
        return null;
    }

    @Override
    public boolean save(Return entity) throws Exception {
        boolean result = CrudUtil.execute("INSERT INTO `Return` VALUES(?,?)", entity.getBorrow_id(), entity.getDate());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Return entity) throws Exception {
        boolean result = CrudUtil.execute("UPDATE `Return` SET date=? WHERE borrow_id=?",entity.getDate(), entity.getBorrow_id());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String pk) throws Exception {
        boolean result = CrudUtil.execute("DELETE FROM `Return` WHERE borrow_id=?", pk);
        if (result) {
            return true;
        }
        return false;
    }

}
