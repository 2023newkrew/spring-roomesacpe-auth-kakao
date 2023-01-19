package nextstep.member;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        Member member = memberDao.findById(id);
        if (Objects.isNull(member)) {
            throw new RoomReservationException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return member;
    }

    public Member findByUsername(String username) {
        Member member = memberDao.findByUsername(username);
        if (Objects.isNull(member)) {
            throw new RoomReservationException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return member;
    }
}
