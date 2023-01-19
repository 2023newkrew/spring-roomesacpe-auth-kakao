package nextstep.member;

import nextstep.auth.TokenRequestDto;
import nextstep.support.exception.DuplicateEntityException;
import nextstep.support.exception.NotExistEntityException;
import nextstep.support.exception.UnauthorizedException;
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
            throw new DuplicateEntityException("같은 username을 가진 회원이 존재합니다.");
        }
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public MemberResponseDto findByUsername(String username) {
        Member foundMember = memberDao.findByUsername(username)
                .orElseThrow(() -> new NotExistEntityException("존재하지 않는 회원입니다."));

        return MemberResponseDto.toDto(foundMember);
    }

    public void validatePassword(Member member,TokenRequestDto tokenRequestDto) {
        if (member.checkWrongPassword(tokenRequestDto.getPassword())) {
            throw new UnauthorizedException("유효하지 않은 회원입니다.");
        }
    }
}
