package nextstep.member;

import lombok.RequiredArgsConstructor;
import nextstep.error.ApplicationException;
import org.springframework.stereotype.Service;

import static nextstep.error.ErrorType.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberDao memberDao;

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        Member member = memberDao.findById(id);
        if (member == null) {
            throw new ApplicationException(MEMBER_NOT_FOUND);
        }
        return member;
    }
}
