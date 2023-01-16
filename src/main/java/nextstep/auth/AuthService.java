package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberMyInfoResponse;
import nextstep.support.AuthorizationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

@Service
public class AuthService {
    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER_TYPE = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        validateUsernamePassword(tokenRequest.getUsername(), tokenRequest.getPassword());

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return null;
    }

    public MemberMyInfoResponse extractMyInfoFromToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }
        String username = jwtTokenProvider.getPrincipal(token);
        return MemberMyInfoResponse.fromEntity(memberDao.findByUsername(username));
    }

    private void validateUsernamePassword(String username, String password) throws AuthorizationException {
        Member member = memberDao.findByUsername(username);

        if (member == null) {
            throw new AuthorizationException();
        }

        if (!Objects.equals(member.getPassword(), password)) {
            throw new AuthorizationException();
        }
    }

}
