package business;

import business.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance(){
        return (boFactory==null)?new BOFactory():boFactory;
    }

    public <T extends SuperBO> T getBO(BOType boType){
        switch (boType) {
            case MEMBER:
                return (T) new MemberBOImpl();
            case BOOK:
                return (T) new BookBOImpl();
            case RETURN:
                return (T) new ReturnBOImpl();
            case USER:
                return (T) new UserBOImpl();
            case BORROW:
                return (T) new BorrowBOImpl();
            default:
                return null;
        }
    }
}
