package nextstep.admin;

import io.restassured.RestAssured;
import nextstep.schedule.ScheduleRequest;
import nextstep.schedule.ScheduleService;
import nextstep.theme.ThemeRequest;
import nextstep.theme.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(AdminController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {
    public static final String ADMIN_ACCESS_TOKEN = "admin_access_token";
    @LocalServerPort
    int port;
    @MockBean
    private AdminInterceptor adminInterceptor;
    @MockBean
    private ThemeService themeService;
    @MockBean
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("테마 생성 API 테스트")
    void createThemeTest() {
        ThemeRequest themeRequest = new ThemeRequest("theme", "This is theme", 10000);
        when(adminInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class))).thenReturn(true);
        when(themeService.create(any(ThemeRequest.class))).thenReturn(1L);
        RestAssured.given()
                .auth()
                .oauth2(ADMIN_ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when()
                .post("/admin/themes")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1");
    }

    @Test
    @DisplayName("테마 삭제 API 테스트")
    void deleteThemeTest() {
        when(adminInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class))).thenReturn(true);

        RestAssured.given()
                .auth()
                .oauth2(ADMIN_ACCESS_TOKEN)
                .when()
                .delete("/admin/themes/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("스케줄 생성 API 테스트")
    void createScheduleTest() {
        ScheduleRequest scheduleRequest = new ScheduleRequest(1L, "2023-1-20", "(7:48");
        when(adminInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class))).thenReturn(true);
        when(scheduleService.create(any(ScheduleRequest.class))).thenReturn(1L);
        RestAssured.given()
                .auth()
                .oauth2(ADMIN_ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .when()
                .post("/admin/schedules")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/schedules/1");
    }

    @Test
    @DisplayName("테마 삭제 API 테스트")
    void deleteScheduleTest() {
        when(adminInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class))).thenReturn(true);

        RestAssured.given()
                .auth()
                .oauth2(ADMIN_ACCESS_TOKEN)
                .when()
                .delete("/admin/schedules/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
