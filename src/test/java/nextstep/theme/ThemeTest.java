package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.AcceptanceTestExecutionListener;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ThemeTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private ThemeDao themeDao;
    private ScheduleDao scheduleDao;

    @DisplayName("테마를 생성할 수 있다")
    @Test
    public void create() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("중복 테마를 생성할 경우, 에러가 발생한다")
    @Test
    public void duplicateCreateTest(){
        createTheme();
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("테마를 조회할 수 있다")
    @Test
    public void showThemes() {
        createTheme();
        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("스케줄이 없는 경우, 테마를 삭제할 수 있다")
    @Test
    void delete() {
        Long id = createTheme();
        RestAssured
            .given().log().all()
            .when().delete("/themes/" + id)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("스케줄이 있는데 테마를 삭제하는 경우, 에러 발생")
    @Test
    void deleteErrorTest(){
        Long id = createTheme();
        themeDao = new ThemeDao(jdbcTemplate);
        scheduleDao = new ScheduleDao(jdbcTemplate);
        Optional<List<Theme>> themeList = themeDao.findById(id);
        Theme theme = null;
        if (themeList.isPresent()) {
            theme = themeList.get().get(0);
        }
        scheduleDao.save(new Schedule(
                theme, LocalDate.parse("2022-01-01"), LocalTime.parse("12:00:00")
        ));
        RestAssured
                .given().log().all()
                .when().delete("/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("없는 테마를 삭제할 경우, 에러가 발생한다")
    @Test
    void emptyDeleteTest(){
        RestAssured
                .given().log().all()
                .when().delete("/themes/" + 1212L)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    public Long createTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
