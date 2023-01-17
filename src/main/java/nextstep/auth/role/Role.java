package nextstep.auth.role;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import nextstep.support.exception.NoSuchRoleException;

import java.util.Arrays;

public enum Role {
    ADMIN("admin"), USER("user");
    @Getter
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public static Role of(String role) {
        return Arrays.stream(Role.values())
                .filter(roleEnum -> role.equals(roleEnum.getRole()))
                .findAny()
                .orElseThrow(NoSuchRoleException::new);
    }
}
