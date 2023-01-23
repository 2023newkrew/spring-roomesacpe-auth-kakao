package nextstep.member;

import nextstep.support.exception.MemberException;
import nextstep.support.exception.RoomEscapeExceptionCode;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findByUsername(String username) {
        return memberDao.findByUsername(username)
                .orElseThrow(() -> new MemberException(RoomEscapeExceptionCode.NOT_FOUND_MEMBER));
    }
}
