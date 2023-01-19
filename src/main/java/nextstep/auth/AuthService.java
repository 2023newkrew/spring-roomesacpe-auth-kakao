package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.exception.InvalidMemberException;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }


    public String createToken(TokenRequest request) {
        Member member = memberDao.findByUsername(request.getUsername());
        if( member == null || member.checkWrongPassword(request.getPassword()))
            throw new InvalidMemberException();

        return jwtTokenProvider.createToken(member.getId().toString());
    }
}
