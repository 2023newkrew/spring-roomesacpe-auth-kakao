package nextstep.member.enums;

import java.util.Locale;
import java.util.Optional;

public enum Role {
    ADMIN,
    USER,
    ANONYMOUS;

    public static Role of(String role) {
        return Optional.ofNullable(Role.valueOf(role.toUpperCase(Locale.ROOT)))
                .orElse(ANONYMOUS);
    }
}
