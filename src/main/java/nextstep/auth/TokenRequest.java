package nextstep.auth;

import javax.validation.constraints.NotEmpty;

public class TokenRequest {

    @NotEmpty
    private String username;

    @NotEmpty
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
