package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberDao memberDao;
    private final JwtTokenProvider tokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider tokenProvider) {
        this.memberDao = memberDao;
        this.tokenProvider = tokenProvider;
    }

    public String getToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        if (member == null) {
            throw new NotExistEntityException();
        }
        validateTokenRequest(member, tokenRequest);
        return generateTokenForMember(member);
    }

    private void validateTokenRequest(Member member, TokenRequest tokenRequest) {
        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new IllegalArgumentException();
        }
    }

    private String generateTokenForMember(Member member) {
        return tokenProvider.createToken(member.getId().toString());
    }
}
