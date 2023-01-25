package nextstep.member;

import java.util.Arrays;

public enum Role {
    USER(0), ADMIN(1);

    private final int roleNumber;

    Role(int roleNumber) {
        this.roleNumber = roleNumber;
    }

    public int getValue() {
        return roleNumber;
    }


    public static Role getRole(int role) {
        return Arrays.stream(Role.values())
                .filter(v -> v.getValue() == role)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("권한이 올바르지 않습니다."));
    }

    public static Role getRole(String role) {
        return Arrays.stream(Role.values())
                .filter(v -> v.name().equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("권한이 올바르지 않습니다."));
    }
}
