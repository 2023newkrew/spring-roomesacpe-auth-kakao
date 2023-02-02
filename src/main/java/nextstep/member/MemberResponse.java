package nextstep.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberResponse {

    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String phone;
}
