package nextstep.auth.service;

import lombok.RequiredArgsConstructor;
import nextstep.auth.dao.MemberRoleDao;
import nextstep.auth.domain.MemberRoles;
import nextstep.auth.support.JwtTokenProvider;
import nextstep.auth.model.TokenRequest;
import nextstep.auth.model.TokenResponse;
import nextstep.member.model.Member;
import nextstep.member.dao.MemberDao;
import nextstep.auth.exception.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;
    private final MemberRoleDao memberRoleDao;

    public TokenResponse createToken(TokenRequest tokenRequest) {
        if (checkInvalidLogin(tokenRequest.getMemberName(), tokenRequest.getPassword())) {
            throw new AuthenticationException();
        }

        String accessToken = jwtTokenProvider.createCredential(tokenRequest.getMemberName());
        return new TokenResponse(accessToken);
    }

    public MemberRoles findRoleByMemberName(String memberName){
        return memberRoleDao.findByMemberName(memberName);
    }

    private boolean checkInvalidLogin(String subject, String credentials) {
        Member member = memberDao.findByMemberName(subject);
        if (member == null) {
            return true;
        }
        return member.checkWrongPassword(credentials);
    }
}
