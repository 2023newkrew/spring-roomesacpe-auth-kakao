package nextstep.auth;

import javax.validation.constraints.NotNull;

public class TokenRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;

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
