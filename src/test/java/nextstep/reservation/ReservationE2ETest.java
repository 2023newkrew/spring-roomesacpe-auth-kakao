package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.auth.JwtTokenConfig;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.MemberRequest;
import nextstep.schedule.ScheduleRequest;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationE2ETest {
    public static final String DATE = "2022-08-11";
    public static final String TIME = "13:00";

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    private ReservationRequest reservationRequest;
    private Long themeId;
    private String memberToken;

    private String adminToken;

    @BeforeEach
    void setUp() {
        TokenRequest adminTokenRequest = new TokenRequest(adminUsername, adminPassword);
        adminToken = issueToken(adminTokenRequest);
        System.out.println("adminToken = " + adminToken);


        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        themeId = createTheme(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        Long scheduleId = createSchedule(scheduleRequest);

        MemberRequest memberRequest = new MemberRequest("username", "password", "name", "010-1234-5678");
        createMember(memberRequest);

        TokenRequest memberTokenRequest = new TokenRequest("username", "password");
        memberToken = issueToken(memberTokenRequest);

        reservationRequest = new ReservationRequest(
                scheduleId,
                "브라운"
        );
    }

    @DisplayName("예약을 생성한다")
    @Test
    void create() {
        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + memberToken)
                .body(reservationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("비로그인 사용자의 예약 생성이 불가하다.")
    void create_fail() {
        var response = RestAssured
                .given().log().all()
                .body(reservationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("예약을 조회한다")
    @Test
    void show() {
        createReservation();

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        var reservation = createReservation();

        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + memberToken)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("자신의 예약이 아닌 예약을 삭제")
    @Test
    void noAuthDelete() {
        var reservation = createReservation();

        MemberRequest memberRequest = new MemberRequest("username2", "password2", "name2", "010-1234-5678");
        createMember(memberRequest);

        TokenRequest tokenRequest = new TokenRequest(memberRequest.getUsername(), memberRequest.getPassword());
        String token = issueToken(tokenRequest);

        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + token)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("중복 예약을 생성한다")
    @Test
    void createDuplicateReservation() {
        createReservation();

        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + memberToken)
                .body(reservationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("예약이 없을 때 예약 목록을 조회한다")
    @Test
    void showEmptyReservations() {
        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(0);
    }

    @DisplayName("없는 예약을 삭제한다")
    @Test
    void createNotExistReservation() {
        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + memberToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private ExtractableResponse<Response> createReservation() {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + memberToken)
                .body(reservationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }

    private Long createMember(MemberRequest memberRequest) {
        var memberResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        String[] memberLocation = memberResponse.header("Location").split("/");
        Long memberId = Long.parseLong(memberLocation[memberLocation.length - 1]);
        return memberId;
    }

    private String issueToken(TokenRequest tokenRequest) {
        var tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        TokenResponse tokenResponse1 = tokenResponse.response().getBody().as(TokenResponse.class);
        String token = tokenResponse1.getAccessToken();
        return token;
    }

    private Long createTheme(ThemeRequest themeRequest) {
        var themeResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = themeResponse.header("Location").split("/");
        Long themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);
        return themeId;
    }

    private Long createSchedule(ScheduleRequest scheduleRequest) {
        var scheduleResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        Long scheduleId = Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);
        return scheduleId;
    }
}
