package nextstep.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class TokenData {

    private final Long id;

    private final String role;

    public TokenData(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
