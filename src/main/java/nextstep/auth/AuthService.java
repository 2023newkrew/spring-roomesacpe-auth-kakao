package nextstep.auth;

import nextstep.exceptions.exception.AuthorizationException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberResponse;
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
        if (checkInvalidLogin(request.getUsername(), request.getPassword())) {
            throw new AuthorizationException("잘못된 비밀번호 입니다.");
        }
        return new TokenResponse(jwtTokenProvider.createToken(request.getUsername()));
    }

    private boolean checkInvalidLogin(String username, String password) {
        Member member = memberDao.findByUsername(username);
        return member.checkWrongPassword(password);
    }

    public MemberResponse findMember(String principal) {
        return new MemberResponse(1L, principal, 10);
    }

    public MemberResponse findMemberByToken(String token) {
        String payload = jwtTokenProvider.getPayload(token);
        return findMember(payload);
    }
}
