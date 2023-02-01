package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.auth.TokenRequest;
import nextstep.dto.dto.ReservationRequest;
import nextstep.dto.member.MemberRequest;
import nextstep.dto.schedule.ScheduleRequest;
import nextstep.dto.theme.ThemeRequest;
import nextstep.persistence.reservation.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationE2ETest {
    public static final String RESERVATION_DATE = "2022-08-11";
    public static final String RESERVATION_TIME = "13:00";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String THEME_NAME = "테마이름";
    public static final String THEME_DESC = "테마설명";
    public static final int THEME_PRICE = 22000;
    public static final String NAME = "name";
    public static final String PHONE = "010-1234-5678";
    public static final String OTHER_USERNAME = "다른사람";
    public static final long MINUS_SCHEDULE_ID = -1L;
    public static final long NOT_EXIST_SCHEDULE_ID = 1000L;
    public static final long RIGHT_SCHEDULE_ID = 1L;
    public static final long NOT_EXIST_THEME_ID = 1000L;

    private ReservationRequest request;
    private Long themeId;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        body = new MemberRequest("다른사람", PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        String accessToken = loginAndGetAccessToken("admin");
        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);
        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", accessToken)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = themeResponse.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, RESERVATION_DATE, RESERVATION_TIME);
        var scheduleResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", accessToken)
                .body(scheduleRequest)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        Long scheduleId = Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);

        request = new ReservationRequest(
                scheduleId,
                "브라운"
        );
    }

    @DisplayName("예약을 생성한다")
    @Test
    void create() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        var response = RestAssured
                .given().log().all()
                .header("authorization", accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("음수 스케줄번호로 예약을 생성할 수 없다.")
    @Test
    void cannotCreateReservationWithMinusThemeId() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        var response = RestAssured
                .given().log().all()
                .header("authorization", accessToken)
                .body(new ReservationRequest(MINUS_SCHEDULE_ID, NAME))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("없는 스케줄번호로 예약을 생성할 수 없다.")
    @Test
    void cannotCreateReservationWithNotExistThemeId() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        var response = RestAssured
                .given().log().all()
                .header("authorization", accessToken)
                .body(new ReservationRequest(NOT_EXIST_SCHEDULE_ID, NAME))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("null인 이름으로 예약을 생성할 수 없다.")
    @Test
    void cannotCreateReservationWithNullName() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        var response = RestAssured
                .given().log().all()
                .header("authorization", accessToken)
                .body(new ReservationRequest(RIGHT_SCHEDULE_ID, null))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("Empty String인 이름으로 예약을 생성할 수 없다.")
    @Test
    void cannotCreateReservationWithEmptyName() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        var response = RestAssured
                .given().log().all()
                .header("authorization", accessToken)
                .body(new ReservationRequest(RIGHT_SCHEDULE_ID, ""))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("Blank인 이름으로 예약을 생성할 수 없다.")
    @Test
    void cannotCreateReservationWithBlankName() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        var response = RestAssured
                .given().log().all()
                .header("authorization", accessToken)
                .body(new ReservationRequest(RIGHT_SCHEDULE_ID, "    "))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }



    @DisplayName("예약을 조회한다")
    @Test
    void show() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        createReservation(accessToken);

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", RESERVATION_DATE)
                .header("authorization", accessToken)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @DisplayName("없는 테마의 예약은 조회할 수 없다.")
    @Test
    void cannotShowNotExistThemeReservation() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        createReservation(accessToken);

        var response = RestAssured
                .given().log().all()
                .param("themeId", NOT_EXIST_THEME_ID)
                .param("date", RESERVATION_DATE)
                .header("authorization", accessToken)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        var reservation = createReservation(accessToken);

        var response = RestAssured
                .given().log().all()
                .header("authorization", accessToken)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("중복 예약을 생성할 수 없다.")
    @Test
    void cannotCreateDuplicateReservation() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        createReservation(accessToken);

        var response = RestAssured
                .given().log().all()
                .header("authorization", accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약이 없을 때 예약 목록을 조회한다")
    @Test
    void showEmptyReservations() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", RESERVATION_DATE)
                .header("authorization", accessToken)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(0);
    }

    @DisplayName("없는 예약을 삭제할 수 없다.")
    @Test
    void cannotCreateNotExistReservation() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        var response = RestAssured
                .given().log().all()
                .header("authorization", accessToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("다른 사람의 예약을 삭제할 수 없다.")
    @Test
    void cannotDeleteNotMyReservation() {
        String accessToken = loginAndGetAccessToken(USERNAME);

        String otherAccessToken = loginAndGetOtherAccessToken();

        var reservation = createReservation(accessToken);

        var response = RestAssured
                .given().log().all()
                .header("authorization", otherAccessToken)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비로그인 사용자가 예약을 생성할 수 없다.")
    @Test
    void cannotCreateByNotLoginUser() {
        var response = RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> createReservation(String accessToken) {
        return RestAssured
                .given().log().all()
                .body(request)
                .header("authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }

    private String loginAndGetAccessToken(String username) {
        TokenRequest body = new TokenRequest(username, PASSWORD);
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");

        return "Bearer " + accessToken.toString();
    }

    private String loginAndGetOtherAccessToken() {
        TokenRequest body = new TokenRequest(OTHER_USERNAME, PASSWORD);
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");

        return "Bearer " + accessToken.toString();
    }
}
