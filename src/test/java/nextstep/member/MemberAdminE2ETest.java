package nextstep.member;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.List;
import nextstep.auth.E2ETestAuthUtils;
import nextstep.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class MemberAdminE2ETest {

    @DisplayName("어드민 권한으로 전체 멤버를 조회한다.")
    @Test
    public void showAllMembers() {
        E2ETestMemberUtils.createMember();

        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/admin/members")
                .then().log().all()
                .extract();

        List<Member> members = response.jsonPath().getList(".", Member.class);
        assertThat(members.size()).isEqualTo(2);
    }

    @DisplayName("일반 유저 권한으로는 전체 멤버를 조회할 수 없다.")
    @Test
    public void showAllMembersWithUserAuthority() {
        E2ETestMemberUtils.createMember();

        String accessToken = E2ETestAuthUtils.loginAndGetAccessToken();
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/admin/members")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @DisplayName("로그인을 하지 않고 전체 멤버를 조회할 수 없다.")
    @Test
    public void showAllMembersWithNoneAuthority() {
        E2ETestMemberUtils.createMember();

        RestAssured
                .given().log().all()
                .when().get("/admin/members")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @Test
    @DisplayName("관리자 권한으로 회원을 삭제할 수 있다.")
    public void deleteMember() {
        Long generatedId = E2ETestMemberUtils.createMember();

        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/admin/members/" + generatedId)
                .then().log().all()
                .extract();
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/admin/members")
                .then().log().all()
                .extract();

        List<Member> members = response.jsonPath().getList(".", Member.class);
        assertThat(members.size()).isEqualTo(1);
    }

    @DisplayName("일반 유저 권한으로는 전체 멤버를 조회할 수 없다.")
    @Test
    public void deleteMemberWithUserAuthority() {
        Long generatedId = E2ETestMemberUtils.createMember();

        String accessToken = E2ETestAuthUtils.loginAndGetAccessToken();
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/admin/members/" + generatedId)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @DisplayName("로그인을 하지 않고 전체 멤버를 조회할 수 없다.")
    @Test
    public void deleteMemberWithNoneAuthority() {
        Long generatedId = E2ETestMemberUtils.createMember();

        RestAssured
                .given().log().all()
                .when().delete("/admin/members/" + generatedId)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("관리자 권한으로 내 정보를 조회할 수 있다.(일반 api 테스트)")
    @Test
    public void getMyInfoWithAdminAuthority() {
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(Member.class)).isNotNull();
    }
}
