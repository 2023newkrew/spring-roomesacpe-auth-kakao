package nextstep.member;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    ADMIN("admin"),
    GENERAL("general");

    private String name;
    private static final Map<String, MemberRole> roleMap = new HashMap<String, MemberRole>();

    static {
        for (MemberRole role : values()) {
            roleMap.put(role.getName(), role);
        }
    }

    public static MemberRole findBy(String roleName) {
        return roleMap.get(roleName);
    }
}
