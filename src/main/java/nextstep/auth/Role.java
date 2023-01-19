package nextstep.auth;

public enum Role {
    USER("user"), ADMIN("admin");

    private final String roleName;

     Role(String roleName) {
        this.roleName = roleName;
    }
}
