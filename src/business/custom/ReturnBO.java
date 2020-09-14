package business.custom;

import business.SuperBO;
import util.MembersTM;
import util.ReturnTM;

import java.util.List;

public interface ReturnBO extends SuperBO {
    List<MembersTM> getAllMembersWithBorrowedAndNotReturnedBooks() throws Exception;
    List<ReturnTM> getAllReturnableBooksOFMember(String memberId) throws Exception;
    List<String> getAllIds() throws Exception;
}
