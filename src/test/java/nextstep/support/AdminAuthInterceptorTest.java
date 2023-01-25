package nextstep.support;

import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.exception.InvalidAuthorizationTokenException;
import nextstep.exception.NotExistEntityException;
import nextstep.exception.UnauthorizedMemberException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AdminAuthInterceptorTest {

    @Autowired
    AdminAuthInterceptor adminAuthInterceptor;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    MemberDao memberDao;

    @DisplayName("헤더에서 토큰을 추출해서 검증 통과하면 true 반환")
    @Test
    void preHandle_success() {
        String token = jwtTokenProvider.createToken("admin1");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AuthorizationTokenExtractor.AUTHORIZATION,
                AuthorizationTokenExtractor.BEARER_TYPE + " " + token);

        assertThat(adminAuthInterceptor.preHandle(request, null, null))
                .isTrue();
    }

    @DisplayName("헤더에서 토큰을 추출에 실패하면 예외 발생")
    @Test
    void preHandle_invalidToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AuthorizationTokenExtractor.AUTHORIZATION, "invalidToken");

        assertThatThrownBy(() -> adminAuthInterceptor.preHandle(request, null, null))
                .isInstanceOf(InvalidAuthorizationTokenException.class);
    }

    @DisplayName("토큰에서 추출한 username에 해당하는 Member가 존재하지 않는 경우 예외 발생")
    @Test
    void preHandle_notFoundAdmin() {
        String token = jwtTokenProvider.createToken("wrongUsername");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AuthorizationTokenExtractor.AUTHORIZATION,
                AuthorizationTokenExtractor.BEARER_TYPE + " " + token);

        assertThatThrownBy(() -> adminAuthInterceptor.preHandle(request, null, null))
                .isInstanceOf(NotExistEntityException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.UNAUTHORIZED);
    }

    @DisplayName("Admin Role이 아닌 경우 예외 발생")
    @Test
    void preHandle_unauthorized() {
        memberDao.save(
                new Member("username", "password", "kim", "010-1234-5678", Role.USER));

        String token = jwtTokenProvider.createToken("username");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AuthorizationTokenExtractor.AUTHORIZATION,
                AuthorizationTokenExtractor.BEARER_TYPE + " " + token);

        assertThatThrownBy(() -> adminAuthInterceptor.preHandle(request, null, null))
                .isInstanceOf(UnauthorizedMemberException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.UNAUTHORIZED);
    }
}