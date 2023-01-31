package nextstep.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginMember {
    private final long id;

    public static LoginMember from(Member member) {
        return new LoginMember(member.getId());
    }
}
