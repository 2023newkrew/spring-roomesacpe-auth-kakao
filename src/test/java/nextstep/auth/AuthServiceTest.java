package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.member.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthServiceTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @Autowired
    AuthService authService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("username과 password 일치 확인 후 토큰을 발급해서 반환")
    @Test
    void create() {
        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);

        TokenResponse tokenResponse = authService.createToken(tokenRequest);

        String username = jwtTokenProvider.getPrincipal(tokenResponse.getAccessToken());

        assertThat(username).isEqualTo(USERNAME);
    }
}
