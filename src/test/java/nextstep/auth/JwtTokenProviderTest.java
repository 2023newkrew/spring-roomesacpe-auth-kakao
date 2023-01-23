package nextstep.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.AcceptanceTestExecutionListener;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestExecutionListeners;

import static nextstep.RoomEscapeApplication.getPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
@SpringBootTest
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class JwtTokenProviderTest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    JwtTokenProvider jwtTokenProvider;
    @BeforeEach
    void setUp(){
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("토큰을 생성할 수 있다")
    @Test
    public void createTokenTest() {
        saveMember(jdbcTemplate);
        ExtractableResponse<Response> response = generateToken();
        assertThat(response.as(TokenResponse.class)).isNotNull();
    }

    @Test
    @DisplayName("토큰을 이용하여 유저 정보를 가져올 수 있다.")
    void findByUsernameTest() {
        saveMember(jdbcTemplate);
        ExtractableResponse<Response> response = generateToken();

        String accessToken = response.body().jsonPath().getString("accessToken");
        String username = jwtTokenProvider.getPrincipal(accessToken);
        Assertions.assertThat(username).isEqualTo(USERNAME);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    public static void saveMember(JdbcTemplate jdbcTemplate){
        PasswordEncoder passwordEncoder = getPasswordEncoder();
        Member member = new Member(USERNAME, passwordEncoder.encode(PASSWORD), "name", "010");
        MemberDao memberDao = new MemberDao(jdbcTemplate);
        memberDao.save(member);
    }

    public static ExtractableResponse<Response> generateToken() {
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
