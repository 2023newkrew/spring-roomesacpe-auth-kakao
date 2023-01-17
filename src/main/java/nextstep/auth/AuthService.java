package nextstep.auth;

import nextstep.exceptions.exception.AuthorizationException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private JwtTokenProvider jwtTokenProvider;
    private MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest request) {
        try {
            checkInvalidLogin(request.getUsername(), request.getPassword());
        } catch (Exception e) {
            throw new AuthorizationException("이름 또는 비밀번호가 잘못되었습니다.");
        }
        return new TokenResponse(jwtTokenProvider.createToken(request.getUsername()));
    }

    private void checkInvalidLogin(String username, String password) {
        Member member = memberDao.findByUsername(username);
        if (member.checkWrongPassword(password)) {
            throw new RuntimeException();
        }
    }
}
