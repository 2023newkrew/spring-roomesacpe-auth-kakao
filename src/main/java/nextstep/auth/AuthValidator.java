package nextstep.auth;

import nextstep.error.ErrorCode;
import nextstep.error.exception.AuthenticationException;
import nextstep.member.Member;
import nextstep.member.MemberRepository;

public class AuthValidator {
    private final MemberRepository memberRepository;

    AuthValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member validateUser(String username, String password) {
        Member member = memberRepository.findByUsername(username);
        if (member.checkWrongPassword(password)) {
            throw new AuthenticationException(ErrorCode.INVALID_USERNAME_PASSWORD);
        }

        return member;
    }
}
