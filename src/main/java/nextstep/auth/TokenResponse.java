package nextstep.auth;

import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class TokenResponse {
    public String accessToken;

    private TokenResponse() {
    }

    public String getAccessToken() {
        return accessToken;
    }
}
