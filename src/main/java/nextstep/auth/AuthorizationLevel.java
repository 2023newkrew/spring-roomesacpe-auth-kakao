package nextstep.auth;

public enum AuthorizationLevel {
    USER("USER"),
    ADMIN("ADMIN");

    private final String authLevel;

    AuthorizationLevel(String authLevel) {
        this.authLevel = authLevel;
    }

    public String value() {
        return authLevel;
    }
}
