package nextstep.member;

public enum MemberRole {
    USER("user"),
    ADMIN("admin");
    private final String role;

    MemberRole(String role) {
        this.role = role;
    }

    public static MemberRole getRole(String value) {
        if (value.equals("user")) return USER;
        if (value.equals("admin")) return ADMIN;
        return null;
    }

    public String valueOf() {
        return role;
    }

}
