package nextstep.schedule;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.schedule.dto.ScheduleRequestDto;
import nextstep.theme.dto.ThemeRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ScheduleE2ETest {

    private Long themeId;

    public static String requestCreateSchedule() {
        ScheduleRequestDto body = new ScheduleRequestDto(1L, LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"));
        return RestAssured
                .given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post("/schedules")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");
    }

    @BeforeEach
    void setUp() {
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테마이름", "테마설명", 22000);
        var response = RestAssured
                .given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDto)
                .when()
                .post("/themes")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = response.header("Location")
                .split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);
    }

    @DisplayName("스케줄을 생성한다")
    @Test
    public void createSchedule() {
        ScheduleRequestDto body = new ScheduleRequestDto(themeId, LocalDate.parse("2022-08-11"), LocalTime.parse("13:00"));
        RestAssured
                .given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post("/schedules")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    public void showSchedules() {
        requestCreateSchedule();

        var response = RestAssured
                .given()
                .log()
                .all()
                .param("themeId", themeId)
                .param("date", "2022-08-11")
                .when()
                .get("/schedules")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath()
                .getList(".")
                .size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        String location = requestCreateSchedule();

        var response = RestAssured
                .given()
                .log()
                .all()
                .when()
                .delete(location)
                .then()
                .log()
                .all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
