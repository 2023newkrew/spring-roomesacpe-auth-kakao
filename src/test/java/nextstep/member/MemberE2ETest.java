package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
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
public class MemberE2ETest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private final Member member = Member.builder()
            .username("username")
            .password("password")
            .name("name")
            .phone("010-1234-5678")
            .build();
    private String token;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(member);
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        member.setId(Long.valueOf(location.split("/")[2]));
        token = jwtTokenProvider.createToken(String.valueOf(member.getId()));

    }


    @Test
    public void 멤버를_조회한다() {
        MemberResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .extract().as(MemberResponse.class);

        assertThat(response.getPassword()).isEqualTo(member.getPassword());
    }

}
