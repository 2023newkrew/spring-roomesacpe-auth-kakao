package nextstep.member;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    Role(String role) {
        this.value = role;
    }

    public String value() {
        return this.value;
    }

    public static Role of(String value) {
        if (value.equals("ADMIN")) {
            return ADMIN;
        }
        return USER;
    }
}
