package nextstep.member;

import nextstep.auth.TokenRequestDto;
import nextstep.support.exception.DuplicateReservationException;
import nextstep.support.exception.InvalidAccessTokenException;
import nextstep.support.exception.NotExistReservationException;
import nextstep.support.exception.NotAdminException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        if (memberDao.findByUsername(memberRequest.getUsername())
                .isPresent()) {
            throw new DuplicateReservationException();
        }
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public MemberResponseDto findByUsername(String username) {
        Member foundMember = memberDao.findByUsername(username)
                .orElseThrow(() -> new NotExistReservationException());

        return MemberResponseDto.toDto(foundMember);
    }

    public void validatePassword(Member member,TokenRequestDto tokenRequestDto) {
        if (member.checkWrongPassword(tokenRequestDto.getPassword())) {
            throw new InvalidAccessTokenException();
        }
    }
}
