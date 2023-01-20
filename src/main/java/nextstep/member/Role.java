package nextstep.member;

import java.util.Arrays;

public enum Role {
    ADMIN, MEMBER;

    public static Role of(String role) {
        return Arrays.stream(values())
                .filter(r -> r.isSameRole(role))
                .findAny()
                .orElseThrow();
    }

    private boolean isSameRole(String role) {
        return this.toString().equalsIgnoreCase(role);
    }

    public boolean isAdmin() {
        return this.equals(ADMIN);
    }

    public String getRole() {
        return this.toString();
    }
}
