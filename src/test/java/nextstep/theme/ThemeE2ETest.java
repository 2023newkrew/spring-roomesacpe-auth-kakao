package nextstep.theme;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import nextstep.E2ETest;
import nextstep.auth.utils.JwtTokenProvider;
import nextstep.dto.theme.ThemeRequest;
import nextstep.entity.Member;
import nextstep.entity.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@E2ETest
public class ThemeE2ETest {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @DisplayName("운영 벰버는 테마를 생성할 수 있다.")
    @Test
    public void create() {
        // given
        String token = createToken(MemberRole.ADMIN);
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);

        // expected
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("일반 멤버는 테마를 생성할 수 없다.")
    @Test
    public void normalMemberDoesNotCreate() {
        // given
        String token = createToken(MemberRole.USER);
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);

        // expected
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    public void showThemes() {
        // given
        createTheme(MemberRole.ADMIN);

        // when
        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        // expected
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("테마를 삭제한다")
    @Test
    void delete() {
        // given
        Long id = createTheme(MemberRole.ADMIN);
        String token = createToken(MemberRole.ADMIN);

        // when
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("일반 유저는 테마를 삭제할 수 없다.")
    @Test
    void 일반_유저는_테마를_삭제할_수_없다() {
        // given
        Long id = createTheme(MemberRole.ADMIN);
        String token = createToken(MemberRole.USER);

        // when
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    public Long createTheme(MemberRole role) {
        String token = createToken(role);
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[3]);
    }

    private String createToken(MemberRole role) {
        Member member = Member.builder().role(role)
                .username("USERNAME")
                .name("NAME")
                .phone("PHONE")
                .password("PASSWORD").build();
        Member.giveId(member, 1L);
        return jwtTokenProvider.createToken(member);
    }
}
