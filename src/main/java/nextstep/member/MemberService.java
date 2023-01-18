package nextstep.member;

import nextstep.auth.JwtTokenProvider;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.support.AuthorizationException;
import nextstep.support.DuplicateNameException;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public MemberService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @Transactional
    public Long create(MemberRequest memberRequest) {
        Member member = memberRequest.toEntity();
        String name = member.getName();
        boolean existingName = memberDao.isExistingMemberName(name);
        if (existingName) {
            throw new DuplicateNameException();
        }
        return memberDao.save(member);
    }


    public Member findByUsername(String username) {
        Member member = memberDao.findByUsername(username);
        if (member == null) {
            throw new NotExistEntityException();
        }
        return member;
    }

    public TokenResponse generateToken(TokenRequest tokenRequest) {
        validateIsMember(tokenRequest);
        String token = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(token);
    }

    public void validateIsMember(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        Member member = memberDao.findByUsername(username);
        if (member == null) {
            throw new AuthorizationException();
        }
        if (member.checkWrongPassword(password)) {
            throw new AuthorizationException();
        }
    }
}
