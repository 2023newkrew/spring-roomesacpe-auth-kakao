package nextstep.member;

import lombok.RequiredArgsConstructor;
import nextstep.support.exception.MemberException;
import nextstep.support.exception.RoomEscapeExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public Member findByUsername(String username) {
        return memberDao.findByUsername(username)
                .orElseThrow(() -> new MemberException(RoomEscapeExceptionCode.NOT_FOUND_MEMBER));
    }

    @Transactional(readOnly = true)
    public Member findByUsernameAndPassword(String username, String password) {
        Member member = findByUsername(username);
        if (member.checkWrongPassword(password)) {
            throw new MemberException(RoomEscapeExceptionCode.NOT_FOUND_MEMBER);
        }
        return member;
    }
}
