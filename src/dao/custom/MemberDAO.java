package dao.custom;

import dao.CrudDAO;
import entity.Member;

public interface MemberDAO extends CrudDAO<Member,String> {
    String getLastMemberId() throws Exception;
}
