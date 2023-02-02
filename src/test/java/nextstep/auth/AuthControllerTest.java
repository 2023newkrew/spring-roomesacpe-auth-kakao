package nextstep.auth;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.restassured.RestAssured;
import nextstep.auth.dto.TokenRequestDto;
import nextstep.member.MemberRole;
import nextstep.member.MemberService;
import nextstep.member.dto.MemberResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

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
        TokenRequestDto tokenRequestDto = new TokenRequestDto("username1", "password1", MemberRole.GENERAL.toString());
        MemberResponseDto memberResponseDto = new MemberResponseDto(1L, "username1", "password", "name", "010-1111-2222",
            MemberRole.GENERAL.toString());

        when(authService.login(any(TokenRequestDto.class))).thenReturn("token");
        when(memberService.findByUsername(anyString())).thenReturn(memberResponseDto);

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
}
