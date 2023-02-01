package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.auth.dto.LoginRequest;
import nextstep.auth.dto.LoginResponse;
import nextstep.auth.dto.TokenRequest;
import nextstep.member.Member;
import nextstep.member.MemberRequest;
import nextstep.reservation.ReservationRequest;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthE2ETest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private String token;
    private final Member member = Member.builder()
            .username("username")
            .password("password")
            .name("name")
            .phone("010-1234-5678")
            .build();

    @BeforeEach
    void setUp() {
        MemberRequest memberRequest = new MemberRequest(member);
        String location = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        member.setId(Long.parseLong(location.split("/")[2]));

        TokenRequest tokenRequest = new TokenRequest(member);
        token = jwtTokenProvider.createToken(tokenRequest);
        assertThat(Long.parseLong(jwtTokenProvider.getSubject(token))).isEqualTo(member.getId());

        LoginRequest loginRequest = new LoginRequest(member.getId(), member.getPassword());
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        String tokenPrinciple = jwtTokenProvider.getSubject(
                response.as(LoginResponse.class)
                        .getAccessToken()
        );
        assertThat(Long.parseLong(tokenPrinciple)).isEqualTo(member.getId());
    }

    @Test
    void 토큰_없이_테마를_조회할_수_없다() {
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

    }

    @Test
    void 토큰_없이_테마를_삭제할_수_없다() {
        RestAssured
                .given().log().all()
                .when().delete("/themes/" + 1L)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }


    @Test
    void 토큰_없이_예약을_할_수_없다() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ReservationRequest())
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 토큰_없이_예약을_삭제할_수_없다() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/" + 1L)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
