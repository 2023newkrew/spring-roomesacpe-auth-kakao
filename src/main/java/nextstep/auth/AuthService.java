package nextstep.auth;

import nextstep.exceptions.exception.AuthorizationException;
import nextstep.member.MemberService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private JwtTokenProvider jwtTokenProvider;
    private MemberService memberService;


    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public TokenResponse createToken(TokenRequest request) {
        if (checkInvalidLogin(request.getUsername(), request.getPassword())) {
            throw new AuthorizationException("잘못된 비밀번호 입니다.");
        }
        return new TokenResponse(jwtTokenProvider.createToken(request.getUsername()));
    }

    private boolean checkInvalidLogin(String username, String password) {
        return !memberService
                .findByUsername(username)
                .getPassword()
                .equals(password);
    }

}
