package nextstep.member;

import nextstep.auth.JwtTokenProvider;
import nextstep.support.NotExistEntityException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;
    private JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Long create(MemberRequest memberRequest) {
        try {
            // 해당 username을 가진 유저가 이미 존재하는지 확인
            memberDao.findByUsername(memberRequest.getUsername());
            throw new IllegalArgumentException("이미 해당 이름을 가진 계정이 존재합니다.");
        } catch (EmptyResultDataAccessException e) {
            return memberDao.save(memberRequest.toEntity());
        }
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUsername(String username) {
        Member member = memberDao.findByUsername(username);
        if (member == null) {
            throw new NotExistEntityException();
        }
        return member;
    }

    public Member findByToken(String token) {
        String principal = jwtTokenProvider.getPrincipal(token);
        return findByUsername(principal);
    }
}
