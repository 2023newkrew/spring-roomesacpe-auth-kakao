package nextstep.auth;

import nextstep.exceptions.exception.AuthorizationException;
import nextstep.member.Member;
import nextstep.member.MemberService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;


    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public TokenResponse createToken(TokenRequest request) {
        if (checkInvalidLogin(request.getId(), request.getPassword())) {
            throw new AuthorizationException("잘못된 비밀번호 입니다.");
        }
        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(request.getId())));
    }

    private boolean checkInvalidLogin(Long id, String password) {
        return memberService
                .findById(id)
                .checkWrongPassword(password);
    }

    public Member getMemberFromToken(String token) {
        Long id = Long.valueOf(jwtTokenProvider.getPrincipal(token));
        return memberService.findById(id);
    }

}
