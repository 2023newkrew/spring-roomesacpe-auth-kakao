package nextstep.auth.domain;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class LoginMember {

    private final String username;

    private final List<Role> roles = new ArrayList<>(List.of(Role.USER));

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
}
