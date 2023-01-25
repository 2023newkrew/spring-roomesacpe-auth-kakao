package nextstep.reservations.dto.auth;

public class TokenRequestDto {
    String username;
    String password;

    public TokenRequestDto() {
    }

    public TokenRequestDto(final String username, final String password) {
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
