package nextstep.member;

import java.util.Arrays;
import nextstep.exceptions.exception.InvalidRoleException;

public enum Role {
    ADMIN, MEMBER;

    public boolean isAdmin() {
        return this.equals(ADMIN);
    }

    public static Role from(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.toString().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(InvalidRoleException::new);

    }

}
