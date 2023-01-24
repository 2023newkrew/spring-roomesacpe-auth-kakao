package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.member.MemberRequest;
import nextstep.schedule.ScheduleRequest;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationE2ETest {
    public static final String DATE = "2022-08-11";
    public static final String TIME = "13:00";

    private ReservationRequest request;
    private Long themeId;
    private Long scheduleId;

    MemberRequest memberRequest1;
    MemberRequest memberRequest2;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        String token = jwtTokenProvider.createToken("admin1");

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        String[] themeLocation = themeResponse.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        var scheduleResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(scheduleRequest)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        scheduleId = Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);

        memberRequest1 = new MemberRequest("username1", "password1", "name1", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest1)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        memberRequest2 = new MemberRequest("username2", "password2", "name2", "010-8765-4321");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest2)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        request = new ReservationRequest(
                scheduleId
        );
    }

    @DisplayName("예약을 생성한다")
    @Test
    void create() {
        String userName = memberRequest1.getUsername();
        String token = jwtTokenProvider.createToken(userName);

        RestAssured
                .given().log().all()
                .body(request)
                .header("Authorization", AuthorizationTokenExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
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
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        var reservation = createReservation();

        String userName = memberRequest1.getUsername();
        String token = jwtTokenProvider.createToken(userName);

        RestAssured
                .given().log().all()
                .header("Authorization", AuthorizationTokenExtractor.BEARER_TYPE + " " + token)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("중복 예약을 생성하면 400 코드 반환")
    @Test
    void createDuplicateReservation() {
        createReservation();

        String userName = memberRequest1.getUsername();
        String token = jwtTokenProvider.createToken(userName);

        RestAssured
                .given().log().all()
                .body(request)
                .header("Authorization", AuthorizationTokenExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(ErrorCode.DUPLICATE_RESERVATION.getStatus())
                .body("code", is(ErrorCode.DUPLICATE_RESERVATION.getCode()));
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
        assertThat(reservations).isEmpty();
    }

    @DisplayName("없는 예약을 삭제하면 404 코드 반환")
    @Test
    void deleteNotExistReservation() {
        String userName = memberRequest1.getUsername();
        String token = jwtTokenProvider.createToken(userName);

        RestAssured
                .given().log().all()
                .header("Authorization", AuthorizationTokenExtractor.BEARER_TYPE + " " + token)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(ErrorCode.RESERVATION_NOT_FOUND.getStatus())
                .body("code", is(ErrorCode.RESERVATION_NOT_FOUND.getCode()));
    }

    @DisplayName("다른 사람의 예약을 삭제하면 403 코드 반환")
    @Test
    void tryDeleteNotMyReservation() {
        ExtractableResponse<Response> reservation = createReservation();

        String userName = memberRequest2.getUsername();
        String token = jwtTokenProvider.createToken(userName);

        RestAssured
                .given().log().all()
                .header("Authorization", AuthorizationTokenExtractor.BEARER_TYPE + " " + token)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .statusCode(ErrorCode.FORBIDDEN.getStatus())
                .body("code", is(ErrorCode.FORBIDDEN.getCode()));
    }

    @DisplayName("잘못된 인증 토큰이 들어오면 401 코드 반환")
    @Test
    void test() {
        RestAssured
                .given().log().all()
                .body(request)
                .header("Authorization", "wrongToken")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(ErrorCode.INVALID_TOKEN.getStatus())
                .body("code", is(ErrorCode.INVALID_TOKEN.getCode()));
    }

    private ExtractableResponse<Response> createReservation() {
        String userName = memberRequest1.getUsername();
        String token = jwtTokenProvider.createToken(userName);

        return RestAssured
                .given().log().all()
                .body(request)
                .header("Authorization", AuthorizationTokenExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }
}
