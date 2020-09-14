package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import util.MembersTM;
import util.ReturnTM;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<MembersTM> getAllMembersWithBorrowedAndNotReturnedBooks() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT M.id,M.name,M.phone_num,BO.borrow_id FROM Member M INNER JOIN Borrow BO ON M.id = BO.m_id WHERE BO.borrow_id NOT IN (SELECT borrow_id FROM `return`)");
        List<MembersTM> members = new ArrayList<>();
        while (resultSet.next()) {
            members.add(new MembersTM(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3)));
        }
        return members;
    }

    @Override
    public List<ReturnTM> getAllReturnDetails(String memberID) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT B.isbn,B.title,BO.borrow_id,BO.m_id FROM Book B INNER JOIN Borrow BO on B.isbn = BO.isbn where BO.borrow_id not in (select borrow_id from `return`)");
        List<ReturnTM> returnDetails = new ArrayList<>();
        while (rst.next()) {
            returnDetails.add(new ReturnTM(rst.getString(1),rst.getString(2),rst.getString(3)));
        }
        return returnDetails;
    }

    public List<String> getMemberIds() throws Exception{
        ResultSet rst = CrudUtil.execute("SELECT B.isbn,B.title,BO.borrow_id,BO.m_id FROM Book B INNER JOIN Borrow BO on B.isbn = BO.isbn where BO.borrow_id not in (select borrow_id from `return`)");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(4));
        }
        return ids;
    }
}
