package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.exception.UnAuthorizedException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Function;

@Service
public class AuthService {
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String BEARER_TYPE = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public LoginMember parseTokenFromRequest(Function<String, String> getHeaderFunction) {
        String header = getHeaderFunction.apply(AUTHORIZATION_HEADER_NAME);
        validateHeader(header);

        String token = parseTokenFromHeader(header);
        validateToken(token);

        Long id = Long.valueOf(jwtTokenProvider.getPrincipal(token));
        Role role = jwtTokenProvider.getRole(token);
        Member member = memberDao.findById(id);
        return LoginMember.of(member, role);
    }

    private void validateHeader(String header) {
        if (header == null) {
            throw new UnAuthorizedException();
        }

        if (!(header.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
            throw new UnAuthorizedException();
        }
    }

    private String parseTokenFromHeader(String header) {
        String token = header.substring(BEARER_TYPE.length()).trim();
        int commaIndex = token.indexOf(',');
        if (commaIndex > 0) {
            token = token.substring(0, commaIndex);
        }
        return token;
    }

    private void validateToken(String token) {
        if(!jwtTokenProvider.validateToken(token)) {
            throw new UnAuthorizedException();
        }
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        validateUsernamePassword(tokenRequest.getUsername(), tokenRequest.getPassword());

        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        if (member == null) {
            throw new UnAuthorizedException();
        }

        String accessToken = jwtTokenProvider.createToken(member.getId().toString(), Role.USER);
        return new TokenResponse(accessToken);
    }

    public TokenResponse createAdminToken() {
        String accessToken = jwtTokenProvider.createToken("0", Role.ADMIN);
        return new TokenResponse(accessToken);
    }

    private void validateUsernamePassword(String username, String password) throws UnAuthorizedException {
        Member member = memberDao.findByUsername(username);

        if (member == null) {
            throw new UnAuthorizedException();
        }

        if (!Objects.equals(member.getPassword(), password)) {
            throw new UnAuthorizedException();
        }
    }

}
