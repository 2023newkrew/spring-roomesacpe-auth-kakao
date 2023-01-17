package nextstep.member.service;

import nextstep.member.dao.MemberDao;
import nextstep.member.dto.MemberRequest;
import nextstep.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(new Member(null, memberRequest.getUsername(), memberRequest.getPassword(), memberRequest.getName(), memberRequest.getPhone()));
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }
}
