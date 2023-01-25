package nextstep.auth.util;

import nextstep.error.ErrorCode;
import nextstep.exception.InvalidAuthorizationTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AuthorizationTokenExtractorTest {

    @Autowired
    AuthorizationTokenExtractor authorizationTokenExtractor;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @DisplayName("토큰을 추출한다")
    @Test
    void extract_single() {
        String value = "value";
        String token = jwtTokenProvider.createToken(value);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(
                AuthorizationTokenExtractor.AUTHORIZATION,
                AuthorizationTokenExtractor.BEARER_TYPE + " " + token);

        assertThatNoException()
                .isThrownBy(() -> authorizationTokenExtractor.extract(request));
        assertThat(authorizationTokenExtractor.extract(request)).isEqualTo(token);
    }

    @DisplayName("여러개의 토큰이 있을 경우 맨 처음 토큰만 추출한다")
    @Test
    void extract_multi() {
        String value1 = "value1";
        String value2 = "value2";
        String token1 = jwtTokenProvider.createToken(value1);
        String token2 = jwtTokenProvider.createToken(value2);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(
                AuthorizationTokenExtractor.AUTHORIZATION,
                AuthorizationTokenExtractor.BEARER_TYPE + " " + token1 + "," + token2);

        assertThatNoException()
                .isThrownBy(() -> authorizationTokenExtractor.extract(request));
        assertThat(authorizationTokenExtractor.extract(request)).isEqualTo(token1);
    }

    @DisplayName("Authorization 헤더가 없는 경우 예외 발생")
    @Test
    void extract_empty() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertThatThrownBy(() -> authorizationTokenExtractor.extract(request))
                .isInstanceOf(InvalidAuthorizationTokenException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_TOKEN);
    }

    @DisplayName("토큰 scheme이 Bearer가 아닐 경우 예외 발생")
    @Test
    void extract_wrongScheme() {
        String accessToken = "token";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(
                AuthorizationTokenExtractor.AUTHORIZATION,
                "wrongScheme" + " " + accessToken);

        assertThatThrownBy(() -> authorizationTokenExtractor.extract(request))
                .isInstanceOf(InvalidAuthorizationTokenException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_TOKEN);
    }
}
