package dao;

import dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance(){
        return (daoFactory==null)?new DAOFactory():daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOType daoType){
        switch (daoType) {
            case BOOK:
                return (T) new BookDAOImpl();
            case MEMBER:
                return (T) new MemberDAOImpl();
            case BORROW:
                return (T) new BorrowDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            case EXTRAS:
                return (T) new ExtrasDAOImpl();
            case RETURN:
                return (T) new ReturnDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }


}
