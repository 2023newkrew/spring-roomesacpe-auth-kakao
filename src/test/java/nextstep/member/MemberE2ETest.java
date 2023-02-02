package nextstep.member;

import io.restassured.RestAssured;
import nextstep.type.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {

    @Value("change-usertype-key")
    String secretKey;

    @DisplayName("멤버를 생성한다")
    @Test
    public void create() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("username이 겹치는 멤버를 생성한다")
    @Test
    public void create_with_existing_username() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("secretKey를 이용하여 usertype을 변경한다")
    @Test
    void change_usertype_with_secret_key() {
        //given
        MemberRequest memberRequest = new MemberRequest("username", "password", "name", "010-1234-5678");
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");

        Long memberId = Long.parseLong(location.split("/")[2]);

        ChangeUserTypeRequest changeUserTypeRequest = new ChangeUserTypeRequest(memberId, UserType.ADMIN, secretKey);
        //when
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(changeUserTypeRequest)
                .when().patch("/members/type")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().header("Location");
    }

    @DisplayName("잘못된 secretKey를 이용하여 usertype을 변경한다")
    @Test
    void change_usertype_with_secret_key_fail() {
        //given
        MemberRequest memberRequest = new MemberRequest("username", "password", "name", "010-1234-5678");
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");

        Long memberId = Long.parseLong(location.split("/")[2]);

        ChangeUserTypeRequest changeUserTypeRequest = new ChangeUserTypeRequest(memberId, UserType.ADMIN, "wrong secret key");
        //when
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(changeUserTypeRequest)
                .when().patch("/members/type")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract().header("Location");
    }
}
