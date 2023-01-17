package nextstep.auth;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TokenResponse {
    public final String accessToken;

    @JsonCreator
    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
