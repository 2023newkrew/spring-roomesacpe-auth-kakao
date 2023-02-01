package nextstep.auth.dto;

public class TokenResponse {
    public String accessToken;

    private TokenResponse() {}

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
