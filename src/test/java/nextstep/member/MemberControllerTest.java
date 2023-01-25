package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.AuthService;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.dto.MemberResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    public static final String ACCESS_TOKEN = "token";
    @LocalServerPort
    int port;
    @MockBean
    private AuthService authService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("토큰으로 멤버 조회 API 테스트")
    void findMemberByTokenTest() {
        MemberResponseDto memberResponseDto = new MemberResponseDto(1L, "username", "password", "name", "010-1234-5678");
        when(authService.findUsernameByToken(anyString())).thenReturn("username");
        when(memberService.findByUsername("username")).thenReturn(memberResponseDto);
        when(jwtTokenProvider.getUsername(ACCESS_TOKEN)).thenReturn("username");

        RestAssured.given()
                .log()
                .all()
                .auth()
                .oauth2(ACCESS_TOKEN)
                .when()
                .get("members/me")
                .then()
                .log()
                .all()
                .body("id", is(memberResponseDto.getId()
                        .intValue()))
                .body("username", is(memberResponseDto.getUsername()))
                .body("password", is(memberResponseDto.getPassword()))
                .body("name", is(memberResponseDto.getName()))
                .body("phone", is(memberResponseDto.getPhone()));
    }
}
