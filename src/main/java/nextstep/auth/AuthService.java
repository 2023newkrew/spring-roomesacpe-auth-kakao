package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse issueToken(TokenRequest tokenRequest) {
        validateMember(tokenRequest);
        String token = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(token);
    }

    private void validateMember(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();

        List<Member> member = memberDao.findByUsername(username);
        if (member.isEmpty()) {
            throw new NotExistEntityException("멤버를 찾을 수 없습니다");
        }
        if (!Objects.equals(member.get(0).getPassword(), password)) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }
    }
}
