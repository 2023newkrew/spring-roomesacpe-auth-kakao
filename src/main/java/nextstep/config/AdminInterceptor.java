package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberRole;
import nextstep.member.MemberService;
import nextstep.support.AuthorizationException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.member.MemberRole.*;
import static nextstep.support.ErrorMessage.*;

@Component
@RequiredArgsConstructor
public class AdminInterceptor extends HandlerInterceptorAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");

        // 로그인 검증 로직
        checkLogin(accessToken);

        // 관리자인지 검증
        checkAdmin(accessToken);

        return super.preHandle(request, response, handler);
    }

    private void checkAdmin(String accessToken){
        Member member = getMemberByToken(accessToken);
        if(member.getRole() != ADMIN){
            throw new AuthorizationException(ADMIN_FAIL);
        }
    }

    private void checkLogin(String accessToken){
        checkNullToken(accessToken);
        checkTokenExpiration(accessToken);
    }

    private Member getMemberByToken(String accessToken){
        checkTokenExpiration(accessToken);
        String token = accessToken.split(" ")[1];
        return memberService.findMemberByToken(token);
    }

    // token 값이 비어있는지 확인
    private void checkNullToken(String accessToken){
        if(accessToken == null) {
            throw new AuthorizationException(LOGIN_FAIL);
        }
    }

    // token 만료 되었는지 확인
    private void checkTokenExpiration(String accessToken){
        String token = accessToken.split(" ")[1];
        if(!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException(TOKEN_EXPIRATION);
        }
    }
}