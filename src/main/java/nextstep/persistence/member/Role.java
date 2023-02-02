package nextstep.persistence.member;

public enum Role {
    ADMIN("admin"),
    NORMAL("normal");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
