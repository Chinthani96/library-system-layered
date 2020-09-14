package dao.custom;

import dao.SuperDAO;
import util.MembersTM;
import util.ReturnTM;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<MembersTM> getAllMembersWithBorrowedAndNotReturnedBooks() throws Exception;
    List<ReturnTM> getAllReturnDetails(String memberId) throws Exception;
    List<String> getMemberIds() throws Exception;
}
