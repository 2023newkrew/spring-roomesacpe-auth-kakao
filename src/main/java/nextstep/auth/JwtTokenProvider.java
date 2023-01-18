package nextstep.auth;

import io.jsonwebtoken.*;
import nextstep.support.exception.NoAccessTokenException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String secretKey = "learning-test-spring";
    private final Long validityInMilliseconds = 3600000L;

    public String createToken(String principal) {
        Claims claims = Jwts.claims()
                .setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPrincipal(String token) {
        String parsedToken = null;
        try {
            parsedToken = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException jwtException) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NoAccessTokenException("액세스 토큰이 존재하지 않습니다.");
        }
        return parsedToken;
    }
}
