package nextstep.auth;

import nextstep.auth.dto.LoginRequest;
import nextstep.auth.dto.LoginResponse;
import nextstep.auth.dto.TokenRequest;
import nextstep.exceptions.exception.auth.WrongPasswordException;
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

    public LoginResponse createToken(LoginRequest request) {
        Member member = memberService.findById(request.getId());
        checkPassword(member, request.getPassword());
        return new LoginResponse(jwtTokenProvider.createToken(TokenRequest.fromMember(member)));
    }

    private static void checkPassword(Member member, String requestPassword) {
        if (member.checkWrongPassword(requestPassword)) {
            throw new WrongPasswordException();
        }
    }

    public Member getMemberFromToken(String token) {
        Long id = Long.valueOf(jwtTokenProvider.getSubject(token));
        return memberService.findById(id);
    }

}
