package nextstep.member;

public enum Role {
    ADMIN("admin"),
    USER("user");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }
}
