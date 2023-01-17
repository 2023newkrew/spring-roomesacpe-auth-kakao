package nextstep.member;

import nextstep.support.exception.DuplicateEntityException;
import nextstep.support.exception.NotExistEntityException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        if (memberDao.findByUsername(memberRequest.getUsername()) != null) {
            throw new DuplicateEntityException("같은 username을 가진 회원이 존재합니다.");
        }
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public MemberResponseDto findByUsername(String username) {
        MemberResponseDto memberResponseDto = null;
        try {
            memberResponseDto = MemberResponseDto.toDto(memberDao.findByUsername(username));
        } catch (NullPointerException nullPointerException) {
            throw new NotExistEntityException("존재하지 않는 회원입니다.");
        }
        return memberResponseDto;
    }
}
