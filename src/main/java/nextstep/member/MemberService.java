package nextstep.member;

import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
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
        return memberDao.findById(id);
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = findByUsername(tokenRequest.getUsername());

        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return new TokenResponse();
    }

    public Member findByUsername(String username) {
        try {
            return memberDao.findByUsername(username);
        } catch (RuntimeException e) {
            throw new RuntimeException("아이디에 해당하는 사용자가 존재하지 않습니다.");
        }
    }
}
