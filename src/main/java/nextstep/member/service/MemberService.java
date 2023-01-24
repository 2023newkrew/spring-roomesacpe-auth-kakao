package nextstep.member.service;

import nextstep.member.repository.MemberDao;
import nextstep.member.dto.MemberRequest;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) { return memberDao.save(memberRequest.toEntity()); }
}
