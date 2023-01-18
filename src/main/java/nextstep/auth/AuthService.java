package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.permission.Authority;
import org.springframework.stereotype.Service;
import nextstep.support.AuthorizationException;

/**
 * AuthService contains logics about creating, validate token and manipulating login logics.
 * this may access MemberDao to validate username and password.
 */
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    /**
     * This method validate that given login information matches program's database.
     * If validation is successfully done, token would be returned.
     *
     * @param tokenRequest which contains login information.
     * @throws AuthorizationException when validation failed.
     * @return tokenResponse which contains token.
     */
    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        if (checkInvalidLogin(tokenRequest.getUsername(), tokenRequest.getPassword(), member)) {
            throw new AuthorizationException();
        }

        // 기본키인 id를 principal로 하여 accessToken을 생성.
        String accessToken = jwtTokenProvider.createToken(member.getId().toString(), member.getAuthority());
        return new TokenResponse(accessToken);
    }

    private boolean checkInvalidLogin(String principal, String credentials, Member member) {
        return !member.getUsername().equals(principal) || !member.getPassword().equals(credentials);
    }

    /**
     * This method validates token.
     *
     * @param token to be validated.
     * @throws AuthorizationException when validation failed.
     * @return id of member.
     */
    public Long getMemberIdFromToken(String token){
        validateToken(token);
        return Long.parseLong(jwtTokenProvider.getPrincipal(token));
    }

    public Authority getAuthorityFromToken(String token){
        validateToken(token);
        return Authority.valueOf(jwtTokenProvider.getAuthority(token));
    }

    private void validateToken(String token){
        if (!jwtTokenProvider.validateToken(token)){
            throw new AuthorizationException();
        }
    }
}
