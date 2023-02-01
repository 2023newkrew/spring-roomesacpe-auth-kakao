package nextstep.schedule;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.dto.TokenRequest;
import nextstep.member.Member;
import nextstep.member.MemberRequest;
import nextstep.member.Role;
import nextstep.theme.Theme;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ScheduleE2ETest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private final Member member = Member.builder()
            .username("username")
            .password("password")
            .phone("010-1234-5678")
            .name("name")
            .role(Role.MEMBER)
            .build();

    private final Theme theme = Theme.builder()
        .name("themeName")
        .desc("themeDesc")
        .build();

    private final Schedule schedule = Schedule.builder()
            .date(LocalDate.now())
            .time(LocalTime.now())
            .theme(theme)
            .build();
    private String token;

    @BeforeEach
    void setUp() {
        MemberRequest memberRequest = new MemberRequest(member);
        String location = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        member.setId(Long.parseLong(location.split("/")[2]));
        TokenRequest tokenRequest = new TokenRequest(member.getId(), member.getRole());
        token = jwtTokenProvider.createToken(tokenRequest);

        ThemeRequest themeRequest = new ThemeRequest(theme);
        location = RestAssured
                .given().auth().oauth2(token).log().all()

                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        theme.setId(Long.parseLong(location.split("/")[2]));

        ScheduleRequest scheduleRequest = new ScheduleRequest(schedule);
        location = RestAssured
                .given().auth().oauth2(token).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        schedule.setId(Long.parseLong(location.split("/")[2]));
    }

    @Test
    void 스케줄을_조회한다() {
        var response = RestAssured
                .given().auth().oauth2(token).log().all()
                .param("themeId", theme.getId())
                .param("date", schedule.getDate().toString())
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @Test
    void 스케줄을_삭제한다() {
        RestAssured
                .given().auth().oauth2(token).log().all()
                .when().delete("/schedules/" + schedule.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }
}
