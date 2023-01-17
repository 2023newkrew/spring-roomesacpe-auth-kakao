package nextstep.schedule;

import io.restassured.RestAssured;
import java.util.List;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static nextstep.Constant.THEME_NAME;
import static nextstep.Constant.THEME_DESCRIPTION;
import static nextstep.Constant.THEME_PRICE;
import static nextstep.Constant.DATE;
import static nextstep.Constant.TIME;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ScheduleE2ETest {
    private Long themeId;

    @BeforeEach
    void setUp() {
        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESCRIPTION, THEME_PRICE);
        String location = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(themeRequest)
                .when().post("/themes")
                .then().statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        String[] themeLocation = location.split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);
    }

    @DisplayName("스케줄을 생성한다")
    @Test
    void createSchedule() {
        String location = getCreatedScheduleLocation();
        int scheduleId = Integer.parseInt(location.split("/")[2]);
        assertThat(scheduleId).isPositive();
    }

    @DisplayName("스케줄 목록을 조회한다")
    @Test
    void showSchedules() {
        String location = getCreatedScheduleLocation();
        int scheduleId = Integer.parseInt(location.split("/")[2]);

        var response = RestAssured
                .given().param("themeId", themeId)
                .param("date", DATE)
                .when().get("/schedules")
                .then().statusCode(HttpStatus.OK.value())
                .extract();

        List<Schedule> schedules = response.jsonPath().getList(".", Schedule.class);
        assertThat(schedules).hasSize(1);

        Schedule schedule = schedules.get(0);
        assertThat(schedule.getTheme().getId()).isEqualTo(themeId);
        assertThat(schedule.getId()).isEqualTo(scheduleId);
        assertThat(schedule.getDate()).isEqualTo(DATE);
        assertThat(schedule.getTime()).isEqualTo(TIME);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        String location = getCreatedScheduleLocation();

        var response = RestAssured
                .given().log().all()
                .when().delete(location)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public String getCreatedScheduleLocation() {
        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        return RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(scheduleRequest)
                .when().post("/schedules")
                .then().statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
    }
}
