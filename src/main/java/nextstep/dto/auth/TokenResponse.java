package nextstep.dto.auth;

public class TokenResponse {
    public String accessToken;

    /* RestAssured에서 사용 */
    @SuppressWarnings("unused")
    public TokenResponse() {
    }

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    /* RestAssured에서 사용 */
    @SuppressWarnings("unused")
    public String getAccessToken() {
        return accessToken;
    }
}
