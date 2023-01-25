package nextstep.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.auth.utils.JwtTokenProvider;
import nextstep.entity.Member;
import nextstep.entity.MemberRole;
import nextstep.repository.MemberDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminMemberControllerTest {

    @Autowired
    MemberDao memberDao;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("일반_멤버는_삭제할_수_없다")
    void 일반_멤버는_삭제할_수_없다() {
        Member member = createMember("normal", MemberRole.USER);
        Member target = createMember("target", MemberRole.USER);
        memberDao.save(member);
        Long id = memberDao.save(target);

        TokenRequest body = new TokenRequest("normal", "PASSWORD");
        String token = loginAndGetToken(body);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().delete("/admin/member/" + id)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

    }

    @Test
    @DisplayName("운영_멤버는_삭제할_수_있다")
    void 운영_멤버는_삭제할_수_있다() {
        Member member = createMember("admin", MemberRole.ADMIN);
        Member target = createMember("target", MemberRole.USER);
        memberDao.save(member);
        Long id = memberDao.save(target);

        TokenRequest body = new TokenRequest("admin", "PASSWORD");
        String token = loginAndGetToken(body);

        Long count = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().delete("/admin/member/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(Long.class);

        assertThat(count).isEqualTo(1L);

    }

    private Member createMember(String normal, MemberRole user) {
        return Member.builder().username(normal)
                .password("PASSWORD")
                .name("NAME")
                .phone("PHONE")
                .role(user)
                .build();
    }

    private String loginAndGetToken(TokenRequest body) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();
    }
}