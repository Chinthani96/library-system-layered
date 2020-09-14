package business.custom.impl;

import business.custom.BorrowBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.BorrowDAO;
import entity.Borrow;


import java.sql.Date;

public class BorrowBOImpl implements BorrowBO {

    private static BorrowDAO borrowDAO = DAOFactory.getInstance().getDAO(DAOType.BORROW);

    @Override
    public String generateNewBorrowId() throws Exception {
        String lastId = borrowDAO.getLastBorrowId();
        int val = Integer.parseInt(lastId.substring(1,4));
        val++;

        if(val<10){
            return "BO" + val;
        }
        else if(val<100){
            return "B0"+val;
        }
        else{
            return "B"+val;
        }
    }

    @Override
    public boolean saveBorrowDetail(String id, String m_id, String isbn, String date) throws Exception {
        return borrowDAO.save(new Borrow(id,m_id,isbn,Date.valueOf(date)));
    }

    @Override
    public boolean updateBorrowDetail(String id, String m_id, String isbn, String date) throws Exception {
        return borrowDAO.update(new Borrow(id,m_id,isbn,Date.valueOf(date)));
    }
}
