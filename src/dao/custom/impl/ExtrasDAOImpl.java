package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ExtrasDAO;
import entity.Extras;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExtrasDAOImpl implements ExtrasDAO {
    @Override
    public List<Extras> findAll() throws Exception {
        List<Extras> extras = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Extras");
        while (resultSet.next()) {
            extras.add(new Extras(resultSet.getInt(1),resultSet.getBigDecimal(2)));
        }
        return extras;
    }

    @Override
    public Extras find(String pk) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Extras WHERE days=?", pk);
        if (rst.next()) {
            return new Extras(rst.getInt(1),rst.getBigDecimal(2));
        }
        return null;
    }

    @Override
    public boolean save(Extras entity) throws Exception {
        boolean result = CrudUtil.execute("INSERT INTO Extras VALUES(?,?)", entity.getDays(), entity.getFee());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Extras entity) throws Exception {
        boolean result = CrudUtil.execute("UPDATE Extras SET fee=? WHERE days=?",entity.getFee(), entity.getDays());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String pk) throws Exception {
        boolean result = CrudUtil.execute("DELETE FROM Extras WHERE days=?", pk);
        if (result) {
            return true;
        }
        return false;
    }
}
