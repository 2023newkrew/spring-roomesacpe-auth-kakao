package nextstep.theme;

import nextstep.error.ErrorCode;
import nextstep.error.exception.AuthenticationException;
import nextstep.member.LoginMember;
import nextstep.member.Role;

public class ThemeValidator {

    public static void checkRole(LoginMember loginMember) {
        if (!loginMember.getRole().equals(Role.ADMIN)) throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
    }
}
