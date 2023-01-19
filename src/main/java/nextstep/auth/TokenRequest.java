package nextstep.auth;

import javax.validation.constraints.NotNull;

public class TokenRequest {

    @NotNull
    private final String username;
    @NotNull
    private final String password;

    public TokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
