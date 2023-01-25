package nextstep.member;

import java.util.Arrays;
import nextstep.exception.CustomException;
import nextstep.exception.ErrorCode;

public enum Role {
    ADMIN,
    USER,
    ;

    public static Role from(String role) {
        return Arrays.stream(Role.values())
                .filter(e -> e.toString().equalsIgnoreCase(role))
                .findAny()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ROLE));
    }

    public boolean isAdmin() {
        return this.equals(ADMIN);
    }
}
