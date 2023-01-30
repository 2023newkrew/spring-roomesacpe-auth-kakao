package nextstep.auth;

public class TokenResponse {
    public String accessToken;

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public TokenResponse() {

    }

    public String getAccessToken() {
        return accessToken;
    }
}
