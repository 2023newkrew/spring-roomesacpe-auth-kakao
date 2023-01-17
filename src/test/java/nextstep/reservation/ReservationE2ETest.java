package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.infrastructure.role.Role;
import nextstep.member.Member;
import nextstep.member.MemberDao;
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

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationE2ETest {
    public static final String DATE = "2022-08-11";
    public static final String TIME = "13:00";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private ReservationRequest request;
    private Long themeId;
    private Long scheduleId;
    private Long memberId;
    private String token;
    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678", "admin");
        var memberResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        TokenRequest loginBody = new TokenRequest(USERNAME, PASSWORD);

        token = requestLogin(loginBody);

        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .auth().oauth2(token)
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
                .body(scheduleRequest)
                .auth().oauth2(token)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        scheduleId = Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);

        String[] memberLocation = memberResponse.header("Location").split("/");
        memberId = Long.parseLong(memberLocation[memberLocation.length - 1]);

        request = new ReservationRequest(
                scheduleId,
                "브라운"
        );
    }

    private String requestLogin(TokenRequest loginBody) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginBody)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();
    }

    @DisplayName("예약을 생성한다")
    @Test
    void create() {
        var response = RestAssured
                .given().log().all()
                .body(request)
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("예약을 조회한다")
    @Test
    void show() {
        createReservation();

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .auth().oauth2(token)
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

        RestAssured
            .given().log().all()
            .auth().oauth2(token)
            .when().delete(reservation.header("Location"))
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value())
            .extract();
    }

    @DisplayName("중복 예약을 생성한다")
    @Test
    void createDuplicateReservation() {
        createReservation();

        var response = RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약이 없을 때 예약 목록을 조회한다")
    @Test
    void showEmptyReservations() {
        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .auth().oauth2(token)
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
                .auth().oauth2(token)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("자신의 예약이 아닌 경우 예약 취소가 불가능하다.")
    @Test
    void deleteNotOwner() {
        var reservation = createReservation();

        Member anotherMember = new Member("notOwnerUsername", "notOwnerPassword", "notOwnerName", "010-1234-5678", Role.ADMIN);
        memberDao.save(anotherMember);
        TokenRequest loginBodyAnother = new TokenRequest(anotherMember.getUsername(), anotherMember.getPassword());

        String anotherToken = requestLogin(loginBodyAnother);

       RestAssured
               .given().log().all()
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .auth().oauth2(anotherToken)
               .when().delete(reservation.header("Location"))
               .then().log().all()
               .statusCode(HttpStatus.FORBIDDEN.value());
    }

    private ExtractableResponse<Response> createReservation() {
        return RestAssured
                .given().log().all()
                .body(request)
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }
}
