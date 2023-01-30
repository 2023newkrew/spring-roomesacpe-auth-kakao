package nextstep.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN,
    USER,
    NONE,
    ;

    private static final Map<String, Role> roleMapper = new HashMap<>();

    static {
        Arrays.stream(Role.values())
                .forEach(role -> roleMapper.put(role.name(), role));
    }

    public static Role of(String role) {

        return Objects.requireNonNull(roleMapper.get(role));
    }
}
