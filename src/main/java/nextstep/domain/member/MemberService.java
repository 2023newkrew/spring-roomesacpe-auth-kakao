package nextstep.domain.member;

import nextstep.interfaces.exception.AuthorizationException;
import nextstep.interfaces.member.dto.MemberRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        try {
            return memberDao.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new AuthorizationException("해당 아이디가 존재하지 않습니다.");
        }
    }
}
