package nextstep;

import io.restassured.RestAssured;
import nextstep.interfaces.auth.dto.TokenRequest;
import nextstep.interfaces.auth.dto.TokenResponse;
import org.springframework.http.MediaType;

public class Login {

    public static final String USERNAME = "user";
    public static final String PASSWORD = "user";
    public static String loginUser(){
        return login(USERNAME, PASSWORD);
    }

    public static String loginAdmin(){
        return login("admin", "admin");
    }

    public static String login(String userName, String password){
        return RestAssured
                .given().log().all()
                .body(new TokenRequest(userName, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();
    }
}
