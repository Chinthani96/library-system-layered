package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.UserDAO;
import entity.Return;
import entity.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> findAll() throws Exception {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM User");
        while (resultSet.next()) {
            users.add(new User(resultSet.getString(1),resultSet.getString(2)));
        }
        return users;
    }

    @Override
    public User find(String pk) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM User WHERE borrow_id=?", pk);
        if (resultSet.next()) {
            return new User(resultSet.getString(1),resultSet.getString(2));
        }
        return null;
    }

    @Override
    public boolean save(User entity) throws Exception {
        boolean result = CrudUtil.execute("INSERT INTO User VALUES(?,?)", entity.getUser_pk().getName(), entity.getUser_pk().getPassword());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(User entity) throws Exception {
        boolean result = CrudUtil.execute("UPDATE User SET password=? WHERE name=?",entity.getUser_pk().getPassword(), entity.getUser_pk().getName());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String pk) throws Exception {
        boolean result = CrudUtil.execute("DELETE FROM User WHERE name=?", pk);
        if (result) {
            return true;
        }
        return false;
    }
}
