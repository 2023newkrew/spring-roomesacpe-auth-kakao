package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.NotExistEntityException;
import nextstep.support.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }


    public TokenResponse login(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername())
                .orElseThrow(NotExistEntityException::new);
        if (!passwordEncoder.matches(tokenRequest.getPassword(), member.getPassword())) {
            throw new WrongPasswordException();
        }
        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(member.getId())));
    }
}
