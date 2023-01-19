package nextstep.member;

import io.restassured.RestAssured;
import nextstep.domain.domain.Member;
import nextstep.domain.model.request.MemberRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.io.InputStream;
import java.util.List;

import static nextstep.auth.LoginUtils.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/sql/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MemberE2ETest {
    private String token;

    @Test
    @DisplayName("유저는 멤버를 생성할 수 없다.")
    public void createByUser() {
        token = loginUser();
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("관리자는 멤버를 생성할 수 있다.")
    public void createByAdmin() {
        token = loginAdmin();
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("토큰으로 멤버 정보를 조회한다")
    public void findByToken() {
        String token = loginUser();
        Member member = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(Member.class);

        assertThat(member.getUsername()).isEqualTo("user");
        assertThat(member.getPassword()).isEqualTo("user");
    }

    @Test
    @DisplayName("로그인이 되어있지 않아도 멤버 리스트를 볼 수 있다.")
    public void findAll() {
        List<?> memberList = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(List.class);

        assertThat(memberList).hasSize(3);
    }
}
