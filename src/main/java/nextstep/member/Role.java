package nextstep.member;

import java.util.Arrays;
import nextstep.exceptions.exception.InvalidRoleException;

public enum Role {
    ADMIN(2), MEMBER(1);

    private final int level;
    Role(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isAvailable(Role role) {
        return this.level >= role.level;
    }

    public static Role from(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.toString().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(InvalidRoleException::new);

    }

}
