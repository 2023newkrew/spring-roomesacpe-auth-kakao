package nextstep.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Optional;

public class AuthorizationTokenExtractorTest {

    @DisplayName("토큰을 추출한다")
    @Test
    void extract_single() {
        String accessToken = "token";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(
                AuthorizationTokenExtractor.AUTHORIZATION,
                AuthorizationTokenExtractor.BEARER_TYPE + " " + accessToken);

        Optional<String> token = AuthorizationTokenExtractor.extract(request);

        Assertions.assertThat(token).isNotEmpty().get()
                .isEqualTo("token");
    }

    @DisplayName("여러개의 토큰이 있을 경우 맨 처음 토큰만 추출한다")
    @Test
    void extract_multi() {
        String accessTokens = "token1,token2";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(
                AuthorizationTokenExtractor.AUTHORIZATION,
                AuthorizationTokenExtractor.BEARER_TYPE + " " + accessTokens);

        Optional<String> token = AuthorizationTokenExtractor.extract(request);

        Assertions.assertThat(token).isNotEmpty().get()
                .isEqualTo("token1");
    }

    @DisplayName("Authorization 헤더가 없는 경우 Optional.empty() 반환")
    @Test
    void extract_empty() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        Optional<String> token = AuthorizationTokenExtractor.extract(request);

        Assertions.assertThat(token).isEmpty();
    }

    @DisplayName("토큰 scheme이 Bearer가 아닐 경우 Optional.empty() 반환")
    @Test
    void extract_wrongScheme() {
        String accessToken = "token";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(
                AuthorizationTokenExtractor.AUTHORIZATION,
                "wrongScheme" + " " + accessToken);

        Optional<String> token = AuthorizationTokenExtractor.extract(request);

        Assertions.assertThat(token).isEmpty();
    }

}
