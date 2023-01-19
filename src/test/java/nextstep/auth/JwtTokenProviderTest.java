package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.member.MemberDao;
import nextstep.member.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void createToken() {
        String token = jwtTokenProvider.createToken("username");

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        String token = jwtTokenProvider.createToken("username");

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("username");
        assertThat(jwtTokenProvider.getMemberId(token)).isEqualTo(1l);

    }
}