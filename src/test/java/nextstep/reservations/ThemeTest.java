package nextstep.reservations;

import io.restassured.RestAssured;
import nextstep.reservations.repository.theme.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:schema/schema.sql", "classpath:data/init.sql"})
public class ThemeTest {
    @LocalServerPort
    int port;
    @Autowired
    ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 테마_생성() {
        Map<String, Object> themeRequest = new HashMap<>();
        themeRequest.put("name", "새로운 테마");
        themeRequest.put("desc", "테마설명");
        themeRequest.put("price", 22_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/2");
    }

    @Test
    void 테마_목록_조회() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 테마_삭제() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 중복_테마_생성_오류() {
        Map<String, Object> themeRequest = new HashMap<>();
        themeRequest.put("name", "워너고홈");
        themeRequest.put("desc", "테마설명");
        themeRequest.put("price", 22_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("이미 동일한 이름의 테마가 있습니다."));
    }

    @Test
    void 존재하지_않는_테마_삭제_오류() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/100")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("존재하지 않는 테마입니다."));
    }
}
