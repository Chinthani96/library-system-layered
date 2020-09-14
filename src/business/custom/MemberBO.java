package business.custom;

import business.SuperBO;
import util.MembersTM;

import java.util.List;

public interface MemberBO extends SuperBO {
    String generateNewMemberId() throws Exception;
    boolean saveMember(MembersTM member) throws Exception;
    boolean updateMember(MembersTM member) throws Exception;
    boolean deleteMember(String id) throws Exception;
    List<MembersTM> getAllMembers() throws Exception;
}
