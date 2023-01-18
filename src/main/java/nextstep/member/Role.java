package nextstep.member;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Role {
    USER("user"),
    ADMIN("admin");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public static final Map<String, Role> map = Collections.unmodifiableMap(init());

    private static Map<String, Role> init() {
        Map<String, Role> result = new HashMap<>();

        for (Role role : Role.values()) {
            result.put(role.description, role);
        }

        return result;
    }
}
