package nextstep.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Value("${security.jwt.token.secret-key}")
    private String DEFINED_SECRET_KEY;

    @Value("${security.jwt.token.expire-length}")
    private Long VALIDITY_IN_MILLISECONDS;

    @Test
    @DisplayName("만기되지 않은 토큰은 유효하다.")
    void test1() {
        String token = createToken("1", DEFINED_SECRET_KEY, VALIDITY_IN_MILLISECONDS);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("만기되지 않은 토큰은 유효하다.")
    void test2() {
        String token = createToken("1", DEFINED_SECRET_KEY, 0L);

        assertThat(jwtTokenProvider.validateToken(token)).isFalse();
    }

    @Test
    @DisplayName("다른 비밀키로 생성된 token은 유효하지 않다.")
    void test3() {
        String token = createToken("1", "NOT_VALID_SECRET_KEY", VALIDITY_IN_MILLISECONDS);

        assertThat(jwtTokenProvider.validateToken(token)).isFalse() ;
    }

    @Test
    @DisplayName("발급된 토큰에서 principal을 추출할 수 있다.")
    void test4() {
        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }

    @ParameterizedTest
    @DisplayName("발급된 토큰에서 Credential을 추출할 수 있다.")
    @NullAndEmptySource
    @ValueSource(strings = {"Bearer", "Bearer ", "Bearer Token"})
    void test5(String token) {
        assertThat(jwtTokenProvider.getCredential(token)).isNotNull();
    }

    private String createToken(String principal, String secretKey, Long validityInMilliseconds) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}