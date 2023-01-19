package nextstep.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.exception.AuthorizationException;
import nextstep.member.Member;
import nextstep.member.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider tokenProvider;
    private final MemberService memberService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = AuthorizationExtractor.extract(request);
        if (token == null) {
            throw new AuthorizationException();
        }
        tokenProvider.validate(token);
        Member member = memberService.findByToken(token);
        return member.isAdmin();
    }
}
