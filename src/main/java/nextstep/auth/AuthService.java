package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.NotExistEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberDao memberDao;

    @Autowired
    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }


    public TokenResponse login(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsernameAndPassword(tokenRequest.getUsername(), tokenRequest.getPassword())
                .orElseThrow(NotExistEntityException::new);
        return new TokenResponse(JwtTokenProvider.createToken(String.valueOf(member.getId())));
    }
}
