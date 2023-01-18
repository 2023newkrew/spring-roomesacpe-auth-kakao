package nextstep.auth;

import nextstep.error.ApplicationException;
import nextstep.presentation.interceptor.AdminInterceptor;
import nextstep.utils.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AdminInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AdminInterceptor adminInterceptor;

    @Test
    void 관리자_권한을_가진_사용자일_경우_요청에_대한_로직이_수행된다() throws Exception {
        // given
        String token = "access-token";

        given(request.getAttribute("accessToken"))
                .willReturn(token);
        given(jwtTokenProvider.getClaim(token, "role"))
                .willReturn("ADMIN");

        // when, then
        assertThat(adminInterceptor.preHandle(request, response, null)).isTrue();
    }

    @Test
    void 괸라자_권한이_없는_사용자일_경우_예외가_발생한다() {
        // given
        String token = "access-token";

        given(request.getAttribute("accessToken"))
                .willReturn(token);
        given(jwtTokenProvider.getClaim(token, "role"))
                .willReturn("USER");

        // when, then
        assertThatThrownBy(() -> adminInterceptor.preHandle(request, response, null))
                .isInstanceOf(ApplicationException.class);
    }

}
