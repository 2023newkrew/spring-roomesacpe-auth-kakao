package nextstep;

import io.restassured.RestAssured;
import nextstep.interfaces.theme.dto.ThemeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ThemeMethod {
    public static Long createTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(Login.loginAdmin())
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
