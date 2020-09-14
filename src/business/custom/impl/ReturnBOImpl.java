package business.custom.impl;

import business.custom.ReturnBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.QueryDAO;
import dao.custom.ReturnDAO;
import util.MembersTM;
import util.ReturnTM;

import java.util.List;

public class ReturnBOImpl implements ReturnBO {
    private static QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.QUERY);

    @Override
    public List<MembersTM> getAllMembersWithBorrowedAndNotReturnedBooks() throws Exception {
        return queryDAO.getAllMembersWithBorrowedAndNotReturnedBooks();
    }

    @Override
    public List<ReturnTM> getAllReturnableBooksOFMember(String memberId) throws Exception {
        return queryDAO.getAllReturnDetails(memberId);
    }

    @Override
    public List<String> getAllIds() throws Exception {
        return queryDAO.getMemberIds();
    }
}
