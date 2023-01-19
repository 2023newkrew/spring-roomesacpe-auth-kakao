package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.UnAuthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;

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

    public LoginMember parseTokenFromRequest(NativeWebRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
        validateHeader(header);

        String token = parseTokenFromHeader(header);
        validateToken(token);

        String username = jwtTokenProvider.getPrincipal(token);
        Member member = memberDao.findByUsername(username);
        return LoginMember.of(member);
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

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername(), Role.USER);
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
