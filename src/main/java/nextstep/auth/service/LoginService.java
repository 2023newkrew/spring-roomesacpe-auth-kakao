package nextstep.auth.service;

import lombok.RequiredArgsConstructor;
import nextstep.auth.utils.JwtTokenProvider;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.member.Member;
import nextstep.member.MemberDao;

@RequiredArgsConstructor
public class LoginService {

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse login(TokenRequest tokenRequest) {
        Member member = findByUsername(tokenRequest.getUsername());

        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return new TokenResponse(jwtTokenProvider.createToken(member.getId().toString()));
    }

    private Member findByUsername(String username) {
        try {
            return memberDao.findByUsername(username);
        } catch (RuntimeException e) {
            throw new RuntimeException("아이디에 해당하는 사용자가 존재하지 않습니다.");
        }
    }

}
