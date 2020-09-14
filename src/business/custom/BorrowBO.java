package business.custom;

import business.SuperBO;
import util.BorrowTM;

public interface BorrowBO extends SuperBO {
    String generateNewBorrowId() throws Exception;
    boolean saveBorrowDetail(String id, String m_id, String isbn, String date) throws Exception;
    boolean updateBorrowDetail(String id, String m_id, String isbn, String date) throws Exception;
}
