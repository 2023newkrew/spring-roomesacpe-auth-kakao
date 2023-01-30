package nextstep.auth.Interceptor;

import nextstep.auth.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberService;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static nextstep.auth.Interceptor.LoginInterceptor.authorization;
import static nextstep.support.Messages.EMPTY_TOKEN;
import static nextstep.support.Messages.MEMBER_NOT_FOUND;

@Component
public class TokenVerifier {
    private static MemberService memberService;
    private static JwtTokenProvider jwtTokenProvider;

    public TokenVerifier(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        TokenVerifier.memberService = memberService;
        TokenVerifier.jwtTokenProvider = jwtTokenProvider;
    }

    public static List<Member> validateToken(HttpServletRequest request) {
        String accessToken = request.getHeader(authorization);
        if (accessToken == null) {
            throw new AuthorizationServiceException(EMPTY_TOKEN.getMessage());
        }
        String username = jwtTokenProvider.getPrincipal(accessToken);
        List<Member> memberList = memberService.findByUsername(username);
        if (memberList.isEmpty()) {
            throw new NullPointerException(MEMBER_NOT_FOUND.getMessage());
        }
        return memberList;
    }
}

