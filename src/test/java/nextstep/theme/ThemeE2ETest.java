package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.dto.TokenRequest;
import nextstep.member.Member;
import nextstep.member.MemberRequest;
import nextstep.member.Role;
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
public class ThemeE2ETest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final Member member = Member.builder()
            .username("username")
            .password("password")
            .phone("010-1234-5678")
            .name("name")
            .role(Role.MEMBER)
            .build();

    private final Theme theme = Theme.builder()
            .name("themeName")
            .desc("themeDesc")
            .price(10000)
            .build();
    private String token;

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
        member.setId(getIdFromLocation(location));
        token = jwtTokenProvider.createToken(new TokenRequest(member));

        ThemeRequest themeRequest = new ThemeRequest(theme);
        location = RestAssured.given().auth().oauth2(token).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        theme.setId(getIdFromLocation(location));
    }


    private Long getIdFromLocation(String location) {
        return Long.parseLong(location.split("/")[2]);
    }

    @Test
    void 테마_목록을_조회한다() {
        var response = RestAssured
                .given().auth().oauth2(token).log().all()
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @Test
    void 테마를_삭제한다() {
        var response = RestAssured
                .given().auth().oauth2(token).log().all()
                .when().delete("/themes/" + theme.getId())
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}
