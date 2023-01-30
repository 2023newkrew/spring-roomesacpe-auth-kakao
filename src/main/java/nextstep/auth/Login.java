package nextstep.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Login {
    private final Long id;
    private final Boolean admin;
}
