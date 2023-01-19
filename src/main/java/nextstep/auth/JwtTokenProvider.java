package nextstep.auth;

import io.jsonwebtoken.*;
import nextstep.exception.BusinessException;
import nextstep.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Component
public class
JwtTokenProvider {

    public static final String ACCESS_TOKEN = "accessJWT";

    private static final String TOKEN_PREFIX = "Bearer";

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public String createToken(String principal, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPrincipal(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRoles(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isTokenNotExist(authorizationHeader)) {
            throw new BusinessException(ErrorCode.TOKEN_NOT_EXIST);
        }
        return authorizationHeader.split(" ")[1];
    }

    private static boolean isTokenNotExist(String authorizationHeader) {
        return authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX + " ");
    }

    public long getValidityInMilliseconds() {
        return validityInMilliseconds;
    }
}
