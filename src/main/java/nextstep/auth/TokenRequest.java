package nextstep.auth;

import javax.validation.constraints.NotBlank;

public class TokenRequest {
    @NotBlank
    private String username;

    @NotBlank
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
