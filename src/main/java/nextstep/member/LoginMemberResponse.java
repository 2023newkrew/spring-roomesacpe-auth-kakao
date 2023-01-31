package nextstep.member;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginMemberResponse {
    @JsonValue
    private final Long id;

    public static LoginMemberResponse fromEntity(LoginMember loginMember) {
        return new LoginMemberResponse(loginMember.getId());
    }
}
