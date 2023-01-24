package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.exception.NotCorrectPasswordException;
import nextstep.exception.NotExistEntityException;
import nextstep.member.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    @DisplayName("Member의 username과 password 일치 확인 후 토큰을 발급해서 반환")
    @Test
    void create() {
        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);

        TokenResponse tokenResponse = authService.createMemberToken(tokenRequest);

        String username = jwtTokenProvider.getPrincipal(tokenResponse.getAccessToken());

        assertThat(username).isEqualTo(USERNAME);
    }

    @DisplayName("username에 해당하는 Member가 DB에 없는 경우 예외 발생")
    @Test
    void create_notFound() {
        TokenRequest tokenRequest = new TokenRequest("wrongUserName", PASSWORD);
        assertThatThrownBy(() -> authService.createMemberToken(tokenRequest))
                .isInstanceOf(NotExistEntityException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.USER_NOT_FOUND);
    }

    @DisplayName("Member의 username과 password가 일치하지 않는 경우 예외 발생")
    @Test
    void create_wrongPassword() {
        TokenRequest tokenRequest = new TokenRequest(USERNAME, "wrongPassword");
        assertThatThrownBy(() -> authService.createMemberToken(tokenRequest))
                .isInstanceOf(NotCorrectPasswordException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.UNAUTHORIZED);
    }

    @DisplayName("Admin의 username과 password가 일치하는지 확인 후 토큰 발급해서 반환")
    @Test
    void createAdminToken() {
        TokenRequest tokenRequest = new TokenRequest("admin1", "admin1");

        TokenResponse tokenResponse = authService.createAdminToken(tokenRequest);

        String username = jwtTokenProvider.getPrincipal(tokenResponse.getAccessToken());

        assertThat(username).isEqualTo("admin1");
    }
}
