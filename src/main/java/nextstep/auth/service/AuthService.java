package nextstep.auth.service;

import lombok.RequiredArgsConstructor;
import nextstep.auth.support.JwtTokenProvider;
import nextstep.auth.model.TokenRequest;
import nextstep.auth.model.TokenResponse;
import nextstep.member.model.Member;
import nextstep.member.dao.MemberDao;
import nextstep.auth.support.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public TokenResponse createToken(TokenRequest tokenRequest) {
        if (checkInvalidLogin(tokenRequest.getMemberName(), tokenRequest.getPassword())) {
            throw new AuthenticationException();
        }

        String accessToken = jwtTokenProvider.createCredential(tokenRequest.getMemberName());
        return new TokenResponse(accessToken);
    }

    private boolean checkInvalidLogin(String subject, String credentials) {
        Member member = memberDao.findByMemberName(subject);
        if (member == null) {
            return true;
        }
        return member.checkWrongPassword(credentials);
    }
}
