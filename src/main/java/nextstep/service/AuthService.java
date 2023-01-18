package nextstep.service;

import nextstep.dao.MemberDao;
import nextstep.domain.Member;
import nextstep.support.exception.AuthorizationException;
import nextstep.support.util.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(long memberId, String password) {
        if (checkInvalidLogin(memberId, password)) {
            throw new AuthorizationException();
        }

        String memberIdStr = String.valueOf(memberId);
        return jwtTokenProvider.createToken(memberIdStr);
    }

    private boolean checkInvalidLogin(long memberId, String password) {
        Member member = memberDao.findByMemberId(memberId);
        return !member.getId().equals(memberId) || member.isInvalidPassword(password);
    }
}
