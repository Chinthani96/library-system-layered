package business.custom.impl;

import business.custom.MemberBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.MemberDAO;
import entity.Member;
import util.MembersTM;

import java.util.ArrayList;
import java.util.List;

public class MemberBOImpl implements MemberBO {

    private static MemberDAO memberDAO = DAOFactory.getInstance().getDAO(DAOType.MEMBER);

    @Override
    public String generateNewMemberId() throws Exception {
        String lastMemberId = memberDAO.getLastMemberId();
        if (lastMemberId == null) {
            return "M001";
        }

        int lastNumber = Integer.parseInt(lastMemberId.substring(1, 4));
        lastNumber++;
        if (lastNumber < 10) {
            return "M00" + lastNumber;
        } else if (lastNumber < 100) {
            return "M0" + lastNumber;
        } else {
            return "M" + lastNumber;
        }
    }

    @Override
    public boolean saveMember(MembersTM member) throws Exception {
        return memberDAO.save(new Member(member.getMemberId(),member.getMemberName(),member.getAddress()));
    }

    @Override
    public boolean updateMember(MembersTM member) throws Exception {
        return memberDAO.update(new Member(member.getMemberId(),member.getMemberName(),member.getAddress()));
    }

    @Override
    public boolean deleteMember(String id) throws Exception {
        return memberDAO.delete(id);
    }

    @Override
    public List<MembersTM> getAllMembers() throws Exception {
        List<Member> all = memberDAO.findAll();
        List<MembersTM> members = new ArrayList<>();
        for (Member member:all){
            members.add(new MembersTM(member.getId(),member.getName(),member.getPhone_num()));
        }
        return members;
    }
}
