package nextstep.auth.model;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class TokenRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public TokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
