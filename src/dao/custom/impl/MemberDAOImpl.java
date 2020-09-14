package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.MemberDAO;
import entity.Book;
import entity.Borrow;
import entity.Member;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {
    @Override
    public List<Member> findAll() throws Exception {
        List<Member> members = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Member");
        while (resultSet.next()) {
            members.add(new Member(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)));
        }
        return members;
    }

    @Override
    public Member find(String pk) throws Exception {
        ResultSet resulSet = CrudUtil.execute("SELECT * FROM Member WHERE id=?", pk);
        if (resulSet.next()) {
            return new Member(resulSet.getString(1),resulSet.getString(2),resulSet.getString(3));
        }
        return null;
    }

    @Override
    public boolean save(Member entity) throws Exception {
        boolean result = CrudUtil.execute("INSERT INTO Member VALUES(?,?,?)", entity.getId(), entity.getName(), entity.getPhone_num());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Member entity) throws Exception {
        boolean result = CrudUtil.execute("UPDATE Member SET name=?,phone_num=? WHERE id=?", entity.getName(), entity.getPhone_num(),entity.getId());
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String pk) throws Exception {
        boolean result = CrudUtil.execute("DELETE FROM Member WHERE id=?", pk);
        if (result) {
            return true;
        }
        return false;
    }

    @Override
    public String getLastMemberId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT id FROM Member ORDER BY id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
