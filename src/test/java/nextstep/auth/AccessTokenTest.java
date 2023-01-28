package nextstep.auth;

import nextstep.auth.domain.AccessToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AccessTokenTest {

    private static final String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjc0MzkwODIyLCJleHAiOjE2NzQzOTQ0MjJ9.etlqY9QcpHiIC2fRF7I1NiTaD_XPb44oXinUtQTgI8c";
    private static final String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTkwLCJleHAiOjE2NjMzMDIxOTAsInJvbGUiOiJBRE1JTiJ9.-OO1QxEpcKhmC34HpmuBhlnwhKdZ39U8q91QkTdH9i0";

    @DisplayName("새로운 토큰을 생성한다.")
    @Test
    void createToken() {
        AccessToken token = AccessToken.create("1", "USER");

        assertThat(token.isValid()).isTrue();
    }

    @DisplayName("토큰에서 memberId를 가져온다.")
    @Test
    void getPrincipal() {
        AccessToken token = AccessToken.create("1", "USER");

        assertThat(token.getSub()).isEqualTo("1");
    }

    @DisplayName("만료된 토큰을 검증한다.")
    @Test
    void validateExpiredToken() {
        AccessToken token = AccessToken.from(expiredToken);

        assertThat(token.isExpired())
                .isTrue()
        ;
    }

    @DisplayName("올바르지 않은 포맷의 토큰을 검증한다.")
    @Test
    void validateInvalidToken() {
        AccessToken token = AccessToken.from(invalidToken);

        assertThat(token.isMalformed())
                .isTrue()
        ;
    }
}