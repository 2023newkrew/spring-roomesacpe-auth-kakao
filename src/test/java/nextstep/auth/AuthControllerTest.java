package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.member.MemberService;
import nextstep.support.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {
    @LocalServerPort
    int port;
    @MockBean
    private AuthService authService;
    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("로그인 API 테스트")
    void loginTest() {
        TokenRequestDto tokenRequestDto = new TokenRequestDto("username1", "password1");

        when(authService.login(any(TokenRequestDto.class))).thenReturn("token");

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequestDto)
                .when()
                .post("/login/token")
                .then()
                .log()
                .all()
                .body("accessToken", is("token"));
    }

    @Test
    @DisplayName("유효하지 않은 토큰 이용시 로그인 실패 테스트")
    void loginWithInvalidTokenTest() {
        TokenRequestDto tokenRequestDto = new TokenRequestDto("username1", "password1");

        doThrow(UnauthorizedException.class).when(memberService)
                .validateIdAndPassword(any(TokenRequestDto.class));

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequestDto)
                .when()
                .post("/login/token")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
